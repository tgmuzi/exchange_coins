package com.zeus.modules.sys.service;

import com.zeus.modules.sys.entity.SysUser;
import com.zeus.modules.sys.entity.SysUserToken;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muzi
 * @since 2023-04-10
 */
public interface ISysUserTokenService extends IService<SysUserToken> {
    SysUserToken getById();

}
