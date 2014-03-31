/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.parser.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;

/**
 *
 * @author ITE
 */
public class CopyFileContent {

    public void copyContents() throws FileNotFoundException, IOException {

        IOReadWrite io = new IOReadWrite();
        List newUserDirectory = io.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        Collections.sort(newUserDirectory, new ComparatorImpl());
        List combinedUserDirectory = io.getAllDirectories(IOProperties.All_YEAR_FILES_BASE_PATH);
        Collections.sort(combinedUserDirectory, new ComparatorImpl());
        
        for (int i = 0; i < newUserDirectory.size(); i++) {
            List newUsers = io.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + "\\" + newUserDirectory.get(i).toString());
            List combinedUsers = io.getAllFilesInADirectory(IOProperties.All_YEAR_FILES_BASE_PATH + "\\" + combinedUserDirectory.get(i).toString());
            System.out.println("NU:" + newUsers);
            System.out.println("OU:" + combinedUsers);

            for (int j = 0; j < newUsers.size(); j++) {
                String newUser = newUsers.get(j).toString();
                System.out.println("J User: " + newUser + "**");
                for (int k = 0; k < combinedUsers.size(); k++) {
                    // add users content if it exists                    
                    String oldUser = combinedUsers.get(k).toString();
                    System.out.println("K User: " + oldUser);

                    if (newUser.equals(oldUser)) {
                        io.mergeUserPostTime(newUser, oldUser);
                        System.out.println("Matched Users :" + oldUser);
                        break;
                    }
                }
                /**
                 * add the new users if it doesn't exist
                 */
                if (!combinedUsers.contains(newUser)) {
                    System.out.println("New User: " + newUser);
                    io.copyFile(newUser);
                }
                System.out.println("-----------------");
            }
            System.out.println("Total Users:" + combinedUsers);
            System.out.println("Old Users:" + newUsers);
            System.out.println("*******************");
        }
    }

    /**
     * Sort the list of strings
     *
     * @param info
     */
    public static class ComparatorImpl implements Comparator<String> {

        public ComparatorImpl() {
        }

        @Override
        public int compare(String t, String t1) {
            int i1 = Integer.parseInt(t.replaceAll("\\D", ""));
            int i2 = Integer.parseInt(t1.replaceAll("\\D", ""));
            return Integer.compare(i1, i2);
        }
    }
}
