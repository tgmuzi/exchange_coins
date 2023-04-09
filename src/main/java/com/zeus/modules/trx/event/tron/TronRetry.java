package com.zeus.modules.trx.event.tron;

import com.blockchain.scanning.chain.RetryStrategy;
import com.zeus.modules.trx.utils.TRXData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

public class TronRetry implements RetryStrategy {
    protected static Logger logger = LoggerFactory.getLogger(TronRetry.class);

    @Override
    public void retry(BigInteger bigInteger) {
        TRXData.jiaoben(bigInteger);
        logger.info("TRON 区块高度：" + bigInteger);
    }
}