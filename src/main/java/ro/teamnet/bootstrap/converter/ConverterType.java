package ro.teamnet.bootstrap.converter;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * Tiupri de convertoare
 */
public enum ConverterType {
    BOOLEAN(Boolean.class),
    LONG(Long.class),
    INTEGER(Integer.class),
    BIG_DECIMAL(BigDecimal.class),
    DATE_TIME(DateTime.class),
    ;

    private Class clazz;

    ConverterType(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    public static ConverterType getByType(Class type){
        for (ConverterType converterType : values()) {
            if(converterType.getClazz().equals(type))
                return converterType;
        }
        throw new RuntimeException("Caz netratat!!!!");
    }
}
