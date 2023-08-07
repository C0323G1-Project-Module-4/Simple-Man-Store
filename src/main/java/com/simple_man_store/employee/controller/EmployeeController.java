package com.simple_man_store.employee.controller;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.model.AccountRole;
import com.simple_man_store.account.model.Role;
import com.simple_man_store.account.service.IAccountRoleService;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.account.service.IRoleService;
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
    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountRoleService accountRoleService;
    @Autowired
    private IRoleService roleService;


    @GetMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName, Model model) {
        Pageable pageable = PageRequest.of(page, 2, Sort.by("name").ascending());
        Page<Employee> employeePage = employeeService.findAll(pageable, searchName);
        model.addAttribute("employeePage", employeePage);
        return "employee/list";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam int deleteId,RedirectAttributes redirectAttributes) {
        boolean removeStatus = employeeService.remove(deleteId);
        if(removeStatus) {
            redirectAttributes.addFlashAttribute("message","Đã xóa Nhân viên thành công");
        } else {
            redirectAttributes.addAttribute("message","Nhân viên không tồn tại trên hệ thống");
        }
        return "redirect:/employee/list";
    }

    @GetMapping("/edit/{id}")
    public String showFormEdit(@PathVariable int id, Model model) {
        Employee employee = employeeService.findById(id);
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        System.out.println(employeeDto);
        model.addAttribute("employeeDto", employeeDto);
        return "employee/edit";

    }

    @PostMapping("/edit")
    public String editEmployee(@Valid @ModelAttribute EmployeeDto employeeDto,
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
        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Cập nhật thông tin nhân viên thành công");
        return "redirect:/employee/list";
    }

    @GetMapping("/create")
    public String showFormCreateEmployee(Model model) {
        EmployeeDto employeeDto = new EmployeeDto();
        model.addAttribute("employeeDto",employeeDto);
        return "/employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute EmployeeDto employeeDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Model model) {

        Employee employee = new Employee();


        String name = employeeDto.getName();
        String email = employeeDto.getEmail();
        String phone = employeeDto.getPhoneNumber();
        Account account = new Account(name,email,phone,"123",true);
        accountService.create(account);

        Role role = roleService.findById(2);

        AccountRole accountRole = new AccountRole(account,role);
        accountRoleService.create(accountRole);
        employeeDto.setAccount(account);

        new EmployeeDto().validate(employeeDto, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("employeeDto", employeeDto);
            return "employee/create";
        }

        BeanUtils.copyProperties(employeeDto, employee);
        employeeService.editEmployee(employee);
        redirectAttributes.addFlashAttribute("message", "Thêm mới nhân viên thành công");
        return "redirect:/employee/list";
    }




}
