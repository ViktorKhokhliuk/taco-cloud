package com.springinaction.tacos.security;

import com.springinaction.tacos.User;
import com.springinaction.tacos.data.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value
    @Builder
    public static class RegistrationForm {
        String username;
        String password;
        String fullName;
        String street;
        String city;
        String state;
        String zip;
        String phone;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@RequestBody RegistrationForm form) {
        userRepository.save(User.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .fullName(form.getFullName())
                .street(form.getStreet())
                .city(form.getCity())
                .state(form.getState())
                .zip(form.getZip())
                .phoneNumber(form.getPhone())
                .build());

        return "redirect:/login";
    }

}
