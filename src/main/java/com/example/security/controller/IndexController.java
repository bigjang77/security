package com.example.security.controller;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security.config.auth.PrincipalDetails;
import com.example.security.model.Users;
import com.example.security.repository.UserRepository;



@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(
            Authentication authentication,
            @AuthenticationPrincipal UserDetails userDetails) {//DI(의존성 주입)
        System.out.println("/test/login========");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication" + principalDetails.getUsers());

        System.out.println("userDetails" + userDetails.getUsername());

        return "세션정보확인";
    }
    
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oauth) {//DI(의존성 주입)
        System.out.println("/test/oauth/login========");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication" + oAuth2User.getAttributes());
        System.out.println("oauth2User:" +oauth.getAttributes());
    

        return "OATUH세션정보확인";
    }

    
    @GetMapping("/")
    public String index() {
        return "index";
    }

    //@AuthenticationPrincipal PrincipalDetails principalDetails 분기 할 필요가 없음
    @GetMapping("/users")
    public @ResponseBody String users(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails:"+principalDetails.getUsers());
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

    @Secured("Admin")
    @GetMapping("/info")
    public @ResponseBody String info() {
        return "개인정보";
    }
    


}
