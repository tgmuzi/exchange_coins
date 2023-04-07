package com.zeus.modules.trx.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronBlockRawDataModel;
import com.blockchain.scanning.chain.model.tron.TronContractModel;
import com.blockchain.scanning.chain.model.tron.TronValueModel;
import com.blockchain.scanning.monitor.TronMonitorEvent;

import java.util.List;

public class TronEventOne implements TronMonitorEvent {

    /**
     * transactionModel 对象里包含此条交易的所有信息
     */
    @Override
    public void call(TransactionModel transactionModel) {
        List<TronContractModel> tronContractModels = transactionModel.getTronTransactionModel().getRawData().getContract();
        for (TronContractModel tronContractModel:   tronContractModels ) {
            TronValueModel tronValueModel = tronContractModel.getParameter().getValue();
            System.out.println("TRON, toAddress: " + tronValueModel.getToAddress());
        }
        System.out.println("TRON 成功了！！！");
        System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
    }
}
