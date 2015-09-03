package ro.teamnet.bootstrap.converter;

/**
 * Interfata descrie un Convertor
 * @param <T>
 */
public interface Converter<T> {
    ConverterType type();
    T from(String someStringValue);
    String to(T value);
}
