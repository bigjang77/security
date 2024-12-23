package com.example.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.model.Users;
import com.example.security.repository.UserRepository;


@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/users")
    public @ResponseBody String users() {
        return "users";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }
    
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/join")
    public String join(Users users) {
        System.out.println(users);
        users.setRole("USER");
        String rawPassword = users.getPassword();
        String encodePassword = bCryptPasswordEncoder.encode(rawPassword);
        users.setPassword(encodePassword);

        userRepository.save(users);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }


}
