package com.zeus.modules.trx.service;

import com.zeus.modules.trx.entity.TrxAdress;
import com.baomidou.mybatisplus.service.IService;
import com.zeus.modules.trx.entity.TrxBigInteger;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author muzi
 * @since 2023-04-09
 */
public interface ITrxAdressService extends IService<TrxAdress> {
    TrxBigInteger getById();

}
