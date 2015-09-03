package ro.teamnet.bootstrap.plugin.upload;

import org.springframework.plugin.core.Plugin;
import org.springframework.web.multipart.MultipartFile;
import ro.teamnet.bootstrap.domain.FileItem;
import ro.teamnet.bootstrap.domain.FileMaster;

import java.io.Serializable;
import java.util.List;

public interface FileServicePlugin extends Plugin<FileUploadType> {

    String uploadFile(MultipartFile multipartFile,String groupId);

    FileMaster getFilesForGroup(String groupId);

    void cleanUpForGroup(String groupId);

    void cleanUp(String token,String groupId);

    Serializable save(Serializable entity,String groupId,BaseFileService baseFileService);
    Serializable update(Serializable entity,String groupId,BaseFileService baseFileService);

    FileItem downloadFile(String token,String groupId);

    List<FileItem> getAllFiles(Long fileMasterId);


}
