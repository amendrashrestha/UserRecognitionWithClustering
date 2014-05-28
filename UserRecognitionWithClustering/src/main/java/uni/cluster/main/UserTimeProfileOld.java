package uni.cluster.main;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 *
 * @author amendrashrestha
 */
public class UserTimeProfileOld {

    public static void main(String[] args) throws IOException, ParseException {
        IOReadWrite ioReadWrite = new IOReadWrite();

        List<User> UserList = ioReadWrite.getAllUsersAsObject();
        ICsvBeanWriter beanWriter = null;

        for (User user1 : UserList) {
            user1.setCategorizedTimeToUser(user1); //Number of messages in 6 hour interval of a day
            user1.setCategorizedDayToUser(user1); //Number of messages in 7 days of week
            user1.setCategorizedMonthToUser(user1); //Number of messages in 12 months of year
            user1.setCategorizedHourOfDayToUser(user1); //Number of messages in 24 hours of day
            user1.setCategorizedTypeOfWeekToUser(user1); //Number of messages in weekdays and weekends
        }

        try {
            String target = System.getProperty("user.home") + "/Desktop/TimeFeatureVector.csv";
            beanWriter = new CsvBeanWriter(new FileWriter(target),
                    CsvPreference.STANDARD_PREFERENCE);
            String[] header = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
                "18", "19", "20", "21", "22", "23", "24",
                "0004", "0408", "0812", "1216", "1620", "2000",
                "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec",
                "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
                "WeekDay", "WeekEnd"};
            final CellProcessor[] processors = getProcessors();
            beanWriter.writeHeader(header);
            
            for(User user : UserList){
                beanWriter.write(user, header, processors);
            }
        } finally {
            if (beanWriter != null) {
                beanWriter.close();
            }
        }

    }

    /*for(User user1 : UserList){
     try (
     CSVWriter writer = new CSVWriter(new FileWriter(System.getProperty("user.home") + "/Desktop/TimeFeatureVector.csv", true), ',')) {
     String[] featureVectorList;
            
     user1.setCategorizedTimeToUser(user1); //Number of messages in 6 hour interval of a day
     user1.setCategorizedDayToUser(user1); //Number of messages in 7 days of week
     user1.setCategorizedMonthToUser(user1); //Number of messages in 12 months of year
     user1.setCategorizedHourOfDayToUser(user1); //Number of messages in 24 hours of day
     user1.setCategorizedTypeOfWeekToUser(user1); //Number of messages in weekdays and weekends
            
            
     int[] hourOfDay = user1.getClassifiedHourOfDayVector();
     int[] timeOfInterval = user1.getClassifiedTimeVector();
     int[] monthOfYear = user1.getClassifiedMonthVector();
     int[] dayOfWeek = user1.getClassifiedDayVector();
     int[] typeOfWeek = user1.getClassifiedTypeOfWeekVector();
            
     int[] combined = ArrayUtils.addAll(hourOfDay, timeOfInterval);
     int[] combined1 = ArrayUtils.addAll(combined, monthOfYear);
     int[] combined2 = ArrayUtils.addAll(combined1, dayOfWeek);
     int[] combined3 = ArrayUtils.addAll(combined2, typeOfWeek);
            
     featureVectorList = Arrays.toString(combined3).split("[\\[\\]]")[1].split(", ");
     System.out.println("Features: " + Arrays.toString(featureVectorList));
            
     //            System.out.println("Hour Of Day: " + Arrays.toString(hourOfDay));
     //            System.out.println("Interal Of Day: " + Arrays.toString(timeOfInterval));
     //            System.out.println("Month Of Year: " + Arrays.toString(monthOfYear));
     //            System.out.println("Days Of Week: " + Arrays.toString(dayOfWeek));
     //            System.out.println("Type Of Week: " + Arrays.toString(typeOfWeek));
            
     writer.writeNext(featureVectorList);
            
            
     }
     System.out.println("*************");
            
     /*System.out.println("User_" + user1.getId());
     int[] dayOfMonth = user1.getClassifiedDayOfMonthVector();
     System.out.println("Day Of Month");
     for(int i : dayOfMonth){
     System.out.println(i);
     }
     System.out.println("Day Of Week");
     int[] day = user1.getClassifiedDayVector();
     for(int i : day){
     System.out.println(i);
     }
     System.out.println("Month Of Year");
     int[] month = user1.getClassifiedMonthVector();
     for(int i : month){
     System.out.println(i);
     }
     System.out.println("**************");*/
    private static CellProcessor[] getProcessors() {

        final CellProcessor[] processors = new CellProcessor[]{
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
            new NotNull(), 
        };
        return processors;
    }
}
