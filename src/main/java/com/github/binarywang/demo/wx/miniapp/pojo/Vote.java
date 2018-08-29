package com.github.binarywang.demo.wx.miniapp.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "vote")
public class Vote implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long id;

    @Column()
    private String name;

    @Column()
    private String pic;

    @Column()
    private String piao;

    @Column()
    private Integer number;

    @Column()
    private String username;

    @Column()
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPiao() {
        return piao;
    }

    public void setPiao(String piao) {
        this.piao = piao;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column()
    private String desc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }



    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
