package com.zeus.modules.trx.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zeus.modules.trx.dao.TrxBigIntegerMapper;
import com.zeus.modules.trx.entity.TrxBigInteger;
import com.zeus.modules.trx.service.ITrxBigIntegerService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TrxBigIntegerServiceImpl extends ServiceImpl<TrxBigIntegerMapper, TrxBigInteger> implements ITrxBigIntegerService {

    @Autowired
    private TrxBigIntegerMapper trxBigIntegerMapper;
    @Override
    public TrxBigInteger getById() {
        return trxBigIntegerMapper.getById();
    }
}
