package ro.teamnet.bootstrap.converter;

public interface Converter<T> {
    ConverterType type();
    T from(String someStringValue);
    String to(T value);
}
