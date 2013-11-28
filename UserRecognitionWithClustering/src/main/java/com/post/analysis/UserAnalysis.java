/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.analysis;

import com.post.parser.IOHandler.IOProperties;
import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.model.Alias;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */
public class UserAnalysis {

    public void executeAnalysis() throws FileNotFoundException, IOException, SQLException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        String clusterFolderPath = IOProperties.XML_DATA_FILE_PATH + IOProperties.CLUSTER_FOLDER_NAME;
        List<String> filesName = new ArrayList<String>();

        filesName = ioReadWrite.getAllFilesInADirectory(clusterFolderPath);
        List<Alias> aliasList = new ArrayList<Alias>();
        List<String> clusterUserID = new ArrayList<String>();
        int divisionTimes = 0;

        for (int i = 0; i < filesName.size(); i++) {
            String fileName = clusterFolderPath + "\\" + filesName.get(i) + IOProperties.USER_FILE_EXTENSION;
            clusterUserID = ioReadWrite.readClusterData(fileName);
            
            for (int k = 0; k < clusterUserID.size(); k++) {
                aliasList = new ArrayList<Alias>();

                for (int j = 0; j < clusterUserID.size(); j++) {
                    String id = clusterUserID.get(j);
                    String basePath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
                    String directoryName = ioReadWrite.getFolderName(id);
                    String ext = IOProperties.USER_FILE_EXTENSION;

                    if (k == j) {
                        divisionTimes = 2;
                    } else {
                        divisionTimes = 1;
                    }
                    aliasList = ioReadWrite.convertTxtFileToAliasObjAndDivide(divisionTimes, basePath, directoryName, id, ext, aliasList);
                }
                System.out.println("Time Stylo Analysis");
                TimeAndStylometricMatching sMatching = new TimeAndStylometricMatching();
                sMatching.executeStylo(aliasList);
            }
        }
    }
}
