package com.pp.grup.Controller;

import com.pp.grup.Entity.Product;
import com.pp.grup.Service.JsoupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@Transactional
public class JsoupController {

    @Autowired
    private JsoupService jsoupService;

    @PostMapping("/api/searchSubmit")
    public ResponseEntity<List<Product>> handleSubmit(@RequestBody String searchName) throws IOException {
        System.out.println("들어온 이름 :  " + searchName); //
        String stringWithQuotes = searchName;
        String stringWithoutQuotes = stringWithQuotes.replace("\"", "");
        System.out.println("이름 확인용 :  " + stringWithoutQuotes);
        jsoupService.searchPlant(stringWithoutQuotes);

//        String decodedSearchName = URLDecoder.decode(searchName, "UTF-8");
//        System.out.println("디코딩 :  " + decodedSearchName);
        List<Product> entities = jsoupService.getProductsBySearchName(stringWithoutQuotes);

//        session.setAttribute("SearchName", encodedSearchName);
//        session.setMaxInactiveInterval(10); // 세션을 10초 동안 유지

        return ResponseEntity.ok(entities);
    }
}
