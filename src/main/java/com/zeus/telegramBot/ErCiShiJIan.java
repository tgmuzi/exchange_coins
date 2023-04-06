package com.zeus.telegramBot;

import com.alibaba.fastjson.JSONObject;
import com.zeus.modules.trx.entity.CoinsVo;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;

public class ErCiShiJIan {

    public static String duoci(String text){
        String infoAccount = TRXData.getAccount(Constant.TRX_API, text);
        CoinsVo coinsVo = JSONObject.parseObject(infoAccount, CoinsVo.class);
        String fromText = "地址：\n" + coinsVo.getAddress() + "\n" + "当前TRX余额："+ coinsVo.getBalance()+ "\n" + "免费带宽已使用量："+ (coinsVo.getFree_net_usage() == null ? "" : coinsVo.getFree_net_usage());
        return fromText;
    }
}
