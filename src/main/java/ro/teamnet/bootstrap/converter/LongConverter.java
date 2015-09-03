package ro.teamnet.bootstrap.converter;

/**
 * Convertor de tip Long
 */
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
