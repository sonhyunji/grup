package com.pp.grup.Service;

import com.pp.grup.Entity.Product;
import com.pp.grup.Entity.Search;
import com.pp.grup.Repository.productRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class jsoupService {

    public List<Product> crawling(String searchKeyword) throws IOException {
            //꽃씨몰, 엑스플랜트, 심폴 순으로 파싱
            String searchName = searchKeyword; //요청받은 검색어

            //요청받은 검색어 DB에 저장
            Search search = new Search();
            search.setSearchName(searchName);

            String flowerUrl = "https://www.flowerseed-mall.com/shop/shopbrand.html?search=";
            Document flowerDoc = Jsoup.connect(flowerUrl+searchName+"&refer=https:").get();
            // 원하는 태그 선택
            Elements products = flowerDoc.select("div#content > div#itemList > div.itemBox");
            // 각 제품에 대한 정보 추출 및 저장
            for (Element product : products) {
                String storeTitle = product.select("div.itemName > a").text();
                String price = product.select("div.itemPrice > span.price").text();
                String imgUrl = product.select("div.itemImg > a > img").attr("src");
                String storeName = "꽃씨몰";

                // 데이터베이스에 저장하는 코드 작성
                saveProduct(storeTitle, storeName, price, imgUrl);
            }

            String xplantUrl = "https://www.xplant.co.kr/shop/search.php?search_str=";
            Document xplantDoc = Jsoup.connect(xplantUrl+searchName).get();
            // 원하는 태그 선택
            products = xplantDoc.select("div#content > div#itemList > div.itemBox");
            // 각 제품에 대한 정보 추출 및 저장
            for (Element product : products) {
                String storeTitle = product.select("div.itemName > a").text();
                String price = product.select("div.itemPrice > span.price").text();
                String imgUrl = product.select("div.itemImg > a > img").attr("src");
                String storeName = "엑스플랜트";

                // 데이터베이스에 저장하는 코드 작성
                saveProduct(storeTitle, storeName, price, imgUrl);
            }

            String simpolUrl = "https://www.xplant.co.kr/shop/search.php?search_str=";
            Document simpolDoc = Jsoup.connect(simpolUrl+searchName).get();
            // 원하는 태그 선택
            products = simpolDoc.select("div#content > div#itemList > div.itemBox");
            // 각 제품에 대한 정보 추출 및 저장
            for (Element product : products) {
                String storeTitle = product.select("div.itemName > a").text();
                String price = product.select("div.itemPrice > span.price").text();
                String imgUrl = product.select("div.itemImg > a > img").attr("src");
                String storeName = "심폴"; //이 부분은 변경하기

                // 데이터베이스에 저장하는 코드 작성
                saveProduct(storeTitle, storeName, price, imgUrl);
            }

        return null;
    }

    @Autowired
    private productRepository productRepository;

    public void saveProduct(String storeTitle, String storeName, String price, String imgUrl) {
        Product product = new Product();
        product.setStoreTitle(storeTitle);
        product.setStoreName(storeName);
        product.setPrice(price);
        product.setImgUrl(imgUrl);
        productRepository.save(product);
    }

}
