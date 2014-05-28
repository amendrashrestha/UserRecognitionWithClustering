package uni.EISIC14.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
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
        List<User> splitUsersList;
        int divisionTimes;

        for (int k = 0; k < tempUsers.size(); k++) {
        splitUsersList = new ArrayList<>();
            for (int j = 0; j < tempUsers.size(); j++) {               
                if (k == j) {
                    divisionTimes = 2;
                } else {
                    divisionTimes = 1;
                }
                User user = tempUsers.get(j);
                splitUsersList = ioReadWrite.returnDividedUserForTimeFeat(divisionTimes, splitUsersList, user);
            }
                AnalyzePostTimeWithHourofDay compare = new AnalyzePostTimeWithHourofDay();
                compare.executePostCompare(splitUsersList);
        }
    }
}
