
package uni.cluster.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.Alias;

/**
 * This main class analyze the time and Stylometric analysis of posts
 * @author ITE
 */
public class UserAnalysis {

    public void executeAnalysis() throws FileNotFoundException, IOException, SQLException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        String clusterFolderPath = IOProperties.All_ACTIVITY_BASE_PATH + IOProperties.CLUSTER_FOLDER_NAME;
        List<String> filesName = new ArrayList<>();
        
        System.out.println("Cluster Data: " + clusterFolderPath);
        filesName = ioReadWrite.getAllFilesInADirectory(clusterFolderPath);
        List<Alias> aliasList;
        List<String> clusterUserID = new ArrayList<>();
        int divisionTimes = 0;
        Set SACUsers = new HashSet();

        for (String filesName1 : filesName) {
            String fileName = clusterFolderPath + "/" + filesName1 + IOProperties.USER_FILE_EXTENSION;
            clusterUserID = ioReadWrite.readClusterData(fileName);
            for (int k = 0; k < clusterUserID.size(); k++) {
                aliasList = new ArrayList<>();
                for (int j = 0; j < clusterUserID.size(); j++) {
                    String id = clusterUserID.get(j);
                    SACUsers.add(id);
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
        System.out.println("Second Activity User Size : " + SACUsers.size());
    }
}
