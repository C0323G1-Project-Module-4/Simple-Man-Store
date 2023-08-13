package com.simple_man_store.order.controller;

import com.simple_man_store.order.config.Config;
import com.simple_man_store.order.dto.PaymentRestDto;
import com.simple_man_store.order.model.Cart;
import com.simple_man_store.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("cart")
@RequestMapping("/payment")
public class PaymentController {

    @ModelAttribute("cart")
    public Cart setupCart() {
        return new Cart();
    }
    @Autowired
    private IOrderService orderService;
    @GetMapping("/create_payment")
    public String createPayment(@RequestParam String tempAmount) throws UnsupportedEncodingException {

////        String orderType = "other";
////        long amount = Integer.parseInt(req.getParameter("amount"))*100;
////        String bankCode = req.getParameter("bankCode");
//
//        String amount = 100000*100 +"";
//        String vnp_IpAddr = "127.0.0.1";
//        String vnp_TxnRef = Config.getRandomNumber(8);
////        String vnp_IpAddr = Config.getIpAddress(req);
//
//        String vnp_TmnCode = Config.vnp_TmnCode;
//
//        Map<String, String> vnp_Params = new HashMap<>();
//        vnp_Params.put("vnp_Version", Config.vnp_Version);
//        vnp_Params.put("vnp_Command", Config.vnp_Command);
//        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//        vnp_Params.put("vnp_Amount", amount);
//        vnp_Params.put("vnp_CurrCode", "VND");
//        vnp_Params.put("vnp_BankCode", "NCB");
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
//        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
//
//        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//        String vnp_CreateDate = formatter.format(cld.getTime());
//        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//
////        cld.add(Calendar.MINUTE, 15);
////        String vnp_ExpireDate = formatter.format(cld.getTime());
////        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//        List fieldNames = new ArrayList(vnp_Params.keySet());
//        Collections.sort(fieldNames);
//        StringBuilder hashData = new StringBuilder();
//        StringBuilder query = new StringBuilder();
//        Iterator itr = fieldNames.iterator();
//        while (itr.hasNext()) {
//            String fieldName = (String) itr.next();
//            String fieldValue = (String) vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                //Build hash data
//                hashData.append(fieldName);
//                hashData.append('=');
//                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                //Build query
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//                query.append('=');
//                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                if (itr.hasNext()) {
//                    query.append('&');
//                    hashData.append('&');
//                }
//            }
//        }
//        String queryUrl = query.toString();
//        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
//        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//
//        PaymentRestDto paymentRestDto = new PaymentRestDto();
//        paymentRestDto.setStatus("OK");
//        paymentRestDto.setMessage("Successfully");
//        paymentRestDto.setURL(paymentUrl);
//
//        return ResponseEntity.status(HttpStatus.OK).body(paymentRestDto);



        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_OrderInfo = "Thanh toan don hang tu Simple-Man " + vnp_TxnRef;
        String orderType = "150000";
        String vnp_IpAddr = "0:0:0:0:0:0:0:1";
        String vnp_TmnCode = Config.vnp_TmnCode;
            String amount = Integer.parseInt(tempAmount) * 100 + "";
            Map vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", amount);
            vnp_Params.put("vnp_CurrCode", "VND");
            String bank_code = "NCB";
            if (bank_code != null && !bank_code.isEmpty()) {
                vnp_Params.put("vnp_BankCode", bank_code);
            }
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);

            vnp_Params.put("vnp_Locale", "vn");
            vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
            Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnp_CreateDate = formatter.format(cld.getTime());
            vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
            cld.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(cld.getTime());
            //Add Params of 2.1.0 Version
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            //Build data to hash and querystring
            List fieldNames = new ArrayList(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();
            StringBuilder query = new StringBuilder();
            Iterator itr = fieldNames.iterator();
            while (itr.hasNext()) {
                String fieldName = (String) itr.next();
                String fieldValue = (String) vnp_Params.get(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    //Build hash data
                    hashData.append(fieldName);
                    hashData.append('=');
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    //Build query
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                    query.append('=');
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                    if (itr.hasNext()) {
                        query.append('&');
                        hashData.append('&');
                    }
                }
            }
            String queryUrl = query.toString();
            String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
            queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
            String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
            // Chuyển hướng đến URL thanh toán
        PaymentRestDto paymentRestDto = new PaymentRestDto();

        return "redirect:"+paymentUrl;

    }

    @RequestMapping("/payment-info")
    public String paymentInfo(@RequestParam(value = "vnp_TransactionStatus") String status,
                              @RequestParam(value = "vnp_Amount") String amount,
                              @RequestParam(value = "vnp_TxnRef") String txnRef,
                              @RequestParam(value = "vnp_PayDate") String date,
                              @RequestParam(value = "vnp_BankCode") String bankCode,
                              @RequestParam(value = "vnp_OrderInfo") String info,
                              Model model,
                              RedirectAttributes redirectAttributes
                              ){
        if(status.equals("00")){
            return "redirect:/order/success?amount="+amount+"&bankCode="+bankCode+"&info="+info+"&txnRef="+txnRef+"&date="+date;
        }else {
            orderService.deleteLast();
            redirectAttributes.addFlashAttribute("msg","Thanh toán thất bại");
            return "redirect:/order/checkout";
        }


    }
}
