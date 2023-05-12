package com.pp.grup.Controller;

import com.pp.grup.Service.jsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class jsoupController {

    @Autowired
    private jsoupService jsoupService;

    @GetMapping("/search")
    public String searching(){
        return "searchEX";
    }

    @GetMapping("/search/plants")
    public String crawling(@RequestParam("query") String query, Model model) {
        // 검색어에 대한 정보 추출

        // 검색 결과 정보 추출

        return "searchEXview";
    }
}
