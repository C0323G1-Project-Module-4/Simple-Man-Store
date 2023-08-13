package com.simple_man_store.account.dto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordDto implements Validator {
    private String oldPassword;
    private String newPassword;
    private String reNewPassword;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDto passwordDto = (PasswordDto) target;

        if (passwordDto.getOldPassword().equals("")) {
            errors.rejectValue("oldPassword",null,"Vui lòng không để trống");
        }
        if (passwordDto.getNewPassword().equals("")) {
            errors.rejectValue("newPassword",null,"Vui lòng không để trống");
        }




    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getReNewPassword() {
        return reNewPassword;
    }

    public void setReNewPassword(String reNewPassword) {
        this.reNewPassword = reNewPassword;
    }
}
