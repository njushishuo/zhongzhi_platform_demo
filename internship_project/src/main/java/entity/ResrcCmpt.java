package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "resrc_cmpt", schema = "cloud_resrc_platform", catalog = "")
public class ResrcCmpt {
    private int id;
    private String name;
    private Serializable cmptType;
    private Integer count;
    private String config;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "cmpt_type")
    public Serializable getCmptType() {
        return cmptType;
    }

    public void setCmptType(Serializable cmptType) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResrcCmpt resrcCmpt = (ResrcCmpt) o;
        return id == resrcCmpt.id &&
                Objects.equals(name, resrcCmpt.name) &&
                Objects.equals(cmptType, resrcCmpt.cmptType) &&
                Objects.equals(count, resrcCmpt.count) &&
                Objects.equals(config, resrcCmpt.config);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, cmptType, count, config);
    }
}
