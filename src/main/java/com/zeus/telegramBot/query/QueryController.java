package com.zeus.telegramBot.query;

import com.zeus.modules.trx.entity.TrxAdress;
import com.zeus.modules.trx.service.ITrxAdressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 查询功能
 *
 */
@Component
public class QueryController {

    @Autowired
    private ITrxAdressService trxAdressService;

    public void trxAdressInsert(TrxAdress trxAdress){
        trxAdress.setCreateTime(new Date());
        trxAdressService.insert(trxAdress);
    }
}
