package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.action.common.CommonAction;
import com.itheima.crm.service.impl.Customer;
import com.itheima.crm.service.impl.CustomerServiceImpl;

@Controller("fixedAreaAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")
public class FixedAreaAction extends CommonAction<FixedArea> {
	@Autowired
	private FixedAreaService fixedAreaService;
	@Autowired
    private CustomerServiceImpl customerProxy;
	private List<Integer> customerIds;
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	private Integer courierId;
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	private Integer takeTimeId;
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	
	@Action("fixedAreaAction_findByPage")
	public String findByPage() throws IOException {
		Pageable pageable=new PageRequest(page-1, rows);
		Specification<FixedArea> spec=new Specification<FixedArea>() {
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return null;
			}
		};
		Page<FixedArea> page=fixedAreaService.findByPage(spec,pageable);
		page2Json(page, new String [] {"fixedArea","fixedAreas","subareas"});
		return NONE;
	}
	
	@Action("fixedAreaAction_save")
	public String save() {
		fixedAreaService.save(model);
		return SUCCESS;
	}
	@Action("fixedAreaAction_findByFixedAreaIdIsNull")
	public String findByFixedAreaIdIsNull() throws IOException {
		List<Customer> list = customerProxy.findByFixedAreaIdIsNull();
		list2Json(list, null);
		return NONE;
	}
	@Action("fixedAreaAction_findByFixedAreaId")
	public String findByFixedAreaId() throws IOException {
		List<Customer> list = customerProxy.findByFixedAreaId(model.getId());
		list2Json(list, null);
		return NONE;
	}
	@Action("fixedAreaAction_assignCustomers2FixedArea")
	public String assignCustomers2FixedArea() throws IOException {
		customerProxy.assignCustomers2FixedArea(model.getId(),customerIds);
		return SUCCESS;
	}
	@Action("fixedAreaAction_associationCourierToFixedArea")
	public String associationCourierToFixedArea() throws IOException {
		fixedAreaService.associationCourierToFixedArea(model.getId(),takeTimeId,courierId);
		return SUCCESS;
	}
	@Action("fixedAreaAction_findSubareasById")
	public String findSubareasById() throws IOException {
		FixedArea fixedArea= fixedAreaService.findById(model.getId());
		if (fixedArea!=null) {
			Set<SubArea> subareas = fixedArea.getSubareas();
			List<SubArea> list = new ArrayList<SubArea>(subareas);
			list2Json(list, new String[]{"fixedArea","subareas"});
		}
		return NONE;
	}
}
