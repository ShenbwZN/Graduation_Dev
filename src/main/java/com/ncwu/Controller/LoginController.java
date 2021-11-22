package com.ncwu.Controller;

import com.ncwu.Entity.User;
import com.ncwu.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 来登录页面
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login";
    }

    /**
     * 邮箱密码登录
     */
    @PostMapping("/login")
    public String login(User user, HttpSession httpSession, Model model) {
        String email = user.getEmail();
        String password = user.getPassword();

        User loginUser;
        // 邮箱密码都不是空的时候查找是否存在该账号
        if (!email.trim().equals("") && !password.trim().equals("")) {
            loginUser = userService.getUserByEmailAndPwd(email, password);
        } else {
            model.addAttribute("msg", "登录信息输入有误！");
            return "login";
        }
        if (loginUser != null) {
            // 登录成功用户保存起来
            httpSession.setAttribute("loginUser", loginUser);
            // 登录成功 重定向； 防止表单重复提交
            return "redirect:/main";
        } else {
            model.addAttribute("msg", "账号或密码不正确");
            return "login";
        }
    }

    /**
     * 验证码登录
     */
    @PostMapping("/loginCode")
    public String loginByCode(String email, String verifyCode, HttpSession session, Model model) {
        Object code = session.getAttribute("verifyCode");
        Object email_vc = session.getAttribute("email");
        if (email_vc == null || code == null) {
            model.addAttribute("msg", "请重新获取验证码！");
            return "login";
        }
        if (email.equals(email_vc.toString()) && code.toString().equals(verifyCode)) {
            User user = userService.getUserByEmail(email);
            session.setAttribute("loginUser", user);
            session.setAttribute("verifyCode", null);
            session.setAttribute("email", null);
            return "redirect:/main";
        } else {
            model.addAttribute("msg", "邮箱或者验证码错误！");
            return "login";
        }
    }

    /**
     * 去main页面
     */
    @GetMapping("/main")
    public String mainPage(HttpSession httpSession, Model model) {
        Object loginUser = httpSession.getAttribute("loginUser");
        if (loginUser != null) {
            return "main";
        } else {
            // 回到登录页面
            model.addAttribute("msg", "请您先登录");
            return "login";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logOut(HttpSession httpSession) {
        httpSession.setAttribute("loginUser", null);
        return "redirect:/main";  //redirect:/main
    }

    /**
     * 注册账号，成功注册之后即可登录
     */
    @PostMapping("/register")
    public String register(User user, String pwd, Model model, HttpSession httpSession) {
        String email = user.getEmail();
        String username = user.getUsername();
        String password = user.getPassword();
        if (email.trim().equals("") || username.trim().equals("") ||
                password.trim().equals("") || pwd.trim().equals("") || !pwd.equals(password)) {
            model.addAttribute("msg", "请仔细检查填写的信息！");
            return "/login";
        } else {
            User user_r = userService.getUserByEmail(email);
            if (user_r == null) {
                userService.save(user);
                httpSession.setAttribute("loginUser", user);
                return "redirect:/main";
            } else {
                model.addAttribute("msg", "邮箱存在请直接登录");
                return "/login";
            }
        }
    }

    /**
     * 修改密码，修改成功直接登录
     */
    @PutMapping("/updateInfo")
    public String update(User user, String verifyCode, Model model, HttpSession session) {
        String email = user.getEmail();
        String password = user.getPassword();
        Object code = session.getAttribute("verifyCode");
        Object email_vc = session.getAttribute("email");
        if (code == null || email_vc == null) {
            model.addAttribute("msg", "请重新获取验证码！");
            return "login";
        }
        if (email.equals(email_vc) && verifyCode.equals(code) && password.trim().length() > 0) {
            User updateUser = userService.getUserByEmail(user.getEmail());
            if (updateUser.getEmail() != null) {
                // 加密之后存起来
                userService.save(updateUser);
                session.setAttribute("loginUser", updateUser);
                session.setAttribute("verifyCode", null);
                session.setAttribute("email", null);
                return "redirect:/main";
            } else {
                model.addAttribute("msg", "密码重置失败，请再次尝试。");
                return "login";
            }
        } else {
            model.addAttribute("msg", "密码重置失败，请再次尝试。");
            return "login";
        }
    }

}
