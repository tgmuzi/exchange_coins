package com.zeus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RemotePropertiesConfig {


    @Value("${tron.trx_address:#{null}}")
    private String trxAddress;

    @Value("${tron.contract_address:#{null}}")
    private String contractAddress;

    @Value("${tron.privateKey:#{null}}")
    private String privateKey;

    public String getTrxAddress() {
        return trxAddress;
    }

    public void setTrxAddress(String trxAddress) {
        this.trxAddress = trxAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
