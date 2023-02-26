package com.springinaction.tacos.web;

import com.springinaction.tacos.service.OrderAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final OrderAdminService orderAdminService;

    @PostMapping("/deleteOrders")
    public String deleteAllOrders() {
        orderAdminService.deleteAllOrders();
        return "redirect:/admin";
    }

}
