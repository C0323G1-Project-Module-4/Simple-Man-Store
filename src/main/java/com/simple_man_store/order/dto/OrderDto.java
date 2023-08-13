package com.simple_man_store.order.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class OrderDto implements Validator {
    private String name;
    private String email;
    private String phone_number;
    private String address;
    private int status =0;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        OrderDto orderDto = (OrderDto) target;
        if(orderDto.name.trim().equals("") || orderDto.name == null){
            errors.rejectValue("name",null,"Vui lòng nhập tên");
        }else if(orderDto.name.length()>80){
            errors.rejectValue("name",null,"Quá ký tự cho phép");
        }
//        else if(orderDto.name.matches("^[\\p{L}\\s]+$")) {
//            errors.rejectValue("name", null, "Tên chỉ cho phép nhập chữ");
//        }

        if(orderDto.email.trim().equals("") || orderDto.email == null){
            errors.rejectValue("email",null,"Vui lòng nhập email");
        }else if(orderDto.email.length()>150){
            errors.rejectValue("email",null,"Quá ký tự cho phép");
        }
//        else if(orderDto.email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
//            errors.rejectValue("email", null, "Sai định dạng e mail");
//        }

        if(orderDto.phone_number.trim().equals("") || orderDto.phone_number == null){
            errors.rejectValue("phone_number",null,"Vui lòng nhập số điện thoại");
        }else if(orderDto.phone_number.length()>20){
            errors.rejectValue("phone_number",null,"Quá ký tự cho phép");
        }else if(orderDto.email.matches("^(?:\\+84|0)(?:\\d{9,10})$")) {
            errors.rejectValue("phone_number", null, "Vui lòng nhập đúng SĐT VD:'0XXXXXXXXX' hoặc '+84XXXXXXXXX'");
        }

        if(orderDto.address.trim().equals("") || orderDto.address == null){
            errors.rejectValue("address",null,"Vui lòng nhập địa chỉ");
        }else if(orderDto.address.length()>500){
            errors.rejectValue("address",null,"Quá ký tự cho phép");
        }
    }
}
