package com.gavin.yjq.counts.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


/**
 * Created by Gavin_Y on 2017/4/9.
 * www.yejiaquan.com
 */
@Entity
public class User {
    @Id
    private long id;

    private String account;

    private String password;

    @Generated(hash = 1235962431)
    public User(long id, String account, String password) {
        this.id = id;
        this.account = account;
        this.password = password;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
