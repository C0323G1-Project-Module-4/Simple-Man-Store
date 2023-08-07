package com.simple_man_store.customer.controller;

import com.simple_man_store.customer.dto.CustomerDto;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.model.CustomerType;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.customer.service.customer_type.ICustomerTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ICustomerTypeService customerTypeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView showList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "") String searchName
                                ) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("name").ascending().and(Sort.by("gender").descending()));
        Page<Customer> customerPage= customerService.findAllPage(pageable, searchName);
        ModelAndView modelAndView = new ModelAndView("customer/list");
        modelAndView.addObject("customerPage", customerPage);
        modelAndView.addObject("customer_type",customerTypeService.findAll());
        return modelAndView;
    }

    @PostMapping("/delete")
    public String delete(@RequestParam Integer deleteId, RedirectAttributes redirectAttributes) {
        Boolean check = customerService.delete(deleteId);
        String msg;
        if (check) {
            msg ="Khách hàng "+customerService.findById(deleteId).getName()+" đã được cút khỏi danh sách";
            redirectAttributes.addFlashAttribute("msg", msg);
        } else {
            redirectAttributes.addAttribute("msg", "Không tìm thấy khách hàng ");
        }
        return "redirect:/customer/list";
    }

    @GetMapping("/showCreate")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("customer/create_customer");
        modelAndView.addObject("customer", new CustomerDto());
        return modelAndView;
    }
    @PostMapping("/create")
    public String create(@ModelAttribute Customer customer,RedirectAttributes redirectAttributes){
      Boolean check =  customerService.add(customer);
        redirectAttributes.addFlashAttribute("msg",check);
        return "redirect:/customer/list";
    }
    @GetMapping("/showUpdate/{id}")
    public ModelAndView showUpdate(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("customer/update");
        Customer customer = customerService.findById(id);
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        modelAndView.addObject("customerDto",customerDto);
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
}
