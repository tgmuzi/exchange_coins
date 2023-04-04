package com.zeus.modules.trx.controller;

import com.zeus.modules.trx.utils.Constant;
import com.zeus.utils.AjaxObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("trx")
public class TrxController {

    /**
     * trx账号信息
     */
    @PostMapping("/trx_Account")
    @ResponseBody
    public AjaxObject user(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        return AjaxObject.ok().data(map);
    }

}
