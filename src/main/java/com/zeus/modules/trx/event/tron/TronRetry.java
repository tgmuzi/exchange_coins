package com.zeus.modules.trx.event.tron;

import com.blockchain.scanning.chain.RetryStrategy;
import com.zeus.modules.trx.utils.TRXData;

import java.math.BigInteger;

public class TronRetry implements RetryStrategy {

    @Override
    public void retry(BigInteger bigInteger) {
        TRXData.jiaoben(bigInteger);
        System.out.println("TRON 区块高度：" + bigInteger);
    }
}