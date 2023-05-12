package com.pp.grup.Entity;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "storeTitle")
    private String storeTitle;

    @Column(name = "storeName")
    private String storeName;

    @Column(name = "price")
    private String price;

    @Column(name = "imgUrl")
    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "searchId")
    private Search search;


    public void setStoreTitle(String storeTitle) {
        this.storeTitle = storeTitle;
    }
    public String getStoreTitle() {
        return storeTitle;
    }

    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getStoreName() { return storeName; }

    public void setPrice(String price) { this.price = price; }
    public String getPrice() { return price; }

    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public String getImgUrl() { return imgUrl; }
}
