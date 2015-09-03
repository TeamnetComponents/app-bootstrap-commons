package ro.teamnet.bootstrap.domain;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class BaseFileUpload implements Serializable{




    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_FILE_UPLOAD_MASTER")
    private FileMaster fileMaster;

    public FileMaster getFileMaster() {
        return fileMaster;
    }

    public void setFileMaster(FileMaster fileMaster) {
        this.fileMaster = fileMaster;
    }


}
