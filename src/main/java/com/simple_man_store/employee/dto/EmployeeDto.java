package com.simple_man_store.employee.dto;

import com.simple_man_store.account.model.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.Period;


public class EmployeeDto implements Validator {
    private Integer id;

    @NotBlank(message = "Vui lòng điền Họ và tên")
    private String name;
    @NotBlank(message = "Vui lòng chọn ngày sinh")
    private String dob;
    @NotBlank(message = "Vui lòng điền số điện thoại")
    private String phoneNumber;
    @Email(message = "Sai định dạng email. Vui lòng nhập lại. Ví dụ về mẫu email chuẩn: tranvan12@gmail.com")
    private String email;

    private boolean gender;
    @NotBlank(message = "Not Empty")
    private String address;
    private boolean flag;
    private Account account;


    private final String regexName = "^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*)*$";
    private final String regexPhoneNumber = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDto employeeDto = (EmployeeDto) target;
        if (employeeDto.getName().length()>255) {
            errors.rejectValue("name",null,"Họ và tên không vượt quá 255 kí tự");
        } else if(!employeeDto.getName().matches(regexName)){
            errors.rejectValue("name",null,"Sai định dạng tên. Định dạng họ và tên mẫu: Trần Văn Hoàng");
        }
        if(!employeeDto.getPhoneNumber().matches(regexPhoneNumber)) {
            errors.rejectValue("phoneNumber",null,"Sau định dạng số điện thoại. Mẫu định dạng số điện thoại đúng: 0987123456 hoặc 84987123456");
        }
        if(employeeDto.getAddress().length() >255) {
            errors.rejectValue("address",null,"Địa chỉ không được quá 255 kí tự");
        }

        LocalDate presentDate = LocalDate.now();
        LocalDate birthday = LocalDate.parse(employeeDto.dob);
        Period agePeriod = Period.between(birthday,presentDate);
        int age = agePeriod.getYears();
        if(age < 18|age>70) {
            errors.rejectValue("dob",null,"Yêu cầu Nhân viên phải đủ 18 tuổi và dưới 70 tuổi");
        }

    }

    public EmployeeDto() {
    }

    public EmployeeDto(Integer id, String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag, Account account) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
        this.account = account;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", flag=" + flag +
                ", account=" + account +
                ", regexName='" + regexName + '\'' +
                ", regexPhoneNumber='" + regexPhoneNumber + '\'' +
                '}';
    }

    public EmployeeDto(String name, String dob, String phoneNumber, String email, boolean gender, String address, boolean flag, Account account) {
        this.name = name;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
