package ro.teamnet.bootstrap.converter;

import java.math.BigDecimal;

/**
 * Convertor din String in BigDecimal si invers
 */
public class BigDecimalConverter extends BaseConverter<BigDecimal>{
    @Override
    public ConverterType type() {
        return ConverterType.BIG_DECIMAL;
    }

    /**
     * Convertim
     * @param someStringValue
     * @return
     */
    @Override
    public BigDecimal from(String someStringValue) {
        if(someStringValue==null)
            return null;
        return new BigDecimal(someStringValue);
    }
}
