package com.zeus.modules.sys.controller;

import com.alibaba.druid.util.StringUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.zeus.exception.BusinessException;
import com.zeus.googleConfig.GoogleAuthenticator;
import com.zeus.googleConfig.QRCodeUtil;
import com.zeus.modules.AbstractController;
import com.zeus.modules.sys.entity.SysUser;
import com.zeus.modules.sys.service.ISysUserService;
import com.zeus.utils.AjaxObject;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("${adminPath}")
public class LoginController extends AbstractController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("/log")
    @ResponseBody
    public String log() {
        log.trace("This is a TRACE level message");
        log.debug("This is a DEBUG level message");
        log.info("This is an INFO level message");
        log.warn("This is a WARN level message");
        log.error("This is an ERROR level message");
        return "See the log for details";
    }
    @GetMapping(value = "/captcha.jpg", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        // 定义response输出类型为image/jpeg类型
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        // 开始生成验证码
        String text = defaultKaptcha.createText(); // 获取验证码文本内容
        System.out.println("验证码文本内容：" + text);
        // request.getSession().setAttribute("captcha", text);
//        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        BufferedImage image = defaultKaptcha.createImage(text); // 根据文本内容创建图形验证码
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream); // 输出流输出图片，格式为jpg
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 生成验证码结束
    }

    /**
     * 登录
     */
    @RequestMapping(value = { "login" }, method = RequestMethod.GET)
    public String login(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public AjaxObject login(HttpServletRequest request, HttpServletResponse response, @RequestBody SysUser sysUser)
            throws IOException {

        if (StringUtils.isEmpty(sysUser.getUserName()) || StringUtils.isEmpty(sysUser.getPassword())) {
            return AjaxObject.error("请输入用户名和密码！");
        }
        // String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        // 用户信息
        SysUser user = sysUserService.selectById(sysUser.getUserName());
        // 账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(sysUser.getPassword(), user.getSalt()).toHex())) {
            throw new BusinessException("密码错误");
        }
        return null;
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        String secretKey = GoogleAuthenticator.getSecretKey();
        System.out.println("secretKey：" + secretKey);
        String code = getCode(secretKey);
        System.out.println("code：" + code);
        return code;
    }

    @RequestMapping("/echarts")
    public String echarts(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/echarts";
    }

    @GetMapping("/main")
    public String main(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/main";
    }

    @GetMapping("/chat")
    public String chat(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/chat";
    }

    @GetMapping("/sysUser")
    public String sysUser(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/sysUser";
    }

    @GetMapping("/chat1")
    public String chat1(HttpServletRequest request, Model model) {
        Integer uuid = UUID.randomUUID().toString().replaceAll("-", "").hashCode();
        uuid = uuid < 0 ? -uuid : uuid;// String.hashCode() 值会为空
        model.addAttribute("ctx", getAdminPath() + "/");
        model.addAttribute("username", uuid);
        model.addAttribute("nickName", getRandomChar());
        return "modules/chat/newChat";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("ctx", getAdminPath() + "/");
        return "modules/sys/index";
    }

    @GetMapping("/add")
    @ResponseBody
    public String add() {
        String getSecret = GoogleAuthenticator.getSecretKey();
        return getSecret;
    }

    /**
     * 生成 Google 密钥，两种方式任选一种
     */
    @GetMapping("getSecret")
    public String getSecret() {
        return GoogleAuthenticator.getSecretKey();
    }

    /**
     * 生成二维码，APP直接扫描绑定，两种方式任选一种
     */
    @GetMapping("getQrcode")
    public void getQrcode(String name, HttpServletResponse response) throws Exception {
        // 生成二维码内容
        String qrCodeText = GoogleAuthenticator.getQrCodeText(GoogleAuthenticator.getSecretKey(), name, "");
        // 生成二维码输出
        QRCodeUtil.encode(qrCodeText, response);
    }

    /**
     * 获取code
     */
    @GetMapping("getCode")
    public String getCode(String secretKey) {
        return GoogleAuthenticator.getCode(secretKey);
    }

    /**
     * 验证 code 是否正确
     */
    @GetMapping("checkCode")
    public String checkCode(String secret, String code) {
        boolean b = GoogleAuthenticator.checkCode(secret, Long.parseLong(code), System.currentTimeMillis());
        if (b) {
            return "success";
        }
        return "error";
    }

    // 随机生成汉字
    private static char getRandomChar() {
        String str = "";
        int hightPos; //
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("错误");
        }

        return str.charAt(0);
    }
}