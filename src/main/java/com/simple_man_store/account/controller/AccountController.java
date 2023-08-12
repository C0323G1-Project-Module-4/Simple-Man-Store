package com.simple_man_store.account.controller;


import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.dto.PasswordDto;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.account.util.WebUtils;
import com.simple_man_store.customer.dto.CustomerDto;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.order.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@SessionAttributes("cart")
public class AccountController {
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerService customerService;
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String landingPage(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            Customer customer = customerService.findByEmail(email);
            String type = customerService.findCustomerTypeByEmail(email);
            model.addAttribute("type", type);
            model.addAttribute("customer_name", customer.getName());
        }
        return "home";
    }
    @GetMapping("/account")
    public String accountDetail(Model model,Principal principal) {
        Customer customer = customerService.findByEmail(principal.getName());
        String type = customerService.findCustomerTypeByEmail(customer.getEmail());
        model.addAttribute("type",type);
        model.addAttribute("customer_name",customer.getName());
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        model.addAttribute("customerDto",customerDto);
        return "/account/detail";
    }
    @GetMapping("/register")
    public String signUp(Model model) {
        AccountDto accountDto = new AccountDto();
        model.addAttribute("accountDto",accountDto);
        return "sign-up";
    }
    @PostMapping("/register-submit")
    public String addNewAccount(@Valid @ModelAttribute AccountDto accountDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                Model model){
        new AccountDto().validate(accountDto,bindingResult);
        if(accountService.findByEmail(accountDto.getEmail()) != null){
            bindingResult.rejectValue("email",null,"Địa chỉ email đã tồn tại!");
            model.addAttribute("accountDto",accountDto);
            return "sign-up";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("accountDto",accountDto);
            return "sign-up";
        }
        accountService.save(accountDto);
       redirectAttributes.addFlashAttribute("msg","Đăng ký thành công");
        return "redirect:/login";
    }
    @GetMapping("/shop")
    public String home(){
        return "shop";
    }

    @GetMapping("shop/detail")
    public String detail(){
        return "detail";
    }

    @GetMapping("/account/change-pass")
    public String changePassword(Model model, Principal principal){
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        model.addAttribute("passwordDto",new PasswordDto());
        return "/account/change-password";
    }

    @GetMapping("/login")
    public String signIn( Model model) {
        return "sign-in";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "adminPage";
    }

    @PostMapping("/account/submit-pass-change")
    public String submitPassChange(@Valid @ModelAttribute PasswordDto passwordDto,BindingResult bindingResult,
                                   Principal principal, Model model){
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        new PasswordDto().validate(passwordDto,bindingResult);
        boolean firstCheck = accountService.checkOldPass(email,passwordDto.getOldPassword());
        boolean secondCheck = accountService.checkNewPass(email,passwordDto.getNewPassword());
        if(!firstCheck){
            bindingResult.rejectValue("oldPassword",null,"Mật khẩu cũ không đúng");
            return "/account/change-password";
        }
        if(bindingResult.hasErrors()){
            return "/account/change-password";
        }
        if(secondCheck){
            bindingResult.rejectValue("newPassword",null,"Mật khẩu mới không được giống mật khẩu cũ");
            return "/account/change-password";
        }
        accountService.changePassword(email,passwordDto.getNewPassword());
        return "redirect:/account";
    }


    @GetMapping("/forget-password")
    public String showForgetPasswordForm(){
        return "account/forgetPasswordForm";
    }

    @GetMapping("/check-email/")
    public String checkEmailOnForget(@RequestParam ("email") String email,Model model, RedirectAttributes redirectAttributes){
        Customer customer = customerService.findByEmail(email);
        if(email.equals("")){
            redirectAttributes.addFlashAttribute("msg","Vui lòng không để trống");
            return "redirect:/forget-password";
        } else if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")){
            redirectAttributes.addFlashAttribute("msg","Email không hợp lệ");
            return "redirect:/forget-password";
        } else if(customer == null){
            redirectAttributes.addFlashAttribute("msg","Email không hợp lệ");
            return "redirect:/forget-password";
        }
        return "account/newPasswordForm";
    }


    @RequestMapping(value = "/userAccountInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
//        model.addAttribute("userInfo", userInfo);

            return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "403Page";
    }
}
