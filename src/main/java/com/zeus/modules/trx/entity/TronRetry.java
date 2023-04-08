package com.zeus.modules.trx.entity;

import com.blockchain.scanning.chain.RetryStrategy;

import java.math.BigInteger;

public class TronRetry implements RetryStrategy {

    @Override
    public void retry(BigInteger bigInteger) {
        System.out.println("TRON 区块高度：" + bigInteger);
    }
}