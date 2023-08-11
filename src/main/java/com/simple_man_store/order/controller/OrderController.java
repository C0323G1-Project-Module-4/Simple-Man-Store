package com.simple_man_store.order.controller;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.order.dto.OrderDto;
import com.simple_man_store.order.dto.ProductCartDto;
import com.simple_man_store.order.model.Cart;
import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.service.IOrderService;
import com.simple_man_store.order_detail.model.OrderDetail;
import com.simple_man_store.order_detail.service.IODService;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.service.IProductService;
import com.simple_man_store.product.service.ISizeService;
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
import java.security.Principal;
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
    private ISizeService sizeService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IODService iodService;
    @Autowired
    private IProductService productService;
    @Autowired
    private IAccountService accountService;

    @Autowired
    private ICustomerService customerService;

    @RequestMapping("/cart")
    public String showCart(Model model, Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
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
    public String showDetail(@PathVariable int id, Model model,Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Product product = productService.selectProductById(id);
        List<Size> sizeList = sizeService.showListSize();
        model.addAttribute("product", product);
        model.addAttribute("sizeList", sizeList);
        return "detail";
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName,
                           Model model, Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<Order> orderPage = orderService.findAllInPage(pageable, searchName);
        model.addAttribute("orderPage", orderPage);
        return "order/list";
    }

    @RequestMapping("/add/{id}")
    public String addToCart(@PathVariable int id,
                            @RequestParam(required = false, defaultValue = "1") int quantity,
                            @ModelAttribute Cart cart,
                            @RequestParam(defaultValue = "") String sizes,
                            @RequestParam(required = false, defaultValue = "") String action,
                            RedirectAttributes redirectAttributes) {
        Product product = productService.selectProductById(id);
        ProductCartDto productCartDto = new ProductCartDto(product.getId(),product.getName(),product.getPrice(),product.getImage(),sizes);
        if (action.equals("increase")) {
            cart.addProduct(productCartDto, 1);
            return "redirect:/order/cart";
        }
        if (action.equals("reduce")) {
            cart.addProduct(productCartDto, -1);
            return "redirect:/order/cart";
        }
        if (action.equals("delete")) {
            cart.removeProduct(productCartDto);
            redirectAttributes.addFlashAttribute("msg", "Đã xoá sản phẩm khỏi giỏ hàng");
            return "redirect:/order/cart";
        }
        if(sizes.equals("")){
            redirectAttributes.addFlashAttribute("msg", "Vui lòng chọn size");
            return "redirect:/order/detail/"+id;
        }
        cart.addProduct(productCartDto, quantity);
        redirectAttributes.addFlashAttribute("msg", "Đã thêm vào giỏ hàng");
        return "redirect:/order/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(Model model,Principal principal) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        OrderDto orderDto = new OrderDto();
        if(customer!=null){
            orderDto.setName(customer.getName());
            orderDto.setEmail(customer.getEmail());
            orderDto.setAddress(customer.getAddress());
            orderDto.setPhone_number(customer.getPhone_number());
        }
        model.addAttribute("orderDto",orderDto);
        model.addAttribute("customer",customer);
        return "checkout";
    }
    @RequestMapping("/asd")
    public String ss(){
        return "order/success";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute Cart cart,
                           @Valid@ModelAttribute OrderDto orderDto,
                           @RequestParam String payment,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal, Model model
    ) {
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        new OrderDto().validate(orderDto,bindingResult);
        if (bindingResult.hasErrors()){
            return "checkout";
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderDto,order);
        Account account = accountService.findByEmail(principal.getName());
        order.setAccount(account);
        orderService.add(order);
        Set<OrderDetail> orderDetail = new HashSet<>();
        for (ProductCartDto key : cart.getProducts().keySet()) {
            Product product = productService.selectProductById(key.getId());
            orderDetail.add(new OrderDetail(key.getPrice(), cart.getProducts().get(key), key.getSize(), order, product));
        }
        iodService.addAll(orderDetail);
        if(payment.equals("vnpay")){
            order.setPayment_date(String.valueOf(LocalDate.now()));
            orderService.add(order);
            int amount= (int) (cart.countTotalPayment()*1);
            return "redirect:/payment/create_payment?tempAmount="+ amount;
        }
        cart.clear();
        redirectAttributes.addFlashAttribute("msg", "Đặt hàng thành công");
        return "redirect:/order/home";
    }

    @RequestMapping("/order-detail/{id}")
    public String showOrderDetail(@PathVariable int id,Model model, Principal principal){
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Order order = orderService.getById(id);
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total=0;
        for (OrderDetail o:orderDetails
             ) {
            total += (o.getPrice()*o.getQuantity());
        }
        model.addAttribute("total",total);
        model.addAttribute("orderDetails",orderDetails);
        model.addAttribute("order",order);
        return "order/detail";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam int id,RedirectAttributes redirectAttributes){
        orderService.delete(id);
        redirectAttributes.addFlashAttribute("msg","Xoá thành công");
        return "redirect:/order/list";
    }

    @RequestMapping("/history")
    public String history(@RequestParam(defaultValue = "0") int page, Model model, Principal principal){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Account account = accountService.findByEmail(principal.getName());
        Page<Order> orderPage = orderService.getByAcount(pageable,account);
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        model.addAttribute("orderPage",orderPage);
        return "history";
    }

    @PostMapping("/is-receive")
    public String isReceive(@RequestParam int id,RedirectAttributes redirectAttributes){
        Order order = orderService.getById(id);
        order.setStatus(true);
        orderService.add(order);
        redirectAttributes.addFlashAttribute("msg","Xác nhận thành công");
        return "redirect:/order/history";
    }

    @PostMapping("/is-checkout")
    public String isCheckout(@RequestParam int id,RedirectAttributes redirectAttributes){
        Order order = orderService.getById(id);
        order.setPayment_date(String.valueOf(LocalDate.now()));
        orderService.add(order);
        redirectAttributes.addFlashAttribute("msg","Xác nhận thành công");
        return "redirect:/order/list";
    }

    @RequestMapping("/history-detail/{id}")
    public String historyDetail(@PathVariable int id,Model model,Principal principal){
        Order order = orderService.getById(id);
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total=0;
        for (OrderDetail o:orderDetails) {
            total += (o.getPrice()*o.getQuantity());
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        model.addAttribute("total",total);
        model.addAttribute("orderDetails",orderDetails);
        model.addAttribute("order",order);
        return "historyDetail";
    }
}
