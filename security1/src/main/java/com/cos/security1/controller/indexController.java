package com.cos.security1.controller;

import com.cos.security1.config.oauth.auth.PrincipalDetails;
import com.cos.security1.damain.User;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(
        Authentication authentication,
        @AuthenticationPrincipal PrincipalDetails userDetails){
        System.out.println("/test/login============");
        PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
        System.out.println("authentication:"+principalDetails.getUser());
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(
            Authentication authentication,
            @AuthenticationPrincipal OAuth2User oAuth) {
        System.out.println("/test/login============");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("authentication:"+oAuth2User.getAttributes());
        System.out.println("OAuth2User"+oAuth.getAttributes());
        return "Oauth 세션 정보 확인하기";
    }

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }
    @GetMapping("/user")
    public @ResponseBody String user(  @AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails:"+principalDetails.getUser());
        return "user";
    }
    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }
    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public  String joinForm(){
        return "joinForm";
    }
    @PostMapping("/join")
    public @ResponseBody String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
    @PreAuthorize("hasROLE('ROLE_MANGER')or hasRole('ROLE_ADMIN')")
    //@PostAuthorize("hasROLE('ROLE_MANGER')or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터정보";
    }
}
