package ro.teamnet.bootstrap.plugin.upload;

import ro.teamnet.bootstrap.domain.FileItem;
import ro.teamnet.bootstrap.domain.FileMaster;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileMasterReflection {

    public static Map<Class, Field> SAVED_FILE_UPLOAD_FIELD = new HashMap<>();


    public static void setFileMasterValue(Object entity, FileMaster value){
        Class clazz=entity.getClass();
        Field fileUploadMasterField=getFileUploadMasterField(clazz);

        try {
            fileUploadMasterField.set(entity,value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateFileMasterWithFiles(Object entity, List<FileItem> files){

        FileMaster oldFileMaster=getFileMasterValue(entity);
        oldFileMaster.getFileItem().addAll(files);



    }




    public static FileMaster getFileMasterValue(Object entity){
        FileMaster ret=null;
        Class clazz=entity.getClass();
        Field fileUploadMasterField=getFileUploadMasterField(clazz);
        try {
            ret= (FileMaster) fileUploadMasterField.get(entity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static Field getFileUploadMasterField(Class clazz) {
        Field ret=null;
        if(!SAVED_FILE_UPLOAD_FIELD.containsKey(clazz)){
            Field[] fields=clazz.getDeclaredFields();
            for (Field field : fields) {
                if(field.getType().equals(FileMaster.class)){
                    ret=field;
                    break;
                }
            }
            if(ret==null){
                throw new RuntimeException("Class "+clazz.getName()+" it should have a field with type "+FileMaster.class.getName());
            }
            ret.setAccessible(true);
            SAVED_FILE_UPLOAD_FIELD.put(clazz,ret);
        }
        ret=SAVED_FILE_UPLOAD_FIELD.get(clazz);
        return ret;
    }
}
