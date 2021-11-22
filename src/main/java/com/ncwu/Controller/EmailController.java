package com.ncwu.Controller;

import com.ncwu.Service.EmailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Random;

@RestController
public class EmailController {

    @Resource
    private EmailService emailService;

    /**
     * 发生邮箱验证码
     */
    @GetMapping("/send")
    public Boolean sendEmail(HttpSession httpSession, String email) {
        String verifyCode = verifyCode();
        httpSession.setAttribute("verifyCode", verifyCode);
        httpSession.setAttribute("email", email);
        String text = "本次验证码为：" + verifyCode + "，您正在申请注册账号，请勿泄露您的验证码";
        try {
            emailService.sendSimpleEmail(email, "注册验证码", text);
            // "验证码已经发送到邮箱请注意查收";
            return true;
        } catch (Exception ignored) {
            // "验证码发送失败";
            return false;
        }
    }

    /**
     * 生成 6 位数字验证码
     */
    private String verifyCode() {
        StringBuilder vc = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; ++i) {
            vc.append(random.nextInt(9));
        }
        return vc.toString();
    }

}