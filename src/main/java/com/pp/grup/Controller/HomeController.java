package com.pp.grup.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    //기본페이지 요청 메서드
    @GetMapping(value = {"/", "/index", "/main"})
    public String main(HttpSession session) {
        if (session.getAttribute("loginEmail")==null){
            return "index";
        } else {
            session.getAttribute("loginEmail");
            return "main";
        }
    }
}