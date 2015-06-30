package ro.teamnet.bootstrap.converter;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeConverter extends BaseConverter<DateTime> {

    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String DD_MMM_YYYY_HH_MM = "dd-MMM-yyyy HH:mm";

    @Override
    public ConverterType type() {
        return ConverterType.DATE_TIME;
    }

    @Override
    public DateTime from(String someStringValue) {
        if(someStringValue==null)
            return null;
        Date data;
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DD_MM_YYYY);
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat(DD_MMM_YYYY_HH_MM);
        try {
            data=simpleDateFormat.parse(someStringValue);
        } catch (ParseException e) {
            try {
                data=simpleDateFormat1.parse(someStringValue);
            } catch (ParseException e1) {
                throw new RuntimeException(e1);
            }
        }
        if(data!=null){
            return new DateTime(data);
        }
        return null;
    }


}
