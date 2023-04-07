package com.zeus.modules.trx.service.impl;

import com.blockchain.scanning.MagicianBlockchainScan;
import com.blockchain.scanning.biz.thread.EventThreadPool;
import com.blockchain.scanning.commons.config.rpcinit.impl.TronRpcInit;
import com.zeus.modules.trx.entity.TronRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.zeus.modules.trx.service.ScheduledTaskService;
import com.zeus.modules.trx.event.TronEventOne;
import com.zeus.utils.ExecBot;
import lombok.extern.slf4j.Slf4j;
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

        EventThreadPool.init(5);

        MagicianBlockchainScan magicianBlockchainScan1 = tron();

    }
    private static MagicianBlockchainScan tron(){
        try {
            MagicianBlockchainScan magicianBlockchainScan = MagicianBlockchainScan.create()
                    .setRpcUrl(TronRpcInit.create().addRpcUrl("https://nile.trongrid.io/wallet"))
                    .setScanPeriod(500)
                    .setBeginBlockNumber(BigInteger.valueOf(35670427))
                    .addTronMonitorEvent(new TronEventOne())
                    .setRetryStrategy(new TronRetry());

            magicianBlockchainScan.start();

//            Thread.sleep(20000);
            logger.info("===========");
//            magicianBlockchainScan.shutdown();

            return magicianBlockchainScan;
        } catch (Exception e) {
            logger.error("Scanning Exception", e);
        }
        return null;
    }
}
