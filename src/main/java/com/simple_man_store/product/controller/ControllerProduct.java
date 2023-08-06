package com.simple_man_store.product.controller;

import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.service.ICategoryService;
import com.simple_man_store.product.service.IProductService;
import com.simple_man_store.product.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ControllerProduct {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IWarehouseService warehouseService;

    @GetMapping("/list")
    public String showListProduct(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String searchName,
                                  Model model){
        Pageable pageable = PageRequest.of(page,8);
        Page<Product> productPage = productService.findAll(pageable,searchName);
        model.addAttribute("searchName",searchName);
        model.addAttribute("productPage",productPage);
        return "product/list";
    }
    @GetMapping("/listPrice")
    public String showListProductPrice(@RequestParam(defaultValue = "0")int page,
                                       @RequestParam(defaultValue = "0-1000000")String priceRange,
                                       Model model){
        String[] parts = priceRange.split("-");
        Double minPrice = Double.valueOf(parts[0]);
        Double maxPrice = Double.valueOf(parts[1]);
        Pageable pageable = PageRequest.of(page,8);
        Page<Product> productPage = productService.findAllPrice(pageable,minPrice,maxPrice);
        model.addAttribute("productPage",productPage);
        return "product/list";
    }
    @GetMapping("/create")
    public String showCreate(Model model){
        Product product = new Product();
        List<Category> categoryList = categoryService.showListCategory();
        model.addAttribute("product",product);
        model.addAttribute("categoryList",categoryList);
        return "product/create";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute Product product, RedirectAttributes redirectAttributes){
        System.out.println(product);
        productService.addProduct(product);
        redirectAttributes.addFlashAttribute("msg","Thêm mới sản phẩm thành công");
        return "redirect:/product/list";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes){
        Product product =productService.selectProductById(id);
        productService.deleteProduct(product);
        redirectAttributes.addFlashAttribute("msg","Xóa sản phẩm thành công");
        return "redirect:/product/list";
    }
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable int id,Model model){
        Product product = productService.selectProductById(id);
        List<Category> categoryList = categoryService.showListCategory();
        model.addAttribute("product",product);
        model.addAttribute("categoryList",categoryList);
        return "product/edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute Product product,RedirectAttributes redirectAttributes){
        productService.editProduct(product);
        redirectAttributes.addFlashAttribute("msg","Chỉnh sửa thông tin sản phẩm thành công");
        return "redirect:/product/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable int id,Model model){
        Product product = productService.selectProductById(id);
        model.addAttribute("product",product);
        return "product/detail";
    }
}
