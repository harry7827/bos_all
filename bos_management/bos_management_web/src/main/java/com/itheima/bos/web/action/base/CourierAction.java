package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.common.CommonAction;


@Controller("courierAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class CourierAction extends CommonAction<Courier> {
	@Autowired
	private CourierService courierService;
	
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	@Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String save() {
		if (model !=null) {
			courierService.save(model);
		}
		return "success";
	}
	@Action(value="courierAction_deleteBatch",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String deleteBatch() {
		courierService.deleteBatch(ids);
		return "success";
	}
	@Action(value="courierAction_restoreBatch",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
	public String restoreBatch() {
		courierService.restoreBatch(ids);
		return "success";
	}
	/*@RequiresPermissions("staff")*/
	@Action(value="courierAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		final String company = model.getCompany();
		final Standard standard = model.getStandard();
		final String type = model.getType();
		final String courierNum = model.getCourierNum();
		Specification<Courier> spec=new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<Predicate>();
				if (StringUtils.isNotBlank(company)) {
					Predicate p=cb.like(root.get("company").as(String.class),"%"+company+"%");
					list.add(p);
				}
				if (StringUtils.isNotBlank(type)) {
					Predicate p=cb.equal(root.get("type").as(String.class), type);
					list.add(p);
				}
				if (StringUtils.isNotBlank(courierNum)) {
					Predicate p=cb.equal(root.get("courierNum").as(String.class), courierNum);
					list.add(p);
				}
				if (standard!=null && StringUtils.isNotBlank(standard.getName())) {
					Join<Object, Object> join=root.join("standard");
					Predicate p=cb.equal(join.get("name").as(String.class), standard.getName());
					list.add(p);
				}
				if (list.size()==0) {
					return null;
				}
				Predicate [] ps=new Predicate[list.size()];
				return cb.and(list.toArray(ps));
			}
			
		};
		
		
		Page<Courier> page = courierService.pageQuery(spec,pageable);
		page2Json(page,new String[]{"fixedAreas"});
		return NONE;
	}
	@Action(value="courierAction_listajax")
	public String findAll() throws IOException {
		List<Courier> list = courierService.findByDeltagIsNull();
		list2Json(list,new String[]{"couriers"});
		return NONE;
	}
}
