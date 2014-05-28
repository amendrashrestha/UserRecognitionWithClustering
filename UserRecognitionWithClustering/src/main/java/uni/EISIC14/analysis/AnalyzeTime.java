package uni.EISIC14.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.analysis.*;
import uni.cluster.parser.model.Alias;
import uni.cluster.parser.model.User;

/**
 * This main class analyze the time and Stylometric analysis of posts
 *
 * @author ITE
 */
public class AnalyzeTime {

    public void executeAnalysis() throws FileNotFoundException, IOException, SQLException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        List<User> userList = ioReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(userList, 5);

        List<Alias> aliasList;
        int divisionTimes;

        for (int k = 0; k < tempUsers.size(); k++) {
            aliasList = new ArrayList<>();
            for (int j = 0; j < tempUsers.size(); j++) {
                String id = Integer.toString(tempUsers.get(j).getId());
                String basePath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
                String directoryName = ioReadWrite.getFolderName(id);
                String ext = IOProperties.USER_FILE_EXTENSION;

                if (k == j) {
                    divisionTimes = 2;
                } else {
                    divisionTimes = 1;
                }
                aliasList = ioReadWrite.convertTxtFileToAliasObjAndDivideForTimeFeat(divisionTimes, basePath, directoryName, id, ext, aliasList);
            }
            AnalyzePostTimeWithHourofDay compare = new AnalyzePostTimeWithHourofDay();
            compare.executePostCompare(aliasList);
        }
    }
}
