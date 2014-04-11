package uni.cluster.main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author ITE
 */
public class Test {

    public static void main(String[] args) throws ParseException, IOException {
        String input_date = "2104-04-10";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-mm-dd");
        Date dt1 = format1.parse(input_date);
        c.setTime(dt1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("Day " + dayOfWeek);
    }
}
