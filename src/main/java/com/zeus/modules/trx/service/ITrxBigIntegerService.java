package com.zeus.modules.trx.service;

import com.zeus.modules.trx.entity.TrxBigInteger;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muzi
 * @since 2023-04-09
 */
public interface ITrxBigIntegerService extends IService<TrxBigInteger> {
    TrxBigInteger getById();
}
