package edu.nju.zhongzhi_demo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DeptRelationPK implements Serializable {
    private int deptId;
    private int parentId;

    @Column(name = "dept_id")
    @Id
    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Column(name = "parent_id")
    @Id
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
        DeptRelationPK that = (DeptRelationPK) o;
        return deptId == that.deptId &&
                parentId == that.parentId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(deptId, parentId);
    }
}
