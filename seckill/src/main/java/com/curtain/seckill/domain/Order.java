package com.curtain.seckill.domain;

import com.curtain.seckill.Exception.SecKillException;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Curtain
 * @date 2019/1/3 14:48
 */

@Entity
@Table(name = "T_Order")
public class Order {

    /** 主键id */
    @Id
    @GeneratedValue
    private Integer id;

    /** 商品id */
    private Integer pid;

    public Order() {
    }

    /** 创建时间 */
    private long createTime;

    public Integer getId() {
        return id;
    }

    public Order(Integer pid, long createTime) {
        setPid(pid);
        setCreateTime(createTime);
    }

    public Integer getPid() {
        if (pid==null){
            throw new SecKillException("商品id验证不通过");
        }
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}

