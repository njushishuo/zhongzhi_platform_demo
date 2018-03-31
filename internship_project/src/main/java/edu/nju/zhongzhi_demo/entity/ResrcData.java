package edu.nju.zhongzhi_demo.entity;

import edu.nju.zhongzhi_demo.enums.DataType;

import javax.persistence.*;

@Entity
@Table(name = "resrc_data")
@PrimaryKeyJoinColumn()
public class ResrcData extends Resource {

    private DataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

}
