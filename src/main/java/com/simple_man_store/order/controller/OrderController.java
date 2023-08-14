package com.simple_man_store.order.controller;

import com.simple_man_store.account.model.Account;
import com.simple_man_store.account.service.IAccountService;
import com.simple_man_store.customer.model.Customer;
import com.simple_man_store.customer.service.customer.ICustomerService;
import com.simple_man_store.customer.until.DateUtils;
import com.simple_man_store.order.dto.OrderDto;
import com.simple_man_store.order.dto.ProductCartDto;
import com.simple_man_store.order.model.Cart;
import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.send_email.SendEmail;
import com.simple_man_store.order.service.IOrderService;
import com.simple_man_store.order_detail.model.OrderDetail;
import com.simple_man_store.order_detail.service.IODService;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.model.Size;
import com.simple_man_store.product.model.Warehouse;
import com.simple_man_store.product.service.IProductService;
import com.simple_man_store.product.service.ISizeService;
import com.simple_man_store.product.service.IWarehouseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
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

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private SendEmail sendEmail;

//    @Scheduled(cron = "*/ * * * * *")

    @Scheduled(cron = "0 0 12 * * ?")
    public void scheduleTaskUsingCronExpression() {
        orderService.checkTotalToRankUpCustomer();
    }

    @RequestMapping("/cart")
    public String showCart(@ModelAttribute Cart cart, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        List<Size> sizeList = sizeService.showListSize();
        model.addAttribute("type", type);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("customer_name", customer.getName());
        return "cart";
    }


    @RequestMapping("/detail/{id}")
    public String showDetail(@PathVariable int id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Warehouse warehouse=warehouseService.selectWarehouseByProductId(id);
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Product product = productService.selectProductById(id);
        List<Size> sizeList = sizeService.showListSize();
        model.addAttribute("product", product);
        model.addAttribute("sizeList", sizeList);
        model.addAttribute("warehouse", warehouse);
        return "detail";
    }

    @RequestMapping("/list")
    public String showList(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "") String searchName,
                           Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<Order> orderPage = orderService.findAllInPage(pageable, searchName);
        for (Order o: orderPage
             ) {
            o.setOrder_date(DateUtils.reverseDate(o.getOrder_date()));
            if(o.getPayment_date()!=null) {
                o.setPayment_date(DateUtils.reverseDate(o.getPayment_date()));
            }
        }
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
        ProductCartDto productCartDto = new ProductCartDto(product.getId(), product.getName(), product.getPrice(), product.getImage(), sizes);
        if (quantity < 1) {
            redirectAttributes.addFlashAttribute("msg", "Vui lòng chọn số lượng lơn hơn 0");
            return "redirect:/order/detail/" + id;
        }
        if (action.equals("increase")) {
            cart.addProduct(productCartDto, 1);
            for (ProductCartDto key : cart.getProducts().keySet()) {
                if (cart.getProducts().get(key) > warehouseService.selectWarehouseByProductId(key.getId()).getQuantity()) {
                    redirectAttributes.addFlashAttribute("msgError", "Số lượng sản phẩm đã vượt quá số lượng trong kho");
                    cart.addProduct(productCartDto, -1);
                    return "redirect:/order/cart";
                }
            }
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
        if (sizes.equals("")) {
            redirectAttributes.addFlashAttribute("msg", "Vui lòng chọn size");
            return "redirect:/order/detail/" + id;
        }
        cart.addProduct(productCartDto, quantity);
        for (ProductCartDto key : cart.getProducts().keySet()) {
            if (cart.getProducts().get(key) > warehouseService.selectWarehouseByProductId(key.getId()).getQuantity()) {
                redirectAttributes.addFlashAttribute("msgError", "Số lượng sản phẩm đã vượt quá số lượng trong kho");
                cart.removeProduct(productCartDto);
                return "redirect:/order/cart";
            }
        }
        redirectAttributes.addFlashAttribute("msg", "Đã thêm vào giỏ hàng");
        return "redirect:/order/cart";
    }

    @GetMapping("/checkout")
    public String showCheckout(@ModelAttribute Cart cart, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Customer baseCustomer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", baseCustomer.getName());
        OrderDto orderDto = new OrderDto();
        if (principal == null) {
            return "redirect:/login";
        }
        if (baseCustomer != null) {
            orderDto.setName(baseCustomer.getName());
            orderDto.setEmail(baseCustomer.getEmail());
            orderDto.setAddress(baseCustomer.getAddress());
            orderDto.setPhone_number(baseCustomer.getPhone_number());
        }
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("customer", baseCustomer);
        return "checkout";
    }

    @RequestMapping("/success")
    public String showSuccess(@ModelAttribute Cart cart,Principal principal,
                              @RequestParam(value = "amount") String amount,
                              @RequestParam(value = "txnRef") String txnRef,
                              @RequestParam(value = "date") String date,
                              @RequestParam(value = "bankCode") String bankCode,
                              @RequestParam(value = "info") String info,
                              Model model){
        if (principal == null) {
            return "redirect:/login";
        }
        Account account = accountService.findByEmail(principal.getName());
        Page<Order> order = orderService.getByAcount(PageRequest.of(0,1,Sort.by("id").descending()),account);
        for (Order o:order
             ) {
            o.setStatus(2);
            o.setPayment_date(String.valueOf(LocalDate.now()));
            orderService.add(o);
        }
        model.addAttribute("amount",(Integer.parseInt(amount)/100));
        model.addAttribute("bankCode",bankCode);
        model.addAttribute("info",info);
        model.addAttribute("txnRef",txnRef);
        model.addAttribute("date",date);
        cart.clear();
        return "order/success";
    }

    @PostMapping("/checkout")
    public String checkout(@ModelAttribute Cart cart,
                           @Valid @ModelAttribute OrderDto orderDto,
                           @RequestParam String payment,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal, Model model
    ) {
        new OrderDto().validate(orderDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "checkout";
        }
        System.out.println(orderDto.getStatus());
        if(orderDto.getStatus()==1){
            return "redirect:/order/history";
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        Account account = accountService.findByEmail(principal.getName());
        order.setAccount(account);
        orderService.add(order);
        Set<OrderDetail> orderDetail = new HashSet<>();
        for (ProductCartDto key : cart.getProducts().keySet()) {
            int quantity = cart.getProducts().get(key);
            Product product = productService.selectProductById(key.getId());
            Warehouse warehouse = warehouseService.selectWarehouseByProductId(product.getId());
            warehouse.setQuantity(warehouse.getQuantity() - quantity);
            orderDetail.add(new OrderDetail(key.getPrice(), quantity, key.getSize(), order, product));
        }
        iodService.addAll(orderDetail);
        try {
            sendEmail.sendHtmlEmail(order.getEmail(),order,orderDetail);
        } catch (MessagingException e) {
            orderService.deleteLast();
            redirectAttributes.addFlashAttribute("msgError","Gửi email thất bại! vui long kiểm tra lại địa chỉ email.");
            return "redirect:/order/checkout";
        }
        if (payment.equals("vnpay")) {
            order.setStatus(1);
            orderService.add(order);
            int amount = (int) (cart.countTotalPayment() * 1);
            return "redirect:/payment/create_payment?tempAmount=" + amount;
        }
        cart.clear();
//        Integer total = customerService.findSumPriceByEmail(principal.getName());
        redirectAttributes.addFlashAttribute("msg", "Đặt hàng thành công");
        return "redirect:/";
    }

    @RequestMapping("/order-detail/{id}")
    public String showOrderDetail(@PathVariable int id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        Order order = orderService.getById(id);
        order.setOrder_date(DateUtils.reverseDate(order.getOrder_date()));
        order.setPayment_date(DateUtils.reverseDate(order.getPayment_date()));
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total = 0;
        for (OrderDetail o : orderDetails
        ) {
            total += (o.getPrice() * o.getQuantity());
        }
        model.addAttribute("total", total);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        return "order/detail";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam int id, RedirectAttributes redirectAttributes) {
        orderService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Xoá thành công");
        return "redirect:/order/list";
    }

    @RequestMapping("/history")
    public String history(@RequestParam(defaultValue = "0") int page, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Account account = accountService.findByEmail(principal.getName());
        Page<Order> orderPage = orderService.getByAcount(pageable, account);
        for (Order o: orderPage
        ) {
            o.setOrder_date(DateUtils.reverseDate(o.getOrder_date()));
            if(o.getPayment_date()!=null) {
                o.setPayment_date(DateUtils.reverseDate(o.getPayment_date()));
            }
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        model.addAttribute("orderPage", orderPage);
        return "history";
    }

    @PostMapping("/is-receive")
    public String isReceive(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Order order = orderService.getById(id);
        orderService.add(order);
        redirectAttributes.addFlashAttribute("msg", "Xác nhận thành công");
        return "redirect:/order/history";
    }

    @PostMapping("/is-checkout")
    public String isCheckout(@RequestParam int id, RedirectAttributes redirectAttributes) {
        Order order = orderService.getById(id);
        order.setPayment_date(String.valueOf(LocalDate.now()));
        orderService.add(order);
        redirectAttributes.addFlashAttribute("msg", "Xác nhận thành công");
        return "redirect:/order/list";
    }

    @RequestMapping("/history-detail/{id}")
    public String historyDetail(@PathVariable int id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Order order = orderService.getById(id);
        order.setOrder_date(DateUtils.reverseDate(order.getOrder_date()));
        if(order.getPayment_date()!=null) {
            order.setPayment_date(DateUtils.reverseDate(order.getPayment_date()));
        }
        List<OrderDetail> orderDetails = iodService.getByOrder(order);
        double total = 0;
        for (OrderDetail o : orderDetails) {
            total += (o.getPrice() * o.getQuantity());
        }
        String email = principal.getName();
        Customer customer = customerService.findByEmail(email);
        String type = customerService.findCustomerTypeByEmail(email);
        model.addAttribute("type", type);
        model.addAttribute("customer_name", customer.getName());
        model.addAttribute("total", total);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("order", order);
        return "historyDetail";
    }
}
