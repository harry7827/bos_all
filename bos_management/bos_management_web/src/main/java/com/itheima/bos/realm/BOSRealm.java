package com.itheima.bos.realm;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.itheima.bos.dao.system.PermissionDao;
import com.itheima.bos.dao.system.RoleDao;
import com.itheima.bos.dao.system.UserDao;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.RoleService;

public class BOSRealm extends AuthorizingRealm {
	@Resource
	private UserDao userDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private PermissionDao permissionDao;
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken userpasswordToken=(UsernamePasswordToken)token;
		String username = userpasswordToken.getUsername();
		User user=userDao.findByUsername(username);
		if (user!=null) {
			String password = user.getPassword();
			SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(user, password, this.getName());
			return info;
		}
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User u = (User) principals.getPrimaryPrincipal();
		if(u.getUsername().equals("admin")){
			List<Permission> listPermission = permissionDao.findAll();
			List<Role> listRole = roleDao.findAll();
			for (Role role : listRole) {
				info.addRole(role.getKeyword());
			}
			for (Permission permission : listPermission) {
				info.addStringPermission(permission.getKeyword());
			}
		}else {
			List<Role> listRole = roleDao.findByUser(u.getId());
			List<Permission> listPermission = permissionDao.findByUser(u.getId());
			for (Role role : listRole) {
				info.addRole(role.getKeyword());
			}
			for (Permission permission : listPermission) {
				info.addStringPermission(permission.getKeyword());
			}
		}
		return info;
	}
}
