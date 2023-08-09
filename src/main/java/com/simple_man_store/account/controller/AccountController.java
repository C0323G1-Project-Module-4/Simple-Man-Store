package com.simple_man_store.account.controller;


import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.account.util.EncrytedPasswordUtils;
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
    public String landingPage(Model model) {
        return "home";
    }
    @GetMapping("/account")
    public String accountDetail(Model model,Principal principal) {
        Customer customer = customerService.findByEmail(principal.getName());
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

    @GetMapping("/login")
    public String signIn(Model model) {
        return "sign-in";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        User loginedUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "adminPage";
    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "logoutSuccessfulPage";
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
