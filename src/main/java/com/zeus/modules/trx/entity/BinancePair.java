package com.zeus.modules.trx.entity;

public class BinancePair {
    private String assetCode;
    private String assetName;
    private String assetLogo;
    private String size;
    private String quotation;

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetLogo() {
        return assetLogo;
    }

    public void setAssetLogo(String assetLogo) {
        this.assetLogo = assetLogo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BinancePair{");
        sb.append("assetCode='").append(assetCode).append('\'');
        sb.append(", assetName='").append(assetName).append('\'');
        sb.append(", assetLogo='").append(assetLogo).append('\'');
        sb.append(", size='").append(size).append('\'');
        sb.append(", quotation='").append(quotation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
