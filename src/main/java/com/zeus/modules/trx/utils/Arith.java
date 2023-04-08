package com.zeus.modules.trx.utils;

import sun.font.BidiUtils;

import java.math.BigDecimal;

/**
 * 金额计算工具类
 */
public class Arith {

    /**
     * 数字1
     */
    public static final BigDecimal one = new BigDecimal("1");
    /**
     * 数字0
     */
    public static final BigDecimal zero = new BigDecimal("0");
    /**
     * 默认保留6位小数
     */
    private static final int scale = 6;
    /**
     * 四舍五入
     */
    private static final int roundingMode = 4;

    /**
     * 相减
     */
    public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }

    /**
     * 相加
     */
    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
        return b1.add(b2);
    }

    /**
     * 相乘
     */
    public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
        return b1.multiply(b2).divide(one, scale, roundingMode);
    }

    /**
     * 相除
     */
    public static BigDecimal divide(BigDecimal b1, BigDecimal b2) {
        return b1.divide(b2, scale, roundingMode);
    }

    /**
     * 获取范围内随机数 十分位
     */
    public static Double randomWithinRangeTen(Double min, Double max){
        int pow = (int) Math.pow(10, 1); // 用于提取指定小数位
        return Math.floor((Math.random() * (max - min) + min) * pow) / pow;
    }
    /**
     * 获取范围内随机数 百分位
     */
    public static Double randomWithinRangeHundred(Double min, Double max){
        int pow = (int) Math.pow(100, 1); // 用于提取指定小数位
        Double n = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        return n;
    }
    /**
     * 获取范围内随机数 百分位(大于1返回1)
     */
    public static Double randomWithinRangeHundredEquip(Double min, Double max){
        int pow = (int) Math.pow(100, 1); // 用于提取指定小数位
        Double n = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
        n = n > 1 ? 1 : n;
        return n;
    }

    /**
     * 随机20位整数
     */
    public static BigDecimal UUID20(){
        int randomId= (int) (Math.random() * (9999999-1000000 + 1) ) + 1000000;
        return new BigDecimal(System.currentTimeMillis() + "" + randomId);
    }

    /**
     * double保留两位小数
     * @param d
     * @return
     */
    public static double formatDouble(double d) {
        return (double)Math.round(d*10000)/10000;
    }

    public static void main(String[] args) {
//        int i = 0;
//        int n = 0;
//        while (i<100){
//            double num = randomWithinRangeHundred(50.0, 101.8);
//            System.out.println(num);
//            if(num >= 100){
//                n++;
//            }
//            i++;
//        }
//        System.out.println(Math.round(90 * 0.03 * 1));
//        System.out.println(n);
        System.out.println(formatDouble(0.01-0.01*0.75));

        String bb = "226517668.378133";
        Number num = Float.parseFloat(bb);
        int oamount = num.intValue();
        long _cost = Long.valueOf(oamount);
        System.out.println(_cost);
    }
}
