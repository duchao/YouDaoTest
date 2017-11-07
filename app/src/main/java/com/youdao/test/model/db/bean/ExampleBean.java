package com.youdao.test.model.db.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by duchao on 2017/11/6.
 */
@Entity
public class ExampleBean {
    @Id
    private Long id;
    private String name;
    @Generated(hash = 1511241133)
    public ExampleBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1952999986)
    public ExampleBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
