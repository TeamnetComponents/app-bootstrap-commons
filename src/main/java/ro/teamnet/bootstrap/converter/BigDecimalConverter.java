package ro.teamnet.bootstrap.converter;

import java.math.BigDecimal;

public class BigDecimalConverter extends BaseConverter<BigDecimal>{
    @Override
    public ConverterType type() {
        return ConverterType.BIG_DECIMAL;
    }

    @Override
    public BigDecimal from(String someStringValue) {
        if(someStringValue==null)
            return null;
        return new BigDecimal(someStringValue);
    }
}
