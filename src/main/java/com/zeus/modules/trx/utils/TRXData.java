package com.zeus.modules.trx.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.entity.BinancePair;
import com.zeus.utils.HttpUtils;
import net.sf.json.JsonConfig;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TRXData {
//    //main net, using TronGrid
//    ApiWrapper wrapper = ApiWrapper.ofMainnet("hex private key", "API key");
//    //Shasta test net, using TronGrid
//    ApiWrapper wrapper = ApiWrapper.ofShasta("hex private key");
//    //Nile test net, using a node from Nile official website
//    ApiWrapper wrapper = ApiWrapper.ofNile("hex private key");

    /**
     * 通过Bianca获取法币汇率
     *
     */
    public static String getsymbol(String pathUrl, String currency) {
        String huilv="";
        String parame = pathUrl.concat("bapi/fiat/v3/public/fiatpayment/sell/get-fiat-list").concat("?cryptoCurrency=").concat(currency);
        String fabi = HttpUtils.get(parame);
        JSONObject jsonObject =JSONObject.parseObject(fabi);
        jsonObject =JSONObject.parseObject(jsonObject.get("data").toString());
        List<BinancePair> binancePairs = JSONArray.parseArray(jsonObject.get("fiatList").toString(),BinancePair.class);
        Iterator<BinancePair> it = binancePairs.iterator();
        while (it.hasNext())
        {
            BinancePair binancePair = it.next();
            if (!"CNY".equals(binancePair.getAssetCode()) && !"USD".equals(binancePair.getAssetCode()))
            {
                it.remove();
            }else {
                huilv += binancePair.getAssetCode() + ": `" + binancePair.getQuotation()+"`\n";
            }
        }
        String parame1 = pathUrl.concat("api/v3/ticker/price");
        String daibi = HttpUtils.get(parame1);
        List<BinanceDaiBi> binanceDaiBis = JSONArray.parseArray(daibi,BinanceDaiBi.class);
        Iterator<BinanceDaiBi> it1 = binanceDaiBis.iterator();
        while (it1.hasNext())
        {
            BinanceDaiBi binanceDaiBi = it1.next();
            if (!"ETHUSDT".equals(binanceDaiBi.getSymbol())&& binanceDaiBi.getSymbol().indexOf("BTCUSDT")<0 && binanceDaiBi.getSymbol().indexOf("TRXUSDT")<0)
            {
                it1.remove();
            }else {
                huilv += binanceDaiBi.getSymbol().replace("USDT","") + ": `" + doubleFormatNumber((1/ binanceDaiBi.getPrice()))+"`\n";
            }
        }
        return huilv;
    }
    /**
     * 通过Bianca获取代币TRX汇率
     *
     */
    public static String getTrxsymbol(String pathUrl) {
        String parame = pathUrl.concat("api/v3/ticker/price").concat("?symbol=").concat(Constant.DAIBI_TRX_ADDRESS).concat(Constant.DAIBI_USDT_ADDRESS);
        return HttpUtils.get(parame);
    }
    /**
     * 通过账户地址获取账户信息。
     *
     * @param pathUrl 网络地址
     * @param address 账户地址
     */
    public static String getAccount(String pathUrl, String address) {
        Map<String, Object> map = new TreeMap<>();
        map.put("address", address);
        map.put("visible", true);
        return HttpUtils.postRequest(pathUrl + Constant.TRX_ACCOUNT, jsonMap(map));
    }

    public static String getAccountResource(String ownerAddressPrivateKey, String address) {

        ApiWrapper wrapper = ApiWrapper.ofNile(ownerAddressPrivateKey);
        Response.Account object = wrapper.getAccount(address);
        Map<String, Object> map = new TreeMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        map.put("accountName", object.getAccountName()); //账号
        map.put("balance", object.getBalance()); //余额
        Date date = new Date(object.getCreateTime());
        map.put("createTime", formatter.format(date)); // 创建时间
        map.put("latestOprationTime", formatter.format(new Date(object.getLatestOprationTime()))); //最新操作时间
        return map.toString();
    }

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

    /**
     * 参数套壳
     */
    public static String jsonMap(Map<String, Object> params) {
        JsonConfig jsonConfig = new JsonConfig();
        net.sf.json.JSONObject taskArray = net.sf.json.JSONObject.fromObject(params, jsonConfig);
        return taskArray.toString();
    }
    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     * 保留两位小数向下取整
     * @param value
     * @return Sting
     */
    public static String doubleFormatNumber(double value) {
        if(value != 0.00){
            if ((int)value == value)
            {
                DecimalFormat df = new DecimalFormat("#############");
                return df.format(value);
            }else {
                DecimalFormat df = new DecimalFormat("#0.#######");
                df.setRoundingMode(RoundingMode.DOWN);
                return df.format(value);
            }

        }else{
            return "0";
        }

    }
    public static void main(String[] args) throws Exception {
        String record = "";
        String privateKey = "369e777966f6113bf65fdb4518f0721c88c4771ec79e37a4dc849fee346fd8ba";//发起人秘钥
        String fromAddress = "TE5mvcKbHyacJu5B7czqwcQcU9X9rL6w7A"; //发起人
        String toAddress = "THccoKjASTyEMmKL5zTNNzR9VDoRz8GSb5";//接收人
        long amount = 1000000L;
        /**
         * 获取代币之间的汇率
         */
//        record = getsymbol(Constant.BINANCE_API,Constant.DAIBI_USDT_ADDRESS);
        record = getTrxsymbol(Constant.BINANCE_API);
        /**
         * 获取用户信息
         */
//        record = getAccount(Constant.TRX_API, fromAddress);
//        CoinsVo coinsVo = JSONObject.parseObject(record, CoinsVo.class);
        /**
         * TRX转账
         */
//        record = transferTRX(privateKey, fromAddress, toAddress, amount);


        System.out.println(record);
    }
}
