package edu.nju.zhongzhi_demo.entity;

import edu.nju.zhongzhi_demo.enums.CmptType;

import javax.persistence.*;

@Entity
@Table(name = "resrc_cmpt")
@PrimaryKeyJoinColumn()
public class ResrcCmpt extends Resource {

    private CmptType cmptType;
    private Integer count;
    private String config;

    @Enumerated(EnumType.STRING)
    @Column(name = "cmpt_type")
    public CmptType getCmptType() {
        return cmptType;
    }

    public void setCmptType(CmptType cmptType) {
        this.cmptType = cmptType;
    }

    @Basic
    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Basic
    @Column(name = "config")
    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

}
