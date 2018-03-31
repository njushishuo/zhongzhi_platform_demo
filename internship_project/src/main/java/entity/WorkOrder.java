package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "work_order", schema = "cloud_resrc_platform", catalog = "")
public class WorkOrder {
    private int id;
    private Serializable status;
    private Serializable reviewResult;
    private Integer appId;
    private Integer applicantId;
    private Timestamp createdTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "status")
    public Serializable getStatus() {
        return status;
    }

    public void setStatus(Serializable status) {
        this.status = status;
    }

    @Basic
    @Column(name = "review_result")
    public Serializable getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Serializable reviewResult) {
        this.reviewResult = reviewResult;
    }

    @Basic
    @Column(name = "app_id")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "applicant_id")
    public Integer getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    @Basic
    @Column(name = "created_time")
    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return id == workOrder.id &&
                Objects.equals(status, workOrder.status) &&
                Objects.equals(reviewResult, workOrder.reviewResult) &&
                Objects.equals(appId, workOrder.appId) &&
                Objects.equals(applicantId, workOrder.applicantId) &&
                Objects.equals(createdTime, workOrder.createdTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, status, reviewResult, appId, applicantId, createdTime);
    }
}
