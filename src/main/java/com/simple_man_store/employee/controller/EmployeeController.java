package com.simple_man_store.employee.controller;

import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.employee.dto.EmployeeDto;
import com.simple_man_store.employee.model.Employee;
import com.simple_man_store.employee.service.IEmployeeService;
import com.simple_man_store.order.model.Cart;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/employee")
@SessionAttributes("cart")
public class EmployeeController {
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ICustomerService customerService;

    @GetMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName,
                           @RequestParam(defaultValue = "") String phoneNumber,
                           Model model, Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Pageable pageable = PageRequest.of(page, 5, Sort.by("name").ascending());
        Page<Employee> employeePage = employeeService.findAll(pageable, searchName,phoneNumber);
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("searchName",searchName);
        model.addAttribute("phoneNumber",phoneNumber);
        String noResultMessage;
        if(employeePage.getTotalElements()==0) {
            noResultMessage = "Không tìm thấy kết quả";
            model.addAttribute("noResultMessage",noResultMessage);
        }

        return "employee/list";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam int deleteId, RedirectAttributes redirectAttributes) {
        boolean removeStatus = employeeService.remove(deleteId);
        if (removeStatus) {
            redirectAttributes.addFlashAttribute("message", "Đã xóa Nhân viên thành công.");
        } else {
            redirectAttributes.addAttribute("message", "Nhân viên không tồn tại trên hệ thống.");
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable int id, Model model,Principal principal) {
        Employee employee = employeeService.findById(id);
        String baseEmail = principal.getName();
        Customer customer = customerService.findByEmail(baseEmail);
        String type = customerService.findCustomerTypeByEmail(baseEmail);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        System.out.println(employeeDto);
        String email = employee.getAccount().getEmail();
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("emailAccount", email);
        return "employee/edit";

    }

    @PostMapping("/edit")
    public String editEmployee(@Valid @ModelAttribute EmployeeDto employeeDto, @RequestParam String emailAccount,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        Employee employee = new Employee();
        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            return "employee/edit";
        }

        BeanUtils.copyProperties(employeeDto, employee);
        Account account = accountService.findByEmail(emailAccount);
        employee.setAccount(account);
        System.out.println(account);
        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin nhân viên thành công.");
        return "redirect:/employee/list";
    }

    @GetMapping("/create")
    public String showFormCreateEmployee(Model model,Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto", employeeDto);
        return "/employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeDto employeeDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {


        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            return "employee/create";
        }
        Account checkAccount = accountService.findByEmail(employeeDto.getEmail());
        if (checkAccount != null) {
            bindingResult.rejectValue("email", null, "Địa chỉ email đã tồn tại trên hệ thống.");
            model.addAttribute("employeeDto", employeeDto);
            return "employee/create";
        }

        AccountDto accountDto = new AccountDto();
        accountDto.setName(employeeDto.getName());
        accountDto.setEmail(employeeDto.getEmail());
        accountDto.setPassword("123");
        accountDto.setPhone(employeeDto.getPhoneNumber());
        accountService.save(accountDto);

        Account account = accountService.findByEmail(accountDto.getEmail());
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        employee.setAccount(account);

        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Thêm mới nhân viên thành công.");
        return "redirect:/employee/list";

    }


}
