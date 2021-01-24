package com.jumbox.demo.mbg.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "`user`")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String username;

    /**
     * 0 member 1 admin
     */
    private Integer role;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取0 member 1 admin
     *
     * @return role - 0 member 1 admin
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置0 member 1 admin
     *
     * @param role 0 member 1 admin
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}