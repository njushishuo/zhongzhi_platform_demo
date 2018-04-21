package edu.nju.zhongzhi_demo.entity;

import edu.nju.zhongzhi_demo.enums.ResourceStatus;
import edu.nju.zhongzhi_demo.enums.ResourceType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "work_order_rsrc", schema = "cloud_resrc_platform", catalog = "")
public class WorkOrderRsrc {
    private int id;
    private int appId;
    private int workOrderId;
    private int resrcId;
    private ResourceType resrcType;
    private ResourceStatus resrcStatus;
    private Integer reviewDeptId;
    private Timestamp processTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "work_order_id")
    public int getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(int workOrderId) {
        this.workOrderId = workOrderId;
    }


    @Basic
    @Column(name = "app_id")
    public int getAppId() {
        return this.appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "resrc_id")
    public int getResrcId() {
        return resrcId;
    }

    public void setResrcId(int resrcId) {
        this.resrcId = resrcId;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "resrc_type")
    public ResourceType getResrcType() {
        return resrcType;
    }

    public void setResrcType(ResourceType resrcType) {
        this.resrcType = resrcType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "resrc_status")
    public ResourceStatus getResrcStatus() {
        return resrcStatus;
    }

    public void setResrcStatus(ResourceStatus resrcStatus) {
        this.resrcStatus = resrcStatus;
    }

    @Basic
    @Column(name = "review_dept_id")
    public Integer getReviewDeptId() {
        return reviewDeptId;
    }

    public void setReviewDeptId(Integer reviewDeptId) {
        this.reviewDeptId = reviewDeptId;
    }

    @Basic
    @Column(name = "process_time")
    public Timestamp getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Timestamp processTime) {
        this.processTime = processTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrderRsrc that = (WorkOrderRsrc) o;
        return id == that.id &&
                workOrderId == that.workOrderId &&
                resrcId == that.resrcId &&
                Objects.equals(resrcType, that.resrcType) &&
                Objects.equals(resrcStatus, that.resrcStatus) &&
                Objects.equals(reviewDeptId, that.reviewDeptId) &&
                Objects.equals(processTime, that.processTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, workOrderId, resrcId, resrcType, resrcStatus, reviewDeptId, processTime);
    }
}
