package com.simple_man_store.employee.util;

public class DateUtil {
    public static String reverseDate(String date) {
        String[] parts = date.split("-"); // Tách chuỗi thành mảng các phần tử dựa trên dấu '-'
        String reversedDate = parts[2] + "-" + parts[1] + "-" + parts[0]; // Đảo ngược thứ tự các phần tử
        return reversedDate;
    }

}
