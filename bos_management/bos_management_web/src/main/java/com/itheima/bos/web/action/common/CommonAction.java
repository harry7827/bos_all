package com.itheima.bos.web.action.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
	private static final long serialVersionUID = -6477587368742534938L;
	//声明模型对象
	protected T model;

	@Override
	public T getModel() {
		return model;
	}
	protected Integer page;
	protected Integer rows;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	private String ids;
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	//构造方法 通过java反射获取实体对象
	public CommonAction(){
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType p=(ParameterizedType) type;
		Class clazz=(Class) p.getActualTypeArguments()[0];
		try {
			model=(T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void page2Json(Page<T> page,String [] excludes) throws IOException {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		String jsonString;
		SimplePropertyPreFilter filter=new SimplePropertyPreFilter();
		if (excludes!=null && excludes.length>=1) {
			Set<String> ecs = filter.getExcludes();
			List<String> list=new ArrayList<>();
			for (String e : excludes) {
				list.add(e);
			}
			ecs.addAll(list);
			jsonString = JSONObject.toJSONString(map,filter,SerializerFeature.DisableCircularReferenceDetect);
		}else {
			jsonString = JSONObject.toJSONString(map,SerializerFeature.DisableCircularReferenceDetect);
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonString);
	}
	public void list2Json (List list,String [] excludes) throws IOException {
		String jsonString;
		SimplePropertyPreFilter filter=new SimplePropertyPreFilter();
		if (excludes!=null && excludes.length>=1) {
			Set<String> ecs = filter.getExcludes();
			List<String> listStr=new ArrayList<>();
			for (String e : excludes) {
				listStr.add(e);
			}
			ecs.addAll(listStr);
			jsonString = JSONObject.toJSONString(list,filter,SerializerFeature.DisableCircularReferenceDetect);
		}else {
			jsonString = JSONObject.toJSONString(list,SerializerFeature.DisableCircularReferenceDetect);
		}
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonString);
		
	}

}