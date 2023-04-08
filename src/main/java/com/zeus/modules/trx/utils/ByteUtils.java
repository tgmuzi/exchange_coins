package com.zeus.modules.trx.utils;

import com.google.protobuf.ByteString;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;
import org.tron.trident.utils.Base58Check;

import java.util.regex.Pattern;

import static org.tron.trident.core.ApiWrapper.parseAddress;

public class ByteUtils {

    /** TRON
     * 加密成 HEX
     * @param addr
     * @return
     */
    public static String tryToHexAddr(String addr){
        addr = addr.replace("0x","");
        String regPattern = "[0-9a-fA-F]{42}";
        if(Pattern.matches(regPattern, addr)) {
            return addr;
        }
        return decodeBase58Address(addr);

    }
    public static String decodeBase58Address(String addr){
        byte[] bytes = decode58Check(addr);
        return toHexString(bytes);
    }
    private static byte[] decode58Check(String input) {
        byte[] decodeCheck = Base58.decode(input);
        if (decodeCheck.length <= 4) {
            return null;
        }
        byte[] decodeData = new byte[decodeCheck.length - 4];
        System.arraycopy(decodeCheck, 0, decodeData, 0, decodeData.length);
        byte[] hash0 = Sha256Hash.hash(decodeData);
        byte[] hash1 = Sha256Hash.hash(hash0);
        if (hash1[0] == decodeCheck[decodeData.length] &&
                hash1[1] == decodeCheck[decodeData.length + 1] &&
                hash1[2] == decodeCheck[decodeData.length + 2] &&
                hash1[3] == decodeCheck[decodeData.length + 3]) {
            return decodeData;
        }
        return null;
    }
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    /** TRON
     * 解密成 Base58
     * @param address
     * @return
     */
    public static String toBase58Address(String address) {
        ByteString rawAddress = parseAddress(address);
        return Base58Check.bytesToBase58(rawAddress.toByteArray());
    }
}
