package com.zeus.modules.trx.event;

import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronBlockRawDataModel;
import com.blockchain.scanning.chain.model.tron.TronContractModel;
import com.blockchain.scanning.chain.model.tron.TronValueModel;
import com.blockchain.scanning.monitor.TronMonitorEvent;
import com.zeus.modules.trx.utils.ByteUtils;
import com.zeus.modules.trx.utils.Constant;

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
            System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
            if ("6100006f5774212a75ef84dbb1bd77bcd8028e3852fbececa48316a57f0917ae".equals(transactionModel.getTronTransactionModel().getTxID())){
                System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
                System.out.println("TRON, amount: " + tronValueModel.getAmount());
            }
            if (tronValueModel.getToAddress() == null) {continue;}
            if (Constant.TRX_ADDRESS_2.equals(ByteUtils.toBase58Address(tronValueModel.getToAddress()))){
                System.out.println("TRON 成功了！！！");
                System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
                System.out.println("TRON, ownerAddress: " + ByteUtils.toBase58Address(tronValueModel.getOwnerAddress()));
                System.out.println("TRON, toAddress: " + ByteUtils.toBase58Address(tronValueModel.getToAddress()));
                System.out.println("TRON, amount: " + tronValueModel.getAmount());
            }
        }
    }
}
