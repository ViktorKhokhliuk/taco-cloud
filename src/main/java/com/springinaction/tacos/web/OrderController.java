package com.springinaction.tacos.web;

import com.springinaction.tacos.TacoOrder;
import com.springinaction.tacos.User;
import com.springinaction.tacos.data.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
    private final OrderRepository orderRepository;

    @GetMapping("/current")
    public String orderForm(Model model) {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder tacoOrder,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "orderForm";
        }
        orderRepository.save(tacoOrder.toBuilder()
                .user(user)
                .build());
        sessionStatus.setComplete();
        return "redirect:/";
    }
}
