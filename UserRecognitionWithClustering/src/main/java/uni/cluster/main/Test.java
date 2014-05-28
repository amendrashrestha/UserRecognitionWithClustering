package uni.cluster.main;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author ITE
 */
public class Test {

    public static void main(String[] args) throws ParseException, IOException {

        List<String> array = new ArrayList();
        List<String> array1 = new ArrayList();

        array.add("23");
        array.add("24");
        array.add("25");

        array1.add("56");
        array1.add("343");
        array1.add("89");


        List anotherArray = new ArrayList();
//        anotherArray.addAll(array);
//        anotherArray.addAll(array1);

        // feed in your array (or convert your data to an array)
        try ( /*try (BufferedWriter br = new BufferedWriter(new FileWriter("/Users/amendrashrestha/Desktop/myfile.csv"))) {
                 StringBuilder sb = new StringBuilder();
                 for (List<String> testArray : arrayList) {
                 for (String element : testArray) {
                 sb.append(element);
                 sb.append(",");
                 }
                 sb.append('\n');
                 }
                 br.write(sb.toString());
                 }*/CSVWriter writer = new CSVWriter(new FileWriter("/Users/amendrashrestha/Desktop/yourfile.csv"), ',')) {
            // feed in your array (or convert your data to an array)
            String[] header = new String[] {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
            int[] test = new int[] {1,2,3,5,6,7};
            int[] test1 = new int[] {8,9,10,11};
            
            int [] combined = ArrayUtils.addAll(test, test1);
            
            String[] testList = Arrays.toString(combined).split("[\\[\\]]")[1].split(", ");
            //String[] tempEntries = convertListToArray(testList);

            writer.writeNext(testList);

        }
    }

    private static String[] convertListToArray(List<String> featureVector) {
        String[] arrayOfArraysOfString = new String[featureVector.size()];
        String[] anotherArray = new String[0];

        arrayOfArraysOfString = featureVector.toArray(anotherArray);

        return arrayOfArraysOfString;
    }

    private static int getTypeOfWeek(int day) throws ParseException {

        if (day >= 2 && day <= 6) {
            return 0;
        } else {
            return 1;
        }
    }
}
