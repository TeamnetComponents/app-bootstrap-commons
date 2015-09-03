package ro.teamnet.bootstrap.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdArraySerializers;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;

@Entity
@Table(name="T_FILE_ITEM")
public class FileItem implements Serializable{

    @Id
    @Column(name="ID_FILE_ITEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SIZE")
    private Long size;

    @Column(name = "CONTENT")
    @Lob
    @Basic(fetch=FetchType.LAZY)
    @JsonIgnore
    private byte[] content;

    @Column(name="TOKEN")
    private String token;

    @Column(name = "CONTENT_TYPE")
    private String type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "FK_FILE_MASTER")
    private FileMaster fileMaster;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }


    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static FileItem from(MultipartFile multipartFile,String token) throws IOException {
        FileItem ret = new FileItem();
        ret.setContent(multipartFile.getBytes());
        ret.setName(multipartFile.getOriginalFilename());
        ret.setSize(multipartFile.getSize());
        ret.setType(multipartFile.getContentType());
        ret.setToken(token);
        return ret;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public FileMaster getFileMaster() {
        return fileMaster;
    }

    public void setFileMaster(FileMaster fileMaster) {
        this.fileMaster = fileMaster;
    }
}
