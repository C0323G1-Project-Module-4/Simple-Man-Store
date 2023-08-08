package com.simple_man_store.employee.controller;

<<<<<<< HEAD
=======
import com.simple_man_store.account.dto.AccountDto;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
import com.simple_man_store.employee.dto.EmployeeDto;
import com.simple_man_store.employee.model.Employee;
import com.simple_man_store.employee.service.IEmployeeService;
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

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
<<<<<<< HEAD
=======
    @Autowired
    private IAccountService accountService;
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92

    @GetMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName, Model model) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("name").ascending());
        Page<Employee> employeePage = employeeService.findAll(pageable, searchName);
        model.addAttribute("employeePage", employeePage);
        return "employee/list";
    }

    @PostMapping("/delete")
<<<<<<< HEAD
    public String deleteEmployee(@RequestParam int deleteId,RedirectAttributes redirectAttributes) {
        boolean removeStatus = employeeService.remove(deleteId);
        if(removeStatus) {
            redirectAttributes.addFlashAttribute("message","Đã xóa Nhân viên thành công");
        } else {
            redirectAttributes.addAttribute("message","Nhân viên không tồn tại trên hệ thống");
=======
    public String deleteEmployee(@RequestParam int deleteId, RedirectAttributes redirectAttributes) {
        boolean removeStatus = employeeService.remove(deleteId);
        if (removeStatus) {
            redirectAttributes.addFlashAttribute("message", "Đã xóa Nhân viên thành công");
        } else {
            redirectAttributes.addAttribute("message", "Nhân viên không tồn tại trên hệ thống");
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        System.out.println(employeeDto);
<<<<<<< HEAD
        model.addAttribute("employeeDto", employeeDto);
=======
        String email = employee.getAccount().getEmail();
        model.addAttribute("employeeDto", employeeDto);
        model.addAttribute("emailAccount", email);
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
        return "employee/edit";

    }

    @PostMapping("/edit")
<<<<<<< HEAD
    public String editEmployee(@Valid @ModelAttribute EmployeeDto employeeDto,
=======
    public String editEmployee(@Valid @ModelAttribute EmployeeDto employeeDto, @RequestParam String emailAccount,
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
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
<<<<<<< HEAD
=======
        Account account = accountService.findByEmail(emailAccount);
        employee.setAccount(account);
        System.out.println(account);
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin nhân viên thành công");
        return "redirect:/employee/list";
    }

    @GetMapping("/create")
    public String showFormCreateEmployee(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
<<<<<<< HEAD
        model.addAttribute("employeeDto",employeeDto);
=======
        model.addAttribute("employeeDto", employeeDto);
>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
        return "/employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeDto employeeDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

<<<<<<< HEAD
        Employee employee = new Employee();
=======

>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            return "employee/create";
        }
<<<<<<< HEAD
        BeanUtils.copyProperties(employeeDto, employee);
        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Thêm mới nhân viên thành công");
        return "redirect:/employee/list";
    }




=======
        Account checkAccount = accountService.findByEmail(employeeDto.getEmail());
        if (checkAccount != null) {
            bindingResult.rejectValue("email", null, "Địa chỉ email đã tồn tại trên hệ thống");
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
        redirectAttributes.addFlashAttribute("message", "Thêm mới nhân viên thành công");
        return "redirect:/employee/list";


    }


>>>>>>> c5d0c05caefecdecf7727a6ca9e4e367fc501c92
}
