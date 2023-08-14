package com.simple_man_store.customer.controller;

import com.simple_man_store.customer.dto.CustomerDto;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.repository.ICustomerRepository;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.customer.service.customer_type.ICustomerTypeService;
import com.simple_man_store.order.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/customer")
@SessionAttributes("cart")
public class CustomerController {
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerTypeService customerTypeService;
    @Autowired
    private ICustomerRepository customerRepository;



    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "") String searchName,
                                 @RequestParam(defaultValue = "") String[] customerType,
                                 Principal principal, Model model
    ) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("name").ascending().and(Sort.by("gender").descending()));
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        if (customerType.length > 0) {
            Page<Customer> customerPage = customerService.findAllPageCustomerTypeId(pageable, searchName, customerType);
            ModelAndView modelAndView = new ModelAndView("customer/list");
            modelAndView.addObject("customerPage", customerPage);
            modelAndView.addObject("searchName",searchName);
            modelAndView.addObject("customerType",customerType);
            String message;
            if (customerPage.getTotalElements()==0){
                message = "Không tìm thấy kết quả";
                modelAndView.addObject("message",message);
            }
            modelAndView.addObject("customer_type", customerTypeService.findAll());
//            modelAndView.addObject("")
            return modelAndView;
        }



        Page<Customer> customerPage = customerService.findAllPage(pageable, searchName);
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("searchName",searchName);

        modelAndView.addObject("customerPage", customerPage);
        modelAndView.addObject("customer_type", customerTypeService.findAll());
        return modelAndView;
    }
    @PostMapping("/delete")
    public String delete(@RequestParam Integer deleteId, RedirectAttributes redirectAttributes) {
        Boolean check = customerService.delete(deleteId);
        String msg;
        if (check) {
            msg ="Khách hàng "+customerService.findById(deleteId).getName()+" đã bị xoá";
            redirectAttributes.addFlashAttribute("msg", msg);
        } else {
            redirectAttributes.addAttribute("msg", "Không tìm thấy khách hàng ");
        }
        return "redirect:/customer/list";
    }

    @GetMapping("/showCreate")
    public ModelAndView showCreate(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("customer/create_customer");
        modelAndView.addObject("customer", new CustomerDto());
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        modelAndView.addObject("type", type);
        modelAndView.addObject("customer_name", customer.getName());
        return modelAndView;
    }
    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer,RedirectAttributes redirectAttributes){
        Boolean check =  customerService.add(customer);
        redirectAttributes.addFlashAttribute("msg",check);
        return "redirect:/customer/list";
    }
    @GetMapping("/showUpdate/{id}")
    public ModelAndView showUpdate(@PathVariable Integer id, Principal principal){

        ModelAndView modelAndView = new ModelAndView("customer/update");
        Customer customer = customerService.searchById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        System.out.println(customerDto.getDob());
        modelAndView.addObject("customerDto",customerDto);
        String email = principal.getName();
        Customer baseCustomer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        modelAndView.addObject("type", type);
        modelAndView.addObject("customer_name", baseCustomer.getName());
        return modelAndView;
    }
    @PostMapping("/edit")
    public String update(@ModelAttribute CustomerDto customerDto, RedirectAttributes redirectAttributes, BindingResult bindingResult){
        Customer customer = new Customer();
        new CustomerDto().validate(customerDto,bindingResult);
        if (bindingResult.hasErrors()){
            return "/customer/update";
        }
        BeanUtils.copyProperties(customerDto,customer);
        customerService.edit(customer);
        redirectAttributes.addFlashAttribute("msg"," Cập nhật thành công khách hàng "+customer.getName());
        return "redirect:/customer/list";
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute CustomerDto customerDto, BindingResult bindingResult,
                       Model model,Principal principal,RedirectAttributes redirectAttributes){
        Customer newCustomer = customerService.findByEmail(principal.getName());
        String type = customerService.findCustomerTypeByEmail(newCustomer.getEmail());
        model.addAttribute("type", type);
        model.addAttribute("customer_name", newCustomer.getName());
        Customer customer = new Customer();
        new CustomerDto().validate(customerDto,bindingResult);
        if(bindingResult.hasErrors()){
            return "/account/detail";
        }
        BeanUtils.copyProperties(customerDto,customer);
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("msg","Cập nhật thành công");
        return "redirect:/account";
    }
    @PostMapping("/even")
    public String receiveMessage(@RequestBody String message,RedirectAttributes redirectAttributes) {
        System.out.println("Received message: " + message);
        redirectAttributes.addFlashAttribute("msg",message);
        return "redirect:/customer/list";
    }
//    @Scheduled(fixedDelay = 5000)
//    public void myScheduledMethod() {
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        Integer money = customerRepository.findCustomerByEmail("user");
//        System.out.println(money);
//        String requestBody = "{\"message\": \"Nhật Béo!\"}";
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/customer/even", httpEntity, String.class);
//    }
}
