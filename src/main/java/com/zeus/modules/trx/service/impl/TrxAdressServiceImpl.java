package com.zeus.modules.trx.service.impl;

import com.zeus.modules.trx.entity.TrxAdress;
import com.zeus.modules.trx.dao.TrxAdressMapper;
import com.zeus.modules.trx.entity.TrxBigInteger;
import com.zeus.modules.trx.service.ITrxAdressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author muzi
 * @since 2023-04-09
 */
@Service
public class TrxAdressServiceImpl extends ServiceImpl<TrxAdressMapper, TrxAdress> implements ITrxAdressService {

    @Override
    public TrxBigInteger getById() {
        return null;
    }
}
