package com.zeus.modules.trx.event;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronBlockRawDataModel;
import com.blockchain.scanning.chain.model.tron.TronContractModel;
import com.blockchain.scanning.chain.model.tron.TronValueModel;
import com.blockchain.scanning.monitor.TronMonitorEvent;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.utils.Arith;
import com.zeus.modules.trx.utils.ByteUtils;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TronEventOne implements TronMonitorEvent {
    private static String privateKey = "369e777966f6113bf65fdb4518f0721c88c4771ec79e37a4dc849fee346fd8ba";//发起人秘钥

    /**
     * transactionModel 对象里包含此条交易的所有信息
     */
    @Override
    public void call(TransactionModel transactionModel) {
        List<TronContractModel> tronContractModels = transactionModel.getTronTransactionModel().getRawData().getContract();
        if ("SUCCESS".equals(transactionModel.getTronTransactionModel().getRet().get(0).getContractRet())) {
            for (TronContractModel tronContractModel : tronContractModels) {
                TronValueModel tronValueModel = tronContractModel.getParameter().getValue();
                if (tronValueModel.getToAddress() == null) {
                    System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
                    // 查询 TEXUSDT汇率
                    String huilv = TRXData.getTrxsymbol(Constant.BINANCE_API);
                    // 查询 TEXUSDT汇率进行编译
                    BinanceDaiBi binanceDaiBis = JSONObject.parseObject(huilv, BinanceDaiBi.class);
                    //通过扫描的交易哈希ID查询订单
                    String record = TRXData.GetTransactionById(Constant.TRX_API, transactionModel.getTronTransactionModel().getTxID());
                    //编译
                    JSONObject jsonObject = JSONObject.parseObject(record);
                    //编译
                    jsonObject = JSONObject.parseObject(jsonObject.get("raw_data").toString());
                    //编译
                    JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("contract").toString());
                    //编译
                    jsonObject = JSONObject.parseObject(jsonArray.get(0).toString());
                    jsonObject = JSONObject.parseObject(jsonObject.get("parameter").toString());//编译
                    jsonObject = JSONObject.parseObject(jsonObject.get("value").toString());//编译

                    String contract_address = TRXData.toBase58Address(jsonObject.get("contract_address").toString());//交易合约地址
                    if (Constant.CONTRACT_ADDRESS.equals(contract_address)) {
                        //通过订单查询的交易哈希秘钥进行解析
                        Map<String, Object> map = TRXData.dataDecodingTutorial(jsonObject.get("data").toString());
                        // 获取交易金额
                        BigDecimal amountNew = new BigDecimal(map.get("amount").toString());
                        amountNew = Arith.divide(amountNew, BigDecimal.valueOf(binanceDaiBis.getPrice()));
                        String fromAddress = TRXData.toBase58Address(jsonObject.get("owner_address").toString()); // 发送人
                        String toAddress = map.get("toAddress").toString();//接收人
                        if (Constant.TRX_ADDRESS.equals(toAddress)) {
                            Number num = Float.parseFloat(amountNew.toString());
                            int oamount = num.intValue();
                            long amount = Long.valueOf(oamount);
                            /**
                             * TRX转账
                             */
                            TRXData.transferTRX(privateKey, toAddress, fromAddress, amount);
                        }
                    }
                }
//            if (Constant.TRX_ADDRESS_2.equals(ByteUtils.toBase58Address(tronValueModel.getToAddress()))){
//                System.out.println("TRON 成功了！！！");
//                System.out.println("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
//                System.out.println("TRON, ownerAddress: " + ByteUtils.toBase58Address(tronValueModel.getOwnerAddress()));
//                System.out.println("TRON, toAddress: " + ByteUtils.toBase58Address(tronValueModel.getToAddress()));
//                System.out.println("TRON, amount: " + tronValueModel.getAmount());
//            }
            }
        }
    }
}
