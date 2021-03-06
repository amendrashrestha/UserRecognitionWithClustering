/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author amendrashrestha
 */
public class NewTest {

    public static void main(String args[]) throws IOException {
        
        List<int[]> users = new ArrayList();
        int[] user1 = {5,4,3,2,1,6};
        int[] user2 = {5,4,3,2,1,6};
        
        users.add(user1);
        users.add(user1);
        
        for(int[] user : users){
            System.out.println("First Index: " + user[0]); 
            int firstUser = user[0];
            //if()
            System.out.println("users: " + Arrays.toString(user));
        }
        
        
        
        double temp;
        int a = 22;
        int b = 27;
        temp = (double) a / b;
        int test = (int) (temp + 0.5);
        System.out.println("Temp: " + temp);
        System.out.println("Temp: " + test);
        
        int[] vector = new int[]{9,9,14,8,21,20};
        int sum = returnTotalSum(vector);
        System.out.println("Sum: " + sum);
        vector = returnNormalizedVector(vector, sum);
        for(int i : vector){
            System.out.println(i);
        }
        String fileName = System.getProperty("user.home") + "/Desktop/TimeFeatureVectorwithSplitTest1.arff";
        //createFile(fileName);
        
    }

    private static int[] returnNormalizedVector(int[] timeVector, int sum) {

        for (int index = 0; index < timeVector.length; index++) {
            double time = timeVector[index];
            double perc = (double) (time / sum);
            int temp = (int) ((perc * 100) + 0.5);
            timeVector[index] = temp;
        }
        return timeVector;
    }
    
    private static int returnTotalSum(int[] timeVector) {
        int sum = 0;

        for (int index = 0; index < timeVector.length; index++) {
            sum = sum + timeVector[index];
        }
        return sum;
    }
    
    private static void createFile(String fileName) throws IOException {
        String content = "@relation usertimeprofile\n"
                + "\n"
                + "@attribute  UserID  numeric\n"
                + "@attribute  Hour1  numeric\n"
                + "@attribute   Hour2  numeric\n"
                + "@attribute   Hour3  numeric\n"
                + "@attribute   Hour4  numeric\n"
                + "@attribute   Hour5  numeric\n"
                + "@attribute   Hour6  numeric\n"
                + "@attribute   Hour7  numeric\n"
                + "@attribute   Hour8  numeric\n"
                + "@attribute   Hour9  numeric\n"
                + "@attribute   Hour10  numeric\n"
                + "@attribute   Hour11  numeric\n"
                + "@attribute   Hour12  numeric\n"
                + "@attribute   Hour13  numeric\n"
                + "@attribute   Hour14  numeric\n"
                + "@attribute   Hour15  numeric\n"
                + "@attribute   Hour16  numeric\n"
                + "@attribute   Hour17  numeric\n"
                + "@attribute  Hour18  numeric\n"
                + "@attribute   Hour19  numeric\n"
                + "@attribute   Hour20  numeric\n"
                + "@attribute   Hour21  numeric\n"
                + "@attribute   Hour22  numeric\n"
                + "@attribute   Hour23  numeric\n"
                + "@attribute   Hour24  numeric\n"
                + "@attribute  MidNight  numeric\n"
                + "@attribute   EarlyMorning  numeric\n"
                + "@attribute   Morning  numeric\n"
                + "@attribute   MidDay  numeric\n"
                + "@attribute   Evening  numeric\n"
                + "@attribute   Night  numeric\n"
                + "@attribute  Jan  numeric\n"
                + "@attribute   Feb  numeric\n"
                + "@attribute   Mar  numeric\n"
                + "@attribute   Apr  numeric\n"
                + "@attribute   May  numeric\n"
                + "@attribute   June  numeric\n"
                + "@attribute   July  numeric\n"
                + "@attribute   Aug  numeric\n"
                + "@attribute   Sep  numeric\n"
                + "@attribute   Oct  numeric\n"
                + "@attribute   Nov  numeric\n"
                + "@attribute   Dec  numeric\n"
                + "@attribute  Sun  numeric\n"
                + "@attribute   Mon  numeric\n"
                + "@attribute   Tue  numeric\n"
                + "@attribute   Wed  numeric\n"
                + "@attribute   Thu  numeric\n"
                + "@attribute   Fri  numeric\n"
                + "@attribute   Sat  numeric\n"
                + "@attribute  WeekDay  numeric\n"
                + "@attribute   WeekEnd  numeric\n"
                + "\n"
                + "@data";

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        }
    }

}
