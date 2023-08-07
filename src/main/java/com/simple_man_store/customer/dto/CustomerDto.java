package com.simple_man_store.customer.dto;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.customer.model.CustomerType;
import com.simple_man_store.customer.until.Validate;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class CustomerDto implements Validator {
    private Integer id;
    private String name;
    private String dob;
    private String email;
    private String phone_number;
    private boolean gender;
    private String address;
    private boolean flag;
    private CustomerType customerType;
    private Account account;
    private final String regexName = "^[A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]*(?:[ ][A-ZÀÁẠẢÃÂẦẤẬẨẪĂẰẮẶẲẴÈÉẸẺẼÊỀẾỆỂỄÌÍỊỈĨÒÓỌỎÕÔỒỐỘỔỖƠỜỚỢỞỠÙÚỤỦŨƯỪỨỰỬỮỲÝỴỶỸĐ][a-zàáạảãâầấậẩẫăằắặẳẵèéẹẻẽêềếệểễìíịỉĩòóọỏõôồốộổỗơờớợởỡùúụủũưừứựửữỳýỵỷỹđ]+)+$";
    private final String regexPhoneNumber = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$";
    private final String regexEmail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public CustomerDto() {
    }

    public CustomerDto(Integer id, String name, String dob, String email, String phone_number, boolean gender, String address, boolean flag, CustomerType customerType, Account account) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.phone_number = phone_number;
        this.gender = gender;
        this.address = address;
        this.flag = flag;
        this.customerType = customerType;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        CustomerDto customerDto = (CustomerDto) target;
        if (customerDto.getName().length()>255) {
            errors.rejectValue("name",null,"Họ và tên không vượt quá 255 kí tự");
        } else if(!customerDto.getName().matches(regexName)){
            errors.rejectValue("name",null,"Sai định dạng tên. Định dạng họ và tên mẫu: Trần Văn Hoàng");
        }
        if(!customerDto.getPhone_number().matches(regexPhoneNumber)) {
            errors.rejectValue("phone_number",null,"Sau định dạng số điện thoại. Mẫu định dạng số điện thoại đúng: 0987123456 hoặc 84987123456");
        }
        if(customerDto.getAddress().length() >255) {
            errors.rejectValue("address",null,"Địa chỉ không được quá 255 kí tự");
        }
        if (!customerDto.getEmail().matches(regexEmail)){
            errors.rejectValue("email",null,"Sau định dạng email. Mẫu định dạng email đúng:A-z..@gmail.com");
        }
        if (!Validate.checkAge(customerDto.getDob())){
            errors.rejectValue("dob",null,"Cảnh báo chưa đủ 18 tuổi");
        }
    }
}
