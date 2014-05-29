/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uni.EISIC14.analysis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 *
 * @author amendrashrestha
 */
public class AnalyzeTimeFeature {
    
    public void executeAnalysis() throws FileNotFoundException, IOException, SQLException, ParseException {

        IOReadWrite ioReadWrite = new IOReadWrite();
        List<User> userList = ioReadWrite.getAllUsersAsObject();

        //For passing limited number of sorted users  
        List<User> tempUsers = ioReadWrite.returnLimitedSortedUser(userList, 1000);
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
                AnalyzePostTimeWithTimeFeatureVector compare = new AnalyzePostTimeWithTimeFeatureVector();
                compare.executePostCompare(splitUsersList);
        }
    }
    
}
