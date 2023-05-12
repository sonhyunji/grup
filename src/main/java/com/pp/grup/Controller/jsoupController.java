package com.pp.grup.Controller;

import com.pp.grup.Service.jsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class jsoupController {

    @Autowired
    private jsoupService jsoupService;

    @GetMapping("/")
    public String crawling(){
        jsoupService.crawling();
        return "크롤링을 완료하였습니다.";
    }
}
