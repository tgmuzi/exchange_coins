package com.zeus.telegramBot;

import com.alibaba.fastjson.JSONObject;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;

public class DuiHuanTRX {

    public static String trx(String lastName){
        String info = TRXData.getTrxsymbol(Constant.BINANCE_API);
        BinanceDaiBi binanceDaiBi = JSONObject.parseObject(info,BinanceDaiBi.class);
        String huilv="";
        huilv= "\uD83D\uDC4F 欢迎  *" + lastName + " *\n" +
                "\n兑换TRX地址：\n`" +  Constant.TRX_ADDRESS +"`\n * 上述地址点击可复制 * \n" +
                " * 进U即兑，全自动返TRX，一笔一回，1U起兑，当前兑换比例 \n 1U : "+TRXData.doubleFormatNumber((1/ binanceDaiBi.getPrice()))+"TRX *\n" +
                " * 请不要使用交易所转账，丢失不负责 * \n" +
                " * 注：交易经过19次网络确认，两分钟内到账 * ";
        return huilv;
    }
}
