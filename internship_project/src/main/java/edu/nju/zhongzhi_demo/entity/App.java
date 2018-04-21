package edu.nju.zhongzhi_demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class App {
    private int id;
    private String name;
    private Integer ownerId;
    private String description;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
    @Column(name = "owner_id")
    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        App app = (App) o;
        return id == app.id &&
                Objects.equals(name, app.name) &&
                Objects.equals(ownerId, app.ownerId) &&
                Objects.equals(description, app.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, ownerId, description);
    }
}
