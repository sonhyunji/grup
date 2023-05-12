package com.pp.grup.Entity;

import javax.persistence.*;

@Entity
@Table(name = "search")
public class Search {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "searchName")
    private String searchName;

    public void setSearchName(String searchName) { this.searchName = searchName; }

    public String getSearchName() { return searchName; }
}
