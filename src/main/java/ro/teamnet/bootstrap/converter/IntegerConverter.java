package ro.teamnet.bootstrap.converter;

/**
 * Convertor de tip Integer
 */
public class IntegerConverter extends BaseConverter<java.lang.Integer> {
    @Override
    public ConverterType type() {
        return ConverterType.INTEGER;
    }

    @Override
    public Integer from(String someStringValue) {
        if(someStringValue==null)
            return null;
        return Integer.getInteger(someStringValue);
    }

}
