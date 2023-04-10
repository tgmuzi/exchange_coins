package com.zeus.modules.sys.service;

import com.zeus.modules.sys.entity.SysUser;
import com.baomidou.mybatisplus.service.IService;
import com.zeus.modules.trx.entity.TrxBigInteger;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muzi
 * @since 2023-04-10
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser getById();

}
