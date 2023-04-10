package com.zeus.modules.trx.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blockchain.scanning.chain.model.TransactionModel;
import com.blockchain.scanning.chain.model.tron.TronContractModel;
import com.blockchain.scanning.chain.model.tron.TronValueModel;
import com.google.protobuf.ByteString;
import com.zeus.config.RemotePropertiesConfig;
import com.zeus.modules.trx.entity.BinanceDaiBi;
import com.zeus.modules.trx.entity.BinancePair;
import com.zeus.modules.trx.entity.TrxBigInteger;
import com.zeus.modules.trx.service.ITrxBigIntegerService;
import com.zeus.utils.HttpUtils;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.trident.abi.TypeDecoder;
import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.exceptions.IllegalException;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;
import org.tron.trident.utils.Base58Check;
import org.web3j.utils.Convert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.tron.trident.core.ApiWrapper.parseAddress;

@Component
public class TRXData {
        protected static Logger logger = LoggerFactory.getLogger(TRXData.class);
        private static RemotePropertiesConfig remotePropertiesConfig;
        @Autowired
        private RemotePropertiesConfig remoteProperties;
        private static ITrxBigIntegerService iTrxBigIntegerService;
        @Autowired
        private ITrxBigIntegerService trxBigIntegerService;
        @PostConstruct
        public void beforeInit() {
            remotePropertiesConfig = remoteProperties;
            iTrxBigIntegerService = trxBigIntegerService;
        }
//        //main net, using TronGrid
//        ApiWrapper wrapper = ApiWrapper.ofMainnet("hex private key", "API key");
//        //Shasta test net, using TronGrid
//        ApiWrapper wrapper = ApiWrapper.ofShasta("hex private key");
//        //Nile test net, using a node from Nile official website
//        ApiWrapper wrapper = ApiWrapper.ofNile("hex private key");

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
                BigDecimal amountNew = new BigDecimal(1);
                amountNew = Arith.divide(amountNew, BigDecimal.valueOf(binanceDaiBi.getPrice()));
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
     * @param fromAccount            转账的账户地址
     * @param toAccount              转到的账户地址
     * @param sunAmount              转账额度
     * @return 转账成功后的交易哈希值
     */
    public static String transferTRX(String fromAccount, String toAccount, long sunAmount) {
        String hashNumber;
        ApiWrapper wrapper = ApiWrapper.ofNile(remotePropertiesConfig.getPrivateKey());
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
     * 通过交易哈希ID获取交易详情
     *
     * @param pathUrl 网络地址
     * @param value 哈希ID
     */
    public static String GetTransactionById(String pathUrl, String value) {
        Map<String, Object> map = new TreeMap<>();
        map.put("value", value);
        return HttpUtils.postRequest(pathUrl + Constant.TRX_GETTRANSACTIONBYID, jsonMap(map));
    }
    /**
     * 自动交易
     *
     * @param transactionModel 返回参数
     */
    public static void GetTransaction(TransactionModel transactionModel) {
        List<TronContractModel> tronContractModels = transactionModel.getTronTransactionModel().getRawData().getContract();
        if ("SUCCESS".equals(transactionModel.getTronTransactionModel().getRet().get(0).getContractRet())) {
            for (TronContractModel tronContractModel : tronContractModels) {
                TronValueModel tronValueModel = tronContractModel.getParameter().getValue();
                if (tronValueModel.getToAddress() == null) {
                    logger.info("TRON, txID: " + transactionModel.getTronTransactionModel().getTxID());
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
                    if(!"TriggerSmartContract".equals(jsonObject.get("type"))){continue;}
                    jsonObject = JSONObject.parseObject(jsonObject.get("parameter").toString());//编译
                    jsonObject = JSONObject.parseObject(jsonObject.get("value").toString());//编译

                    String contract_address = TRXData.toBase58Address(jsonObject.get("contract_address"));//交易合约地址
                    if (remotePropertiesConfig.getContractAddress().equals(contract_address)) {
                        //通过订单查询的交易哈希秘钥进行解析
                        Map<String, Object> map = TRXData.dataDecodingTutorial(jsonObject.get("data").toString());
                        // 获取交易金额
                        BigDecimal amountNew = new BigDecimal(map.get("amount").toString());
                        amountNew = Arith.divide(amountNew, BigDecimal.valueOf(binanceDaiBis.getPrice()));
                        String fromAddress = TRXData.toBase58Address(jsonObject.get("owner_address").toString()); // 发送人
                        String toAddress = map.get("toAddress").toString();//接收人
                        if (remotePropertiesConfig.getTrxAddress().equals(toAddress)) {
                            Number num = Float.parseFloat(amountNew.toString());
                            int oamount = num.intValue();
                            long amount = Long.valueOf(oamount);
                            /**
                             * TRX转账
                             */
                            TRXData.transferTRX(toAddress, fromAddress, amount);
                        }
                    }
                }
            }
        }
    }

    /**
     * 参数套壳
     */
    public static String jsonMap(Map<String, Object> params) {
        JsonConfig jsonConfig = new JsonConfig();
        net.sf.json.JSONObject taskArray = net.sf.json.JSONObject.fromObject(params, jsonConfig);
        return taskArray.toString();
    }

    public static void jiaoben(BigInteger bigInteger){
        TrxBigInteger trxBigInteger  = iTrxBigIntegerService.getById();
        trxBigInteger.setBigInteger(bigInteger.toString());
        trxBigInteger.setCreateTime(new Date());
        iTrxBigIntegerService.updateById(trxBigInteger);
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
//        String fromAddress = "TE5mvcKbHyacJu5B7czqwcQcU9X9rL6w7A"; //发起人
//        String toAddress = "THccoKjASTyEMmKL5zTNNzR9VDoRz8GSb5";//接收人
        /**
         * 获取代币之间的汇率
         */
//        record = getsymbol(Constant.BINANCE_API,Constant.DAIBI_USDT_ADDRESS);
        /**
         * 获取用户信息
         */
//        record = GetTransactionById(Constant.TRX_API, "6100006f5774212a75ef84dbb1bd77bcd8028e3852fbececa48316a57f0917ae");
//        System.out.println(record);
//        CoinsVo coinsVo = JSONObject.parseObject(record, CoinsVo.class);
        /**
         * TRX转账
         */
//        record = transferTRX(privateKey, fromAddress, toAddress, amount);
//        String huilv = getTrxsymbol(Constant.BINANCE_API);
//        BinanceDaiBi binanceDaiBis = JSONObject.parseObject(huilv,BinanceDaiBi.class);
        record = GetTransactionById(Constant.TRX_API, "2c889cc68526a6f6511eb42a4f88d195287d646f68e6b06d7e1a2a5b17f597e5");
        System.out.println(record);
//        JSONObject jsonObject = JSONObject.parseObject(record);
//        jsonObject=JSONObject.parseObject(jsonObject.get("raw_data").toString());
//        JSONArray jsonArray =JSONArray.parseArray(jsonObject.get("contract").toString());
//        jsonObject=JSONObject.parseObject(jsonArray.get(0).toString());
//        jsonObject=JSONObject.parseObject(jsonObject.get("parameter").toString());
//        jsonObject=JSONObject.parseObject(jsonObject.get("value").toString());
//        Map<String,Object> map = dataDecodingTutorial(jsonObject.get("data").toString());
//        BigDecimal amountNew = new BigDecimal(map.get("amount").toString());
//        amountNew = Arith.divide(amountNew, BigDecimal.valueOf(binanceDaiBis.getPrice()));
//        String fromAddress = TRXData.toBase58Address(jsonObject.get("owner_address").toString());
//        String toAddress = map.get("toAddress").toString();
//        BigDecimal usdt = Convert.fromWei(amountNew, Convert.Unit.MWEI);// 转换成USDT金额格式
//        Number num = Float.parseFloat(amountNew.toString());
//        int oamount = num.intValue();
//        long amount = Long.valueOf(oamount);
//        String contract_address = TRXData.toBase58Address(jsonObject.get("contract_address").toString());//交易合约地址
//        System.out.println(jsonObject.get("data").toString());
//        System.out.println("owner_address:"+toBase58Address(jsonObject.get("owner_address").toString()));
//        System.out.println("toAddress:"+map.get("toAddress").toString());
//        System.out.println("contract_address:"+toBase58Address(jsonObject.get("contract_address").toString()));
//        System.out.println("amount:"+usdt);



    }

    /**
     * 哈希交易data数据解析
     * @param DATA
     * @return
     */
    public static Map<String,Object> dataDecodingTutorial(String DATA) {
        Map<String,Object> map = new TreeMap<>();
        Address rawRecipient = TypeDecoder.decodeAddress(DATA.substring(8,72)); //recipient address
        String recipient = rawRecipient.toString();
        Uint256 rawAmount = TypeDecoder.decodeNumeric(DATA.substring(72,136), Uint256.class); //amount
        BigInteger amount = rawAmount.getValue();
        map.put("amount",amount);
        map.put("toAddress",recipient);
        return map;
    }

    /**
     * TRON  HEX 地址转换 Base58
     * @param address
     * @return
     */
    public static String toBase58Address(Object address) {
        if (StringUtils.isNotBlank(address+"")){
            ByteString rawAddress = parseAddress(address.toString());
            return Base58Check.bytesToBase58(rawAddress.toByteArray());
        }else {
            return null;
        }
    }
}
