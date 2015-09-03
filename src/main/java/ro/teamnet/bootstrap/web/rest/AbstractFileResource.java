package ro.teamnet.bootstrap.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.teamnet.bootstrap.domain.FileItem;
import ro.teamnet.bootstrap.domain.FileMaster;
import ro.teamnet.bootstrap.plugin.upload.BaseFileService;
import ro.teamnet.bootstrap.plugin.upload.FileMasterReflection;
import ro.teamnet.bootstrap.plugin.upload.FileServicePlugin;
import ro.teamnet.bootstrap.plugin.upload.FileUploadType;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractFileResource<T extends Serializable> extends AbstractResource<T, Long> {

    private BaseFileService<T> baseFileService;

    public AbstractFileResource(BaseFileService<T> baseFileService) {
        super(baseFileService);
        this.baseFileService = baseFileService;
    }

    public abstract String groupId();

    public FileUploadType getFileUploadStrategy(){
        return FileUploadType.USER_SESSION;
    }

    @Inject
    @Qualifier("fileServicePluginRegistry")
    private PluginRegistry<FileServicePlugin, FileUploadType> fileServicePlugins;



    public T save(T entity) {

       T ret= (T) fileServicePlugins.getPluginFor(getFileUploadStrategy()).save(entity,groupId(), baseFileService);
        FileMasterReflection.setFileMasterValue(ret, null);
       return ret;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    @Timed
    public T create(@RequestBody T entity) {
        T ret= (T) fileServicePlugins.getPluginFor(getFileUploadStrategy()).save(entity,groupId(), baseFileService);
        FileMasterReflection.setFileMasterValue(ret, null);
        return ret;
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT)
    @Timed
    public T update(@RequestBody T t) {
        T ret=(T) fileServicePlugins.getPluginFor(getFileUploadStrategy()).update(t,groupId(), baseFileService);
        FileMasterReflection.setFileMasterValue(ret, null);
        return ret;
    }



}
