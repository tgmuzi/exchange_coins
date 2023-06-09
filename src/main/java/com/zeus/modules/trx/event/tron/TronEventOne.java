package com.zeus.modules.trx.event.tron;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronContractModel;
import com.blockchain.scanning.chain.model.tron.TronValueModel;
import com.blockchain.scanning.monitor.TronMonitorEvent;
import com.zeus.config.RemotePropertiesConfig;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.utils.Arith;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class TronEventOne implements TronMonitorEvent {
    protected static Logger logger = LoggerFactory.getLogger(TronEventOne.class);

    @Autowired
    private RemotePropertiesConfig remoteProperties;
    /**
     * transactionModel 对象里包含此条交易的所有信息
     */
    @Override
    public void call(TransactionModel transactionModel) {
        logger.info("TRON 成功了！！！");
        logger.info("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
        TRXData.GetTransaction(transactionModel);
    }
}
