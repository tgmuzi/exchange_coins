package com.zeus.modules.trx.dao;

import com.zeus.modules.trx.utils.ByteUtils;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.utils.HttpUtils;
import net.sf.json.JsonConfig;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;


import java.util.HashMap;
import java.util.Map;

public class trxData {
//    //main net, using TronGrid
//    ApiWrapper wrapper = ApiWrapper.ofMainnet("hex private key", "API key");
//    //Shasta test net, using TronGrid
//    ApiWrapper wrapper = ApiWrapper.ofShasta("hex private key");
//    //Nile test net, using a node from Nile official website
//    ApiWrapper wrapper = ApiWrapper.ofNile("hex private key");


    /**
     * TRX转账
     * 创建 -> 签名 -> 广播 -> （等待上链） -> 在链上查询到交易信息
     *
     * @param ownerAddressPrivateKey 转账的账户私钥
     * @param fromAccount            转账的账户地址
     * @param toAccount              转到的账户地址
     * @param sunAmount              转账额度
     * @return 转账成功后的交易哈希值
     */
    public static String transferTRX(String ownerAddressPrivateKey, String fromAccount, String toAccount, long sunAmount) {
        String hashNumber;
        ApiWrapper wrapper = ApiWrapper.ofNile(ownerAddressPrivateKey);
        try {
            Response.TransactionExtention transaction = wrapper.transfer(fromAccount, toAccount, sunAmount); //创建交易
            Chain.Transaction signedTxn = wrapper.signTransaction(transaction); //签名交易
            long l = wrapper.estimateBandwidth(signedTxn); //计算交易所需的带宽
            hashNumber = wrapper.broadcastTransaction(signedTxn); //广播交易
        } catch (IllegalException e) {
            throw new RuntimeException(e);
        }
        return hashNumber;
    }

    public static void main(String[] args) throws Exception {
        String record = "";
        String privateKey = "369e777966f6113bf65fdb4518f0721c88c4771ec79e37a4dc849fee346fd8ba";//发起人秘钥
        String owner_address = "TE5mvcKbHyacJu5B7czqwcQcU9X9rL6w7A"; //发起人
        String to_address = "THccoKjASTyEMmKL5zTNNzR9VDoRz8GSb5";//接收人
        long amount = 1000000L;
        record = transferTRX(privateKey, owner_address, to_address, amount);

        System.out.println(record);
    }
}
