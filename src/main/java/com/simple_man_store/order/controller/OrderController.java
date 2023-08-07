package com.simple_man_store.order.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.order.dto.OrderDto;
import com.simple_man_store.order.model.Cart;
import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.service.IOrderService;
import com.simple_man_store.order_detail.model.OrderDetail;
import com.simple_man_store.order_detail.service.IODService;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.service.IProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@SessionAttributes("cart")
@RequestMapping("/order")
public class OrderController {
    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }

    @Autowired
    private IOrderService orderService;
    @Autowired
    private IODService iodService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IAccountService accountService;

    @RequestMapping("/cart")
    public String showCart() {
        return "cart";
    }

    @RequestMapping("/home")
    public String showHome(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String searchName,
            Model model
    ){
        Pageable pageable = PageRequest.of(page,9);
        List<Product> productPage = productService.findAll();
        model.addAttribute("searchName",searchName);
        model.addAttribute("productPage",productPage);
        return "home";
    }

    @RequestMapping("/shop")
    public String showShop(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName,
                           Model model
    ) {
        Pageable pageable = PageRequest.of(page, 9);
        List<Product> productPage = productService.findAll();
        model.addAttribute("searchName", searchName);
        model.addAttribute("productPage", productPage);
        return "shop";
    }

    @RequestMapping("/contact")
    public String showContact() {
        return "contact";
    }

    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model model) {

        Product product = productService.selectProductById(id);
        model.addAttribute("product", product);
        return "detail";
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName,
                           Model model) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<Order> orderPage = orderService.findAllInPage(pageable, searchName);
        model.addAttribute("orderPage", orderPage);
        return "order/list";
    }

    @RequestMapping("/add/{id}")
    public String addToCart(@PathVariable int id,
                            @RequestParam(required = false, defaultValue = "1") int quantity,
                            @ModelAttribute Cart cart,
                            @RequestParam(required = false, defaultValue = "") String action,
                            RedirectAttributes redirectAttributes) {
        Product product = productService.selectProductById(id);
        if (action.equals("increase")) {
            cart.addProduct(product, 1);
            return "redirect:/order/cart";
        }
        if (action.equals("reduce")) {
            cart.addProduct(product, -1);
            return "redirect:/order/cart";
        }
        if (action.equals("delete")) {
            cart.removeProduct(product);
            redirectAttributes.addFlashAttribute("msg", "Đã xoá sản phẩm khỏi giỏ hàng");
            return "redirect:/order/cart";
        }
        cart.addProduct(product, quantity);
        redirectAttributes.addFlashAttribute("msg", "Đã thêm vào giỏ hàng");
        return "redirect:/order/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        OrderDto orderDto = new OrderDto();
        model.addAttribute("orderDto",orderDto);
        return "checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute Cart cart,
                           @Valid@ModelAttribute OrderDto orderDto,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes
    ) {
        new OrderDto().validate(orderDto,bindingResult);
        if (bindingResult.hasErrors()){
            return "checkout";
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDto,order);
        Account account = accountService.findByEmail("lequangphuoc2305@gmail.com");
        order.setAccount(account);
        orderService.add(order);
        Set<OrderDetail> orderDetail = new HashSet<>();
        for (Product key : cart.getProducts().keySet()) {
            orderDetail.add(new OrderDetail(key.getPrice(), cart.getProducts().get(key), "xl", order, key));
        }
        iodService.addAll(orderDetail);
        cart.clear();
        redirectAttributes.addFlashAttribute("msg", "Đặt hàng thành công");
        return "redirect:/order/home";
    }

    @RequestMapping("/order-detail/{id}")
    public String showOrderDetail(@PathVariable int id,Model model){
        Order order = orderService.getById(id);
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total=0;
        for (OrderDetail o:orderDetails
             ) {
            total += (o.getPrice()*o.getQuantity());
        }
        model.addAttribute("total",total);
        model.addAttribute("orderDetails",orderDetails);
        return "order/detail";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam int id,RedirectAttributes redirectAttributes){
        orderService.delete(id);
        redirectAttributes.addFlashAttribute("msg","Xoá thành công");
        return "redirect:/order/list";
    }

    @RequestMapping("/history")
    public String history(@RequestParam(defaultValue = "0") int page,Model model){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Account account = accountService.findByEmail("lequangphuoc2305@gmail.com");
        Page<Order> orderPage = orderService.getByAcount(pageable,account);
        model.addAttribute("orderPage",orderPage);
        return "history";
    }

    @PostMapping("/is-checkout")
    public String isCheckout(@RequestParam int id,RedirectAttributes redirectAttributes){
        Order order = orderService.getById(id);
        order.setPayment_date(String.valueOf(LocalDate.now()));
        orderService.add(order);
        redirectAttributes.addFlashAttribute("msg","Xác nhận thành công");
        return "redirect:/order/history";
    }

    @RequestMapping("/history-detail/{id}")
    public String historyDetail(@PathVariable int id,Model model){
        Order order = orderService.getById(id);
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total=0;
        for (OrderDetail o:orderDetails) {
            total += (o.getPrice()*o.getQuantity());
        }
        model.addAttribute("total",total);
        model.addAttribute("orderDetails",orderDetails);
        model.addAttribute("order",order);
        return "historyDetail";
    }
}
