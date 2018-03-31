package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "resrc_api", schema = "cloud_resrc_platform", catalog = "")
public class ResrcApi {
    private int id;
    private String name;
    private Serializable level;

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
    @Column(name = "level")
    public Serializable getLevel() {
        return level;
    }

    public void setLevel(Serializable level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResrcApi resrcApi = (ResrcApi) o;
        return id == resrcApi.id &&
                Objects.equals(name, resrcApi.name) &&
                Objects.equals(level, resrcApi.level);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, level);
    }
}
