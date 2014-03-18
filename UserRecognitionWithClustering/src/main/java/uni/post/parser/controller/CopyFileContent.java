/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.parser.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
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
        List combinedUserDirectory = io.getAllDirectories(IOProperties.All_YEAR_FILES_BASE_PATH);
        for (int i = 0; i < newUserDirectory.size(); i++) {
            List newUsers = io.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + "\\" + combinedUserDirectory.get(i).toString());
            List combinedUsers = io.getAllFilesInADirectory(IOProperties.All_YEAR_FILES_BASE_PATH + "\\" + combinedUserDirectory.get(i).toString());
                for(int j = 0; j < newUsers.size(); j++){
                    for(int k = 0; k < combinedUsers.size(); k++){
                        if(combinedUsers.get(j).toString().equals(newUsers.get(k).toString())){
                            System.out.println(newUsers.get(k).toString());
                            break;
                        }
                    }
                }
        }


        /**
         * Files to add from new location
         */
        String[] newUserID = io.getAllFileNameWithPath(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        Arrays.sort(newUserID);

//        for(int i = 0; i < newUserID.length; i++){
//            if(newUserID[i].contains(userID)){
//                
//            }
//        }
        
    }
}
