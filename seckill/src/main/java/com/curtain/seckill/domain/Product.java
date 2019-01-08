package com.curtain.seckill.domain;

import com.curtain.seckill.Exception.SecKillException;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Curtain
 * @date 2019/1/3 14:39
 */

@Entity
@Table(name = "T_Product")
public class Product implements Serializable{

    /** 主键id */
    @Id
    @GeneratedValue
    private Integer id;

    /** 商品名 */
    private String name;

    /** 库存 */
    private int stock;

    /** 版本号 */
    @Version
    private int version;

    public Product(String name, int stock, int version) {
        setName(name);
        setStock(stock);
        setVersion(version);
    }

    public Product() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        if (name==null||name.length()>100){
            throw new SecKillException("商品名验证不通过");
        }

        this.name = name;

    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public int getVersion() {
        return version;
    }
}
