package com.pp.grup.Service;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

@Service
public class jsoupService {
    public void crawling() {
        try {
            // 웹 페이지 파싱
            Document doc = Jsoup.connect("https://www.flowerseed-mall.com/shop/shopbrand.html?search=%C0%E5%B9%CC&refer=https:").get();
            // 원하는 태그 선택
            Elements products = doc.select("div#content > div#itemList > div.itemBox");
            // 각 제품에 대한 정보 추출 및 저장
            for (Element product : products) {
                String name = product.select("div.itemName > a").text();
                String price = product.select("div.itemPrice > span.price").text();
                String imgUrl = product.select("div.itemImg > a > img").attr("src");

                // 데이터베이스에 저장하는 코드 작성
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
