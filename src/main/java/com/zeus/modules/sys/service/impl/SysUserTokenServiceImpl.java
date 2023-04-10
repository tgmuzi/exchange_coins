package com.zeus.modules.sys.service.impl;

import com.zeus.modules.sys.entity.SysUserToken;
import com.zeus.modules.sys.dao.SysUserTokenMapper;
import com.zeus.modules.sys.service.ISysUserTokenService;
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
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements ISysUserTokenService {

    @Override
    public SysUserToken getById() {
        return null;
    }
}
