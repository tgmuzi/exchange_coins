package com.zeus.modules.trx.service.impl;

import com.blockchain.scanning.MagicianBlockchainScan;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.config.rpcinit.impl.TronRpcInit;
import com.zeus.modules.trx.entity.TrxBigInteger;
import com.zeus.modules.trx.event.tron.TronEventOne;
import com.zeus.modules.trx.event.tron.TronRetry;
import com.zeus.modules.trx.service.ITrxBigIntegerService;
import com.zeus.modules.trx.service.ScheduledTaskService;
import com.zeus.utils.ExecBot;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.math.BigInteger;

@Slf4j
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {
    private static Logger logger = LoggerFactory.getLogger(ScheduledTaskServiceImpl.class);

    @Autowired
    private ITrxBigIntegerService trxBigIntegerService;

    @Override
    public void All_Bot() {
        //梯子在自己电脑上就写127.0.0.1  软路由就写路由器的地址
        String proxyHost = "127.0.0.1";
        //端口根据实际情况填写，说明在上面，自己看
        int proxyPort = 1080;

        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        //注意一下这里，ProxyType是个枚举，看源码你就知道有NO_PROXY,HTTP,SOCKS4,SOCKS5;
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        DefaultBotSession defaultBotSession = new DefaultBotSession();
        defaultBotSession.setOptions(botOptions);
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(defaultBotSession.getClass());


            //需要代理
//            ExecBot bot = new ExecBot(botOptions);
//            telegramBotsApi.registerBot(bot);
            //不需代理
            ExecBot bot2 = new ExecBot();
            telegramBotsApi.registerBot(bot2);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        /**
         * 启动TRON链上交易扫描
         */
        EventThreadPool.init(5);
        MagicianBlockchainScan magicianBlockchainScan1 = tron();

    }

    /**
     * 启动TRON链上交易扫描
     */
    private  MagicianBlockchainScan tron(){
        try {
            TrxBigInteger trxBigInteger = trxBigIntegerService.getById();
            MagicianBlockchainScan magicianBlockchainScan = MagicianBlockchainScan.create()
                    .setRpcUrl(TronRpcInit.create().addRpcUrl("https://nile.trongrid.io/wallet"))
                    .setScanPeriod(1000)
                    .setBeginBlockNumber(BigInteger.valueOf(Integer.parseInt(trxBigInteger.getBigInteger())))
                    .addTronMonitorEvent(new TronEventOne())
                    .setRetryStrategy(new TronRetry());

            magicianBlockchainScan.start();

            return magicianBlockchainScan;
        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
        return null;
    }
}
