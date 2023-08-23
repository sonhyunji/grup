package com.pp.grup.Controller;

import com.pp.grup.Entity.Product;
import com.pp.grup.Service.jsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class jsoupController {
    @Autowired
    private jsoupService jsoupService;

    @GetMapping("/crawling/main")
    public String search(){
        return "crawling";
    }

    //크롤링 검색을 한 이후 결과 값을 보여줌
    @PostMapping("/searchSubmit")
    public String handleSubmit(@RequestParam("searchKeyword") String searchKeyword, Model model) throws IOException {
        jsoupService.searchPlant(searchKeyword);
        String searchName = UriUtils.encode(searchKeyword, StandardCharsets.UTF_8);
        return "redirect:/entities?searchName=" + searchName;
    }

    @GetMapping("/entities")
    public String getEntities(@RequestParam("searchName") String searchName, Model model) {
        List<Product> entities = jsoupService.getProductsBySearchName(UriUtils.decode(searchName, StandardCharsets.UTF_8));
        model.addAttribute("entities", entities);
        return "jsoupresult";
    }

    @PostMapping("/deleteEntity")
    public String deleteEntity(@RequestParam("searchKeyword") String searchKeyword) {
        jsoupService.deleteBySearchName(searchKeyword);
        return "redirect:crawling";
    }

}
