package com.pp.grup.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    @Column(name = "writer")
    private String memberName;

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}