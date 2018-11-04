package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubareaService;
import com.itheima.bos.utils.Config;
import com.itheima.bos.utils.FileUtil;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("subareaAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Result(name="success",location="/pages/base/sub_area.html",type="redirect")
public class SubareaAction extends CommonAction<SubArea> {
	@Autowired
	private Config config;
	@Autowired
	private SubareaService subareaService;
	
	@Action("subareaAction_findByPage")
	public String findByPage() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		final Area area = model.getArea();
		final String keyWords = model.getKeyWords();
		final FixedArea fixedArea = model.getFixedArea();
		Specification<SubArea> spec=new Specification<SubArea>() {
			@Override
			public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				if (StringUtils.isNotBlank(keyWords)) {
					Predicate p=cb.like(root.get("keyWords").as(String.class),"%"+keyWords+"%");
					list.add(p);
				}
				if (area!=null) {
					if (StringUtils.isNoneBlank(area.getProvince())) {
						Join<Object,Object> join=root.join("area");
						Predicate p=cb.equal(join.get("province").as(String.class),area.getProvince());
						list.add(p);
					}
					if (StringUtils.isNoneBlank(area.getCity())) {
						Join<Object,Object> join=root.join("area");
						Predicate p=cb.equal(join.get("city").as(String.class),area.getCity());
						list.add(p);
					}
					if (StringUtils.isNoneBlank(area.getDistrict())) {
						Join<Object,Object> join=root.join("area");
						Predicate p=cb.equal(join.get("district").as(String.class),area.getDistrict());
						list.add(p);
					}
				}
				if (fixedArea!=null && StringUtils.isNotBlank(fixedArea.getId())) {
					Join<Object,Object> join=root.join("fixedArea");
					Predicate p=cb.like(join.get("id").as(String.class),fixedArea.getId());
					list.add(p);
				}
				if (list.size()==0) {
					return null;
				}
				Predicate [] ps=new Predicate[list.size()];
				return cb.and(list.toArray(ps));
			}
		};
		Page<SubArea> page=subareaService.findByPage(spec,pageable);
		page2Json(page, new String [] {"fixedArea","subareas"});
		return NONE;
	}
	
	@Action("subareaAction_save")
	public String save() {
		subareaService.save(model);
		return SUCCESS;
	}
	
	@Action("subareaAction_exportXls")
	public String exportXls() throws IOException {
		List<SubArea> subareas=subareaService.findAll();
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
		HSSFSheet sheet = hssfWorkbook.createSheet("管理分区数据");
		HSSFRow row0 = sheet.createRow(0);
		row0.createCell(0).setCellValue("编号");
		row0.createCell(1).setCellValue("分区起始号");
		row0.createCell(2).setCellValue("分区终止号");
		row0.createCell(3).setCellValue("分区关键字");
		row0.createCell(4).setCellValue("辅助关键字");
		row0.createCell(5).setCellValue("区域信息");
		for (SubArea subarea : subareas) {
			HSSFRow row = sheet.createRow(sheet.getLastRowNum()+1);
			row.createCell(0).setCellValue(subarea.getId());
			row.createCell(1).setCellValue(subarea.getStartNum());
			row.createCell(2).setCellValue(subarea.getEndNum());
			row.createCell(3).setCellValue(subarea.getKeyWords());
			row.createCell(4).setCellValue(subarea.getAssistKeyWords());
			row.createCell(5).setCellValue(subarea.getArea().getName());
		}
		String filename = "分区数据.xls";
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		String newFileName = FileUtils.encodeDownloadFilename(filename, agent);
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType("application/vnd.ms-excel;charset=UTF-8");
		ServletActionContext.getResponse().setHeader("content-disposition", "filename="+newFileName);
		hssfWorkbook.write(outputStream);
		return NONE;
	}
	@Action("subareaAction_SubAreaTemplate")
	public String SubAreaTemplate() throws IOException {
		
		String templatePath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ config.getTemplateFolder()
				+ File.separator
				+ config.getSubAreaTemplate();
		FileUtil.download(templatePath, ServletActionContext.getResponse());
		return NONE;
	}
	//分区分布图
	@Action(value="subareaAction_doPie")
	public String doPie() throws IOException{
		List<Object[]> list = subareaService.doPie();
		this.list2Json(list, new String[]{});
		return NONE;
	}
}
