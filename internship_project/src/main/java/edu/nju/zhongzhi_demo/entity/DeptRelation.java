package edu.nju.zhongzhi_demo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dept_relation", schema = "cloud_resrc_platform", catalog = "")
@IdClass(DeptRelationPK.class)
public class DeptRelation {
    private int deptId;
    private int parentId;

    @Id
    @Column(name = "dept_id")
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Id
    @Column(name = "parent_id")
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptRelation that = (DeptRelation) o;
        return deptId == that.deptId &&
                parentId == that.parentId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(deptId, parentId);
    }
}
