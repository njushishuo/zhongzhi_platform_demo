package edu.nju.zhongzhi_demo.entity;

import edu.nju.zhongzhi_demo.enums.ApiLevel;

import javax.persistence.*;

@Entity
@Table(name = "resrc_api")
@PrimaryKeyJoinColumn()
public class ResrcApi extends Resource {

    private ApiLevel apiLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "api_level")
    public ApiLevel getApiLevel() {
        return apiLevel;
    }

    public void setApiLevel(ApiLevel apiLevel) {
        this.apiLevel = apiLevel;
    }



}
