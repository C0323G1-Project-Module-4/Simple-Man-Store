package com.simple_man_store.employee.dto;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.time.LocalDate;
import java.time.Period;

@Component
public class EmployeeDto implements Validator {
    @Autowired
    private IAccountService accountService;

    private Integer id;
    private String name;
    private String dob;
    private String phoneNumber;
    private String email;
    private boolean gender;
    private String address;
    private boolean flag;



    private final String regexName = "^([\\p{Lu}][\\p{Ll}]{1,8})(\\s([\\p{Lu}]|[\\p{Lu}][\\p{Ll}]{1,10})){0,5}$";
    private final String regexPhoneNumber = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
    private final String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDto employeeDto = (EmployeeDto) target;


        if (employeeDto.getName().equals("")) {
            errors.rejectValue("name",null,"Vui lòng điền họ và tên");
        } else if (employeeDto.getName().length()>255) {
            errors.rejectValue("name",null,"Họ và tên không vượt quá 255 kí tự");
        } else if(!employeeDto.getName().matches(regexName)){
            errors.rejectValue("name",null,"Sai định dạng tên. Định dạng họ và tên mẫu: Trần Văn Hoàng");
        }

        if (employeeDto.getPhoneNumber().equals("")) {
            errors.rejectValue("phoneNumber",null,"Vui lòng điền số điện thoại");
        } else if(!employeeDto.getPhoneNumber().matches(regexPhoneNumber)) {
            errors.rejectValue("phoneNumber",null,"Sai định dạng số điện thoại. Mẫu định dạng số điện thoại đúng: 0987123456 hoặc 84987123456");
        }

        if (employeeDto.getEmail().equals("")) {
            errors.rejectValue("email",null,"Vui lòng điền địa chỉ email");
        } else if(!employeeDto.getEmail().matches(regexEmail)) {
            errors.rejectValue("email",null,"Sai định dạng email. Mẫu định dạng email đúng: tranvanhoang123@gmail.com");
        }





        if (employeeDto.getAddress().equals("")) {
            errors.rejectValue("address",null,"Vui lòng điền địa chỉ");
        } else if(employeeDto.getAddress().length() >255) {
            errors.rejectValue("address",null,"Địa chỉ không được quá 255 kí tự");
        }

        if(employeeDto.dob.equals("")) {
            errors.rejectValue("dob",null,"Vui lòng nhập ngày sinh");
        } else {
            LocalDate presentDate = LocalDate.now();
            LocalDate birthday = LocalDate.parse(employeeDto.dob);
            Period agePeriod = Period.between(birthday,presentDate);
            int age = agePeriod.getYears();
            if(age < 18|age>70) {
                errors.rejectValue("dob",null,"Yêu cầu Nhân viên phải đủ 18 tuổi và dưới 70 tuổi");
            }
        }



    }

    public EmployeeDto() {
    }



    @Override
    public String toString() {
        return "EmployeeDto{" +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", flag=" + flag +
                ", regexName='" + regexName + '\'' +
                ", regexPhoneNumber='" + regexPhoneNumber + '\'' +
                '}';
    }

    public EmployeeDto(String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag) {
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
    }

    public EmployeeDto(Integer id, String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
