package com.simple_man_store.order_detail.controller;

import com.simple_man_store.order.model.Order;
import com.simple_man_store.order.repository.IOrderRepository;
import com.simple_man_store.order.service.IOrderService;
import com.simple_man_store.order_detail.model.OrderDetail;
import com.simple_man_store.order_detail.service.IODService;
import com.simple_man_store.product.model.Product;
import com.simple_man_store.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping("/order-detail")
public class OrderDetailController {
    @Autowired
    private IODService oDService;
@Autowired
private IOrderService orderService;
    @Autowired
    private IProductService productService;


}
