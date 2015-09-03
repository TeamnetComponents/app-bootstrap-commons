package ro.teamnet.bootstrap.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_FILE_MASTER")
public class FileMaster {

    @Id
    @Column(name = "ID_FILE_MASTER")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "fileMaster",cascade = CascadeType.ALL)
    private List<FileItem> fileItem;

    @Column(name="GROUP_ID")
    private String group;


    @NotNull
    @Column(name = "CREATED")
    @Temporal(TemporalType.DATE)
    protected Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FileItem> getFileItem() {
        return fileItem;
    }

    public void setFileItem(List<FileItem> fileItem) {
        this.fileItem = fileItem;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
