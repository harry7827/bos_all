package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.common.CommonAction;

@Controller("userAction")
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class UserAction extends CommonAction<User> {
	@Resource
	private UserService userService;
	private String checkCode;
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	private String ids;
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action(value="userAction_login",results= {@Result(name="success",location="/index.html",type="redirect"),
			@Result(name="fail",location="/login.html",type="redirect")})
	public String login() throws IOException {
		try {
			Subject subject = SecurityUtils.getSubject();
			String key = (String) subject.getSession().getAttribute("key");
			if (StringUtils.isNoneBlank(checkCode,model.getUsername(),model.getPassword()) && checkCode.equals(key)) {
				UsernamePasswordToken token=new UsernamePasswordToken(model.getUsername(), model.getPassword());
				subject.login(token);
				return "success";
			}
		} catch (InvalidSessionException e) {
			e.printStackTrace();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return "fail";
	}
	@Action(value="userAction_logout",results= {
			@Result(name="fail",location="/login.html",type="redirect")})
	public String logout() throws IOException {
		SecurityUtils.getSubject().logout();
		return "fail";
	}
	@Action(value="userAction_pageQuery")
	public String pageQuery() throws IOException {
		Pageable pageable = new PageRequest(page-1, rows);
		Page<User> pageUser = userService.pageQuery(pageable);
		page2Json(pageUser, new String[]{"roles"});
		return NONE;
	}
	@Action(value="userAction_save",results= {
			@Result(name="userlist",location="/pages/system/userlist.html",type="redirect")})
	public String save() throws IOException {
		userService.save(model);
		return "userlist";
	}
	@Action(value="userAction_delete",results= {
			@Result(name="userlist",location="/pages/system/userlist.html",type="redirect")})
	public String delete() throws IOException {
		userService.delete(ids);
		return "userlist";
	}
}
