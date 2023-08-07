package com.simple_man_store.customer.until;

import java.time.LocalDate;
import java.time.Period;

public class Validate {
    public Validate() {

    }

    public static boolean checkAge(String dob) {
        LocalDate localDate = LocalDate.now();
        LocalDate birth = LocalDate.parse(dob);
        Period period = Period.between(birth, localDate);
        if (period.getYears() < 18) {
            return false;
        }
        return true;
    }
}
