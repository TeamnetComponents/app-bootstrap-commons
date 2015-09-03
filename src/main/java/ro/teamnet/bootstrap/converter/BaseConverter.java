package ro.teamnet.bootstrap.converter;

/**
 * Clasa template entru convertori
 * @param <T> - Tipul de baza pentru care se face conversia
 */
public abstract class BaseConverter<T> implements Converter<T> {

    /**
     * Metoda face conversia de la obiectul T la tsring
     * @param value - T obiectul care este convertit
     * @return String
     */
    public String to(T value) {
        if(value==null)
            return null;
        return value.toString();
    }
}
