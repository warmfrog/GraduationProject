package cn.boosp.sharebook.controller;

import cn.boosp.sharebook.common.exception.AccountException;
import cn.boosp.sharebook.common.exception.EmailOrPhoneIsNullException;
import cn.boosp.sharebook.common.exception.UserAlreadyExistException;
import cn.boosp.sharebook.common.exception.UsernameIsNullException;
import cn.boosp.sharebook.common.pojo.User;
import cn.boosp.sharebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("")
    public String admin(){
        return "admin/pages/welcome";
    }

    @PostMapping("")
    public String register(User user, RedirectAttributes redirectAttributes){
        try {
            user  = userService.addAdmin(user);
        }catch (UsernameIsNullException e){
            redirectAttributes.addFlashAttribute("message", "用户名不能为空!");
            return "redirect:/admin/register";
        }catch (UserAlreadyExistException e){
            redirectAttributes.addFlashAttribute("message","用户已存在!");
            return "redirect:/admin/register";
        }catch (EmailOrPhoneIsNullException e){
            redirectAttributes.addFlashAttribute("message", "邮箱和密码不能同时为空!");
            return "redirect:/admin/register";
        } catch (AccountException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("message","注册成功,请登录");
        return "redirect:/admin/login";
    }

    @GetMapping("/book")
    public String book(){
        return "admin/pages/book-list";
    }
    @GetMapping("/user")
    public String user(){
        return "admin/pages/user-list";
    }
    @GetMapping("/ubook")
    public String ubook(){
        return "admin/pages/ubook-list";
    }

}
