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
    private Department department;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne()
    @JoinColumn(name="dept_id")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

}
