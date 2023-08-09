package com.simple_man_store.product.controller;

import com.simple_man_store.product.dto.ProductDto;
import com.simple_man_store.product.model.Category;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.model.Warehouse;
import com.simple_man_store.product.service.ICategoryService;
import com.simple_man_store.product.service.IProductService;
import com.simple_man_store.product.service.ISizeService;
import com.simple_man_store.product.service.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IWarehouseService warehouseService;
    @Autowired
    private ISizeService sizeService;


    @GetMapping("/list")
    public String showListProduct(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "") String category,
                                  @RequestParam(defaultValue = "0-100000000") String priceRange,
                                  @RequestParam(defaultValue = "") String searchName,
                                  Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        String[] parts = priceRange.split("-");
        Double minPrice = Double.valueOf(parts[0]);
        Double maxPrice = Double.valueOf(parts[1]);
        List<Category> categoryList = categoryService.showListCategory();
        Page<Product> productPage = productService.findProduct(pageable, searchName, minPrice, maxPrice, category);
        model.addAttribute("category", category);
        model.addAttribute("priceRange", priceRange);
        model.addAttribute("searchName", searchName);
        model.addAttribute("productPage", productPage);
        model.addAttribute("categoryList", categoryList);
        return "product/list";
    }
    @GetMapping
    public String searchProduct(Model model){

        return "product/search";
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        ProductDto productDto = new ProductDto();
        List<Category> categoryList = categoryService.showListCategory();
        List<Size> sizeList =sizeService.showListSize();
        model.addAttribute("productDto", productDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("sizeList", sizeList);
        return "/product/create";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute ProductDto productDto,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirectAttributes) {
//        validate
        new ProductDto().validate(productDto,bindingResult);
        List<Category> categoryList = categoryService.showListCategory();
        List<Size> sizeList = sizeService.showListSize();
        if (bindingResult.hasErrors()){
            model.addAttribute("productDto",productDto);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("sizeList", sizeList);
            return "/product/create";
        }

//         product
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImage(productDto.getImage());
        product.setDescription(productDto.getDescription());
        Category category = categoryService.findCategoryId(productDto.getCategoryId());
        product.setCategory(category);
        productService.addProduct(product);
//          warehouse
        Warehouse warehouse = new Warehouse();
        warehouse.setQuantity(productDto.getQuantity());
        warehouse.setProduct(product);
        Size size = sizeService.selectSizeById(productDto.getSizeId());
        warehouse.setSize(size);
        warehouseService.addWareHouse(warehouse);
        redirectAttributes.addFlashAttribute("msg", "Thêm mới sản phẩm thành công");
        return "redirect:/product/list";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int deleteId, RedirectAttributes redirectAttributes) {
        System.out.println(deleteId);
        Warehouse warehouse = warehouseService.selectWarehouseByProductId(deleteId);
        Product product = productService.selectProductById(deleteId);
        warehouseService.deleteWareHouse(warehouse);
        productService.deleteProduct(product);
        redirectAttributes.addFlashAttribute("msg", "Xóa sản phẩm thành công");
        return "redirect:/product/list";
    }
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
//        Warehouse warehouse = warehouseService.selectWarehouseByProductId(id);
//        Product product = productService.selectProductById(id);
//        warehouseService.deleteWareHouse(warehouse);
//        productService.deleteProduct(product);
//        redirectAttributes.addFlashAttribute("msg", "Xóa sản phẩm thành công");
//        return "redirect:/product/list";
//    }


    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable int id, Model model) {
        Warehouse warehouse =warehouseService.selectWarehouseByProductId(id);
        ProductDto productDto = new ProductDto();
        productDto.setId(warehouse.getProduct().getId());
        productDto.setName(warehouse.getProduct().getName());
        productDto.setPrice(warehouse.getProduct().getPrice());
        productDto.setImage(warehouse.getProduct().getImage());
        productDto.setDescription(warehouse.getProduct().getDescription());
        productDto.setCategoryId(warehouse.getProduct().getCategory().getId());
        productDto.setSizeId(warehouse.getSize().getId());
        productDto.setQuantity(warehouse.getQuantity());

        List<Category> categoryList = categoryService.showListCategory();
        List<Size> sizeList = sizeService.showListSize();
        model.addAttribute("productDto", productDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("sizeList",sizeList);
        return "product/edit";
    }



    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute ProductDto productDto,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
//        Validation
        new ProductDto().validate(productDto,bindingResult);
        List<Category> categoryList = categoryService.showListCategory();
        List<Size> sizeList = sizeService.showListSize();
        if (bindingResult.hasErrors()){
            model.addAttribute("productDto",productDto);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("sizeList",sizeList);
            return "/product/edit";
        }
//        Product
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        Category category = categoryService.findCategoryId(productDto.getCategoryId());
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setPrice(productDto.getPrice());
        productService.editProduct(product);
//        Warehouse
        Warehouse warehouse = warehouseService.selectWarehouseByProductId(productDto.getId());
        warehouse.setQuantity(productDto.getQuantity());
        Size size = sizeService.selectSizeById(productDto.getSizeId());
        warehouse.setSize(size);
        warehouseService.editWareHouse(warehouse);
        redirectAttributes.addFlashAttribute("msg", "Chỉnh sửa thông tin sản phẩm thành công");
        return "redirect:/product/list";
    }


    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model model) {
        Warehouse warehouse = warehouseService.selectWarehouseByProductId(id);
        model.addAttribute("warehouse", warehouse);
        return "product/detail";
    }
}
