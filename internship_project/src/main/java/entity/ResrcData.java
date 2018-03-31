package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "resrc_data", schema = "cloud_resrc_platform", catalog = "")
public class ResrcData {
    private int id;
    private Serializable docType;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "doc_type")
    public Serializable getDocType() {
        return docType;
    }

    public void setDocType(Serializable docType) {
        this.docType = docType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResrcData resrcData = (ResrcData) o;
        return id == resrcData.id &&
                Objects.equals(docType, resrcData.docType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, docType);
    }
}
