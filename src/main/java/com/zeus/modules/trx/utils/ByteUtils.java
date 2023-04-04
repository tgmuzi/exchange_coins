package com.zeus.modules.trx.utils;

import com.zeus.utils.HttpUtils;
import org.bitcoinj.core.Base58;
import org.bitcoinj.core.Sha256Hash;

import java.util.regex.Pattern;

public class ByteUtils {


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

    private static String encode58Check(byte[] input) {
        byte[] hash0 = Sha256Hash.hash(input);
        byte[] hash1 = Sha256Hash.hash(hash0);
        byte[] inputCheck = new byte[input.length + 4];
        System.arraycopy(input, 0, inputCheck, 0, input.length);
        System.arraycopy(hash1, 0, inputCheck, input.length, 4);
        return Base58.encode(inputCheck);
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
}
