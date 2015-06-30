package ro.teamnet.bootstrap.converter;

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
