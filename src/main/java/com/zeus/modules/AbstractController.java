package com.zeus.modules;

import com.zeus.config.RemotePropertiesConfig;
import com.zeus.modules.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller公共组件
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RemotePropertiesConfig remotePropertiesConfig;

	public String getAdminPath() {
		return remotePropertiesConfig.getAdminPath();
	}
	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected String getUserCode() {
		return getUser().getUserName();
	}

	protected String getContextPath() {
		return request.getContextPath();
	}

	protected HttpServletRequest getHttpServletRequest() {
		return request;
	}
}
