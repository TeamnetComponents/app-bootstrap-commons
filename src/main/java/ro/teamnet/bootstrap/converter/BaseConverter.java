package ro.teamnet.bootstrap.converter;

public abstract class BaseConverter<T> implements Converter<T> {


    public String to(T value) {
        if(value==null)
            return null;
        return value.toString();
    }
}
