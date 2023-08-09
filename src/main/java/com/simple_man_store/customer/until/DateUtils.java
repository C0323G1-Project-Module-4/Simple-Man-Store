package com.simple_man_store.customer.until;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class DateUtils {
    public static String reverseDate(String date) {
        String[] parts = date.split("-");
        String reversedDate = parts[2] + "-" + parts[1] + "-" + parts[0];
        return reversedDate;
    }

}