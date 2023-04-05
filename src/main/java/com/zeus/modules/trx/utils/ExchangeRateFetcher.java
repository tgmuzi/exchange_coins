package com.zeus.modules.trx.utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ExchangeRateFetcher {

    private static final String BASE_API_URL = "https://api.binance.com/api/v3/ticker/price?symbol=";

    public static void main(String[] args) throws Exception {
        String symbol = "ETHUSDT"; // 代币对
        String apiEndpoint = BASE_API_URL + symbol;

        URL url = new URL(apiEndpoint);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.toString(), JsonObject.class);
        String price = jsonObject.get("price").getAsString();

        System.out.println("Price of " + symbol + ": " + price);
    }
}
