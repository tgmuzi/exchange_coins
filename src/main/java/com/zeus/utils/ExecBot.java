package com.zeus.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zeus.modules.trx.entity.CoinsVo;
import com.zeus.modules.trx.utils.Constant;
import com.zeus.modules.trx.utils.TRXData;
import com.zeus.telegramBot.BottomButton;
import com.zeus.telegramBot.BuiltInButton;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExecBot extends TelegramLongPollingBot {

    //填你自己的token和username
    private String token = "6170863910:AAGo3EGe3QSHE6-um6yeB4H4TuyOe8GoDRw";
    private String username = "AllBeings_bot";

    public ExecBot() {
        this(new DefaultBotOptions());
    }

    public ExecBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();

            Long chatId = message.getChatId();
            String lastName = message.getChat().getLastName();
            String userName = message.getChat().getUserName();
            String text = message.getText();
            switch (text) {
                case "/now":
                    break;
                case "/week":
                    break;
                case "/de":
                    try {
                        sendMessAge(token, chatId, text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "/start":
                    JSONObject reply_markup = new JSONObject();
                    JSONArray inline_keyboard = new JSONArray();
                    JSONArray jsonArray0 = getRowInlineKeyboard(new String[]{"管理我的群组", "1" + "-callbackdata"});
                    JSONArray jsonArray1 = getRowInlineKeyboard(new String[]{"合作品牌", "2" + "-callbackdata"}, new String[]{"荣誉榜单", "3" + "-callbackdata"});
                    JSONArray jsonArray2 = getRowInlineKeyboard(new String[]{"使用指南", "4" + "-callbackdata"}, new String[]{"意见箱", "5" + "-callbackdata"});
                    JSONArray jsonArray3 = getRowInlineKeyboard(new String[]{"购买广告", "6" + "-callbackdata"}, new String[]{"我的钱包", "7" + "-callbackdata"});
                    inline_keyboard.add(jsonArray0);
                    inline_keyboard.add(jsonArray1);
                    inline_keyboard.add(jsonArray2);
                    inline_keyboard.add(jsonArray3);
                    reply_markup.put("inline_keyboard", inline_keyboard);
                    //                    sendMsg(reply_markup.toString(),chatId);
                    try {
                        String fromText = "\uD83D\uDC4F 欢迎  *" + lastName + " *\n\n" + "UID:`" + chatId + "`";
                        sendDmiPhoto(token, chatId, fromText, reply_markup);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "查询":
                    String fromText = "@" + userName + "\n" + "请输入TRC地址：";
                    try {
                        sendMsg(chatId, fromText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "查汇率":
                    String info = TRXData.getsymbol(Constant.BASE_API_URL, Constant.TRX_ADDRESS,Constant.USDT_ADDRESS);
                    JSONObject jsonObject = JSONObject.parseObject(info);
                     fromText = "@" + userName + "\n 当前汇率为：\n" + jsonObject.get("price") + "\n" + "以上更新时间为："+ new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                    try {
                        sendMsg(chatId, fromText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "已绑定地址":
                     fromText = "@" + userName + "\n" + "请输入TRC地址：";
                    try {
                        sendMsg(chatId, fromText);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    if (text.length() >10){
                        String infoAccount = TRXData.getAccount(Constant.TRX_API, text);
                        CoinsVo coinsVo = JSONObject.parseObject(infoAccount, CoinsVo.class);
                        fromText = "地址：\n" + coinsVo.getAddress() + "\n" + "当前TRX余额："+ coinsVo.getBalance()+ "\n" + "免费带宽已使用量："+ coinsVo.getFree_net_usage();
                        try {
                            sendMsg(chatId, fromText);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        sendBottomButton(chatId);
                    }
                    break;
            }
        } else {
            String data = update.getCallbackQuery().getData().split("-")[0];
            Long chatId = update.getCallbackQuery().getFrom().getId();
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            String lastName = update.getCallbackQuery().getFrom().getLastName();
            String fromText = "\uD83D\uDC4F 欢迎  *" + lastName + " *\n\n" + "UID:`" + chatId + "`";

            JSONObject reply_markup = new JSONObject();
            JSONArray inline_keyboard = new JSONArray();
            JSONArray jsonArray0 = getRowInlineKeyboard(new String[]{"管理我的群组", "1" + "-callbackdata"});
            JSONArray jsonArray1 = getRowInlineKeyboard(new String[]{"合作品牌", "2" + "-callbackdata"}, new String[]{"荣誉榜单", "3" + "-callbackdata"});
            JSONArray jsonArray2 = getRowInlineKeyboard(new String[]{"使用指南", "4" + "-callbackdata"}, new String[]{"意见箱", "5" + "-callbackdata"});
            inline_keyboard.add(jsonArray0);
            inline_keyboard.add(jsonArray1);
            inline_keyboard.add(jsonArray2);
            reply_markup.put("inline_keyboard", inline_keyboard);
            switch (data) {
                case "1":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "2":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "3":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "4":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "5":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "6":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                case "7":
                    System.out.println(update.getCallbackQuery().getData());
                    editMessageText(token, chatId, messageId, fromText, reply_markup);
                    break;
                default:
            }
        }
    }

    /**
     * 构造一行内联键盘
     */
    public JSONArray getRowInlineKeyboard(String[]... keyboards) {
        JSONArray jsonArray = new JSONArray();
        for (String[] keyboard : keyboards) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("text", keyboard[0]);
            jsonObject.put("callback_data", keyboard[1]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 构造一行内联键盘
     */
    public ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("1");
        keyboardButton.setRequestContact(false);
        keyboardButton.setRequestLocation(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(keyboardButton);
        keyboardRows.add(keyboardRow);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }

    //回复普通文本消息
    public void sendMsg(long chatId, String fromText) {
        //创建一个SendMessage对象
        SendMessage message = new SendMessage();
        message.setChatId(chatId + "");
        message.setText(fromText);
        //使用Bot的execute方法发送消息
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
        //内置按钮
    public void sendInlineKeyboardButton(long chatId) {
        InlineKeyboardMarkup keyboardRows = BuiltInButton.sendInlineKeyboardButton(chatId);
        SendMessage message = SendMessage.builder().text("暂时停用此功能！").chatId(chatId+"").build();
        message.setReplyMarkup(keyboardRows);
        //使用Bot的execute方法发送消息
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
        //底部菜单
    public void sendBottomButton(long chatId) {
        ReplyKeyboardMarkup keyboardRows = BottomButton.sendBottomButton();
        SendMessage message = SendMessage.builder().text("暂时停用此功能！").chatId(chatId+"").build();
        message.setReplyMarkup(keyboardRows);
        //使用Bot的execute方法发送消息
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public String executeLinuxCmd(String cmd) {
        System.out.println("执行命令[ " + cmd + " ]");
        Runtime run = Runtime.getRuntime();
        try {
            Process process = run.exec(cmd);
            String line;
            BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer out = new StringBuffer();
            while ((line = stdoutReader.readLine()) != null) {
                out.append(line + "\n");
            }
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            process.destroy();
            return out.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendDmiPhoto(String botToken, Long chatId, String text, JSONObject replyMarkup) throws Exception {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";
        JSONObject json = new JSONObject();
        json.put("chat_id", chatId);
        json.put("text", text);
        json.put("parse_mode", "MarkdownV2");
        json.put("disable_web_page_preview", true);
        json.put("disable_notification", true);
//        json.put("reply_markup", replyMarkup);
        HttpUtils.postJSON(url, json.toJSONString());

    }

    private void sendMessAge(String botToken, Long chatId, String text) throws Exception {
        String url = "http://127.0.0.1:1080/bot" + botToken + "/sendMessage";
        JSONObject json = new JSONObject();
        json.put("chat_id", chatId);
        json.put("text", text);
        json.put("parse_mode", "MarkdownV2");
        json.put("disable_web_page_preview", true);
        json.put("disable_notification", true);
        HttpUtils.postJSON(url, json.toJSONString());

    }

    private void editMessageText(String botToken, Long chatId, Integer messageId, String text, JSONObject replyMarkup) {
        String url = "https://api.telegram.org/bot" + botToken + "/editMessageText";
        JSONObject json = new JSONObject();
        json.put("chat_id", chatId);
        json.put("message_id", messageId);
        json.put("text", text);
        json.put("parse_mode", "MarkdownV2");
        json.put("disable_web_page_preview", true);
        json.put("reply_markup", replyMarkup);
        try {
            HttpUtils.postJSON(url, json.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


