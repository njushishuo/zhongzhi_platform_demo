package edu.nju.zhongzhi_demo.entity;

import edu.nju.zhongzhi_demo.enums.ResourceType;

import javax.persistence.*;

@Entity
@Table(name = "resource")
@Inheritance(strategy = InheritanceType.JOINED )
public class Resource {
    private int id;
    private String name;
    private ResourceType resourceType;
    private Integer deptId;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName(){return name;}

    public void setName(String name) {this.name = name;}

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    @Basic
    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

}
