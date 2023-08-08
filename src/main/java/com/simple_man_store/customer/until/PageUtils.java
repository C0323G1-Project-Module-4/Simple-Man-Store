package com.simple_man_store.customer.until;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;

public class PageUtils {
    public static <Customer> Page<Customer> mergePages(List<Page<Customer>> pages) {
        List<Customer> mergedContent = new ArrayList<>();
        long totalElements = 0;

        for (Page<Customer> page : pages) {
            mergedContent.addAll(page.getContent());
            totalElements += page.getTotalElements();
        }

        return new PageImpl<>(mergedContent, pages.get(0).getPageable(), totalElements);
    }
}