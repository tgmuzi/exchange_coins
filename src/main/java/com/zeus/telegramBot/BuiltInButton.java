package com.zeus.telegramBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class BuiltInButton {
    //内置按钮
    public static InlineKeyboardMarkup sendInlineKeyboardButton(long chatId) {
        // 创建按钮列表
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();
        List<InlineKeyboardButton> row3 = new ArrayList<>();
        InlineKeyboardButton button1 = InlineKeyboardButton.builder().text("查询1").build();
        button1.setCallbackData("查询");
        row1.add(button1);
        InlineKeyboardButton button2 = InlineKeyboardButton.builder().text("监听").build();
        button2.setCallbackData("监听");
        row1.add(button2);
        InlineKeyboardButton button3 = InlineKeyboardButton.builder().text("查汇率").build();
        button3.setCallbackData("查汇率");
        row2.add(button3);
        InlineKeyboardButton button4 = InlineKeyboardButton.builder().text("已绑定地址").build();
        button4.setCallbackData("已绑定地址");
        row2.add(button4);
        InlineKeyboardButton button5 = InlineKeyboardButton.builder().text("兑换TRX").build();
        button5.setCallbackData("兑换TRX");
        row3.add(button5);
        InlineKeyboardButton button6 = InlineKeyboardButton.builder().text("客服").build();
        button6.setCallbackData("客服");
        row3.add(button6);
        buttons.add(row1);
        buttons.add(row2);
        buttons.add(row3);
        InlineKeyboardMarkup keyboardRows = new InlineKeyboardMarkup();
        keyboardRows.setKeyboard(buttons);
        return keyboardRows;
    }
}
