package com.zeus.telegramBot.function;

import com.alibaba.fastjson.JSONObject;
import com.zeus.config.RemotePropertiesConfig;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.entity.CoinsVo;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.utils.Convert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Component
public class DuiHuanTRX{

    private static RemotePropertiesConfig remotePropertiesConfig;
    @Autowired
    private RemotePropertiesConfig propertiesConfig;
    @PostConstruct
    public void beforeInit() {
        remotePropertiesConfig = propertiesConfig;
    }

    public static String trx(String lastName){
        String info = TRXData.getTrxsymbol(Constant.BINANCE_API);
        BinanceDaiBi binanceDaiBi = JSONObject.parseObject(info,BinanceDaiBi.class);
        String infoAccount = TRXData.getAccount(Constant.TRX_API, remotePropertiesConfig.getTrxAddress());
        CoinsVo coinsVo = JSONObject.parseObject(infoAccount, CoinsVo.class);
        BigDecimal trx = Convert.fromWei(coinsVo.getBalance(), Convert.Unit.MWEI);// 转换成USDT金额格式
        String huilv="";
        huilv= "\uD83D\uDC4F 欢迎  *" + lastName + " *\n" +
                "\n兑换TRX地址：\n`" +  remotePropertiesConfig.getTrxAddress() +"`" +
                "\n*可用余额 TRX*：\n`" +  trx +"`\n * 上述地址点击可复制 * \n" +
                " * 进U即兑，全自动返TRX，一笔一回，1U起兑，当前兑换比例 \n 1U : "+TRXData.doubleFormatNumber((1/ binanceDaiBi.getPrice()))+"TRX *\n" +
                " * 请不要使用交易所转账，丢失不负责 * \n" +
                " * 注：交易经过19次网络确认，两分钟内到账 * ";
        return huilv;
    }
}
