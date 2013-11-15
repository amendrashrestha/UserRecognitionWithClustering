/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.parser.clustering;

import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.model.User;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Batman
 */
public class Cluster {
    
    public Cluster(){
    }
    
    // There are still something to be done in this function
    public List<User> getAllClusterizedUser() throws FileNotFoundException, IOException{
        List<User> userList = new ArrayList<User>();
        IOReadWrite ioReadWrite = new IOReadWrite();
        List allUserList = ioReadWrite.getAllUsersAsObject();
        List<FirstActivityCluster> facList = getFirstActivityCluster(allUserList);
        List<SleepingCluster> scList = getSleepingCluster(facList, allUserList);
        List<SecondActivityCluster> sacList = getSecondActivityCluster(facList, scList, allUserList);
        return userList;
    }
    
    /**
     * This function gives the users in the first activity cluster
     * @param List<USer>
     * @return List<FirstActivityCluster>
     */
    public List<FirstActivityCluster> getFirstActivityCluster(List<User> userList){
        List<FirstActivityCluster> facList = new ArrayList<FirstActivityCluster>();
        FirstActivityCluster fac = new FirstActivityCluster();
        User userObj = new User();
        UserDivision userDivision  = new UserDivision();
        List dividedUserList = userDivision.divideAllUser(userList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserFirstActivityCluster(dividedUserList);
        facList = fac.getUserInSameClusterForFirstActivityClusterAsFACObject(dividedUserList);
        return facList;
    }
    
    /**
     * This function returns the users in the sleeping cluster
     * @param List<FirstActivityCluster>
     * @param List<User>
     * @return 
     */
    public List<SleepingCluster> getSleepingCluster(List<FirstActivityCluster> facList, List<User> userList){
        List<SleepingCluster> scList = new ArrayList<SleepingCluster>();
        User userObj = new User();
        SleepingCluster scObj = new SleepingCluster();
        UserDivision userDivisionObj  = new UserDivision();
        List<User> unDividedUserList = userObj.getInitialUserForSleepingCluster(facList, userList);
        List dividedUserList = userDivisionObj.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSleepingCluster(dividedUserList);
        scList = scObj.getUserInSameClusterForSleepingCluster(dividedUserList);
        return scList;
    }
    
    /**
     * This function returns the users in the second activity cluster
     * @param List<FirstActivityCluster> 
     * @param List<SleepingCluster>
     * @param List<User>
     * @return 
     */
    public List<SecondActivityCluster> getSecondActivityCluster(List<FirstActivityCluster> facList, List<SleepingCluster> scList, List<User> userList){
        List<SecondActivityCluster> sacList = new ArrayList<SecondActivityCluster>();
        User userObj = new User();
        SecondActivityCluster sacObj = new SecondActivityCluster();
        UserDivision userDivisionObj = new UserDivision();
        List<User> unDividedUserList = userObj.getInitialUserForSecondActivityCluster(facList, scList, userList);
        List dividedUserList = userDivisionObj.divideAllUser(unDividedUserList);
        dividedUserList = userObj.setCategorizedTimeToUser(dividedUserList);
        dividedUserList = userObj.generateUserSecondActivityCluster(dividedUserList);
        sacList = sacObj.getUserInSameClusterForSecondActivityCluster(dividedUserList);
        return sacList;
    }
}
