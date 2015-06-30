package ro.teamnet.bootstrap.converter;

public class LongConverter extends BaseConverter<Long> {
    @Override
    public ConverterType type() {
        return ConverterType.LONG;
    }

    @Override
    public Long from(String someStringValue) {
        if(someStringValue==null)
            return null;
        return null;
    }

}
