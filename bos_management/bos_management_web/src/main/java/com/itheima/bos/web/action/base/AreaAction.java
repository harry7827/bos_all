package com.itheima.bos.web.action.base;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.utils.PinYin4jUtils;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("areaAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
@Result(name="success",location="/pages/base/area.html",type="redirect")
public class AreaAction extends CommonAction<Area> {
	@Autowired
	private AreaService areaService;
	@Autowired
	private StandardService standardService;
	private File areaFile;
	public void setAreaFile(File areaFile) {
		this.areaFile = areaFile;
	}
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	@Action(value="areaAction_importXls")
	public String areaAction_importXls() throws IOException {
		String flag="1";
		try {
			areaService.importXls(areaFile);
		} catch (Exception e) {
			e.printStackTrace();
			flag="0";
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().print(flag);
		return NONE;
	}
	@Action(value="areaAction_findAll")
	public String findAll() throws IOException {
		List<Area> list=null;
		if (StringUtils.isNotBlank(q)) {
			list = areaService.findByQ(q);
		}else{
			list = areaService.findAll();
		}
		list2Json(list, new String[]{"subareas"});
		return NONE;
	}
	@Action(value="areaAction_save")
	public String save() throws IOException {
		String province = model.getProvince();
		province=province.substring(0, province.length()-1);
		String district = model.getDistrict();
		district=district.substring(0, district.length()-1);
		String city = model.getCity();
		city=city.substring(0, city.length()-1);
		String [] citycodes = PinYin4jUtils.getHeadByString(province+city+district);
		String citycode = PinYin4jUtils.stringArrayToString(citycodes);
		String shortcode = PinYin4jUtils.hanziToPinyin(city,"");
		model.setCitycode(citycode);
		model.setShortcode(shortcode);
		if (model.getId()==null||"".equals(model.getId())) {
			model.setId(UUID.randomUUID().toString().replaceAll("-",""));
		}
		areaService.save(model);
		return "success";
	}
	@Action(value="areaAction_pageQuery")
	public String pageQuery() throws IOException {
		/*List<Standard> standards = standardService.findAll();
		System.err.println(standards);*/
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Area> page = areaService.pageQuery(pageable);
		page2Json(page, new String [] {"subareas"});
		return NONE;
	}
	@Action(value="areaAction_deleteBatch")
	public String deleteBatch() throws IOException {
		areaService.deleteBatch(ids);
		return "success";
	}
}
