package com.zeus.modules.sys.service.impl;

import com.zeus.modules.sys.entity.SysUser;
import com.zeus.modules.sys.dao.SysUserMapper;
import com.zeus.modules.sys.service.ISysUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muzi
 * @since 2023-04-10
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getById() {
        return null;
    }
}
