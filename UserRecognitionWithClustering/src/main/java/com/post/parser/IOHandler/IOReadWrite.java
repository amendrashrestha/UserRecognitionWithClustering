package com.post.parser.IOHandler;

import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.SecondActivityCluster;
import com.post.parser.clustering.SleepingCluster;
import com.post.parser.clustering.UserDivision;
import com.post.parser.controller.FileDirectoryHandler;
import com.post.parser.model.Posts;
import com.post.parser.model.User;
import com.post.stylometric.StylometricMatching;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Batman
 */
public class IOReadWrite {

    public IOReadWrite() {
    }

    public void writeToFile(String fileName, String content) throws IOException {
        String getUserFolderName = getFolderName(fileName);
        checkAndCreateDirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH, getUserFolderName);
        String fileLocation = IOProperties.INDIVIDUAL_USER_FILE_PATH + getUserFolderName;
        String tempfileName = fileName + IOProperties.USER_FILE_EXTENSION;
        String completeFileNameNPath = fileLocation + "/" + tempfileName;
        File file = new File(completeFileNameNPath);
        file.createNewFile();

        PrintWriter out = new PrintWriter(new FileWriter(completeFileNameNPath, true));
        out.append(content + IOProperties.DATA_SEPERATOR);
        out.close();
    }

    /*
     * It returns the name of the xml file in a folder
     */
    public String[] getAllXmlFileName() {
        FileDirectoryHandler handle = new FileDirectoryHandler();
        handle.rootlist(IOProperties.XML_DATA_FILE_PATH);
        String[] filesList = new String[FileDirectoryHandler.getList().size()];
        FileDirectoryHandler.list.toArray(filesList);
        return filesList;
    }

    public String getFolderName(String userId) {
        String folderName = "";
        int userID = Integer.valueOf(userId);
        if (userID > 0 && userID <= 50000) {
            folderName = "50K";
        } else if (userID > 50000 && userID <= 100000) {
            folderName = "100K";
        } else if (userID > 100000 && userID <= 150000) {
            folderName = "150K";
        } else if (userID > 150000 && userID <= 200000) {
            folderName = "200K";
        } else if (userID > 200000 && userID <= 250000) {
            folderName = "250K";
        } else if (userID > 300000 && userID <= 350000) {
            folderName = "300K";
        } else if (userID > 350000 && userID <= 400000) {
            folderName = "350K";
        } else if (userID > 400000 && userID <= 450000) {
            folderName = "400K";
        } else if (userID > 450000 && userID <= 500000) {
            folderName = "450K";
        }
        return folderName;
    }

    public void checkAndCreateDirectory(String path, String folderName) {
        File directory = new File(path + "\\" + folderName); //for mac use / and for windows use "\\"
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public File checkAndCreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    public List getAllDirectories(String basePath) {
        File file = new File(basePath);
        String[] directories = file.list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        //System.out.print(Arrays.toString(directories));
        return Arrays.asList(directories);
    }

    public String readTxtFileAsString(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(basePath + directoryName + "/" + fileName + extension);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            if (file.exists()) {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
        String a = stringBuilder.substring(0, (stringBuilder.length() - (IOProperties.DATA_SEPERATOR).length())).toString();
        return a;
    }

    public User convertTxtFileToUserObj(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;
        User user = new User();
        List postList = new ArrayList();
        user.setId(Integer.valueOf(fileName));
        if (userPostAsString.contains(IOProperties.DATA_SEPERATOR)) {
            temp = userPostAsString.split(IOProperties.DATA_SEPERATOR);
        } else {
            temp = new String[1];
            temp[0] = userPostAsString;

        }
        for (int i = 0; i < temp.length; i++) {
            if (temp[i].toString().matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")
                    || temp[i].toString().length() == 8) {
                temp[i] = temp[i].toString() + "  ";
            }
            Posts posts = new Posts();
            String date = temp[i].substring(0, 8);
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                posts.setTime(date);
                posts.setContent(temp[i].substring(9, temp[i].length()));
                postList.add(posts);
                //System.out.println(date);
            } else {
                continue;

            }
        }
        user.setUserPost(postList);

        return user;
    }

    public List getAllFilesInADirectory(String directoryName) {
        List returnList = new ArrayList();
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            String a = listOfFiles[i].getName();
            returnList.add(a.substring(0, a.length() - 4));
        }
        return returnList;
    }

    /**
     * Return users as an object and returns only those users who has posted
     * more than 60 messages in discussion board
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<User> getAllUsersAsObject() throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFiles = new ArrayList();
        List allFilesSize = new ArrayList();
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList.get(i));
            for (int j = 0; j < allFilesSize.size(); j++) {
                User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, directoryList.get(i).toString(), allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);
                if (user.getUserPost().size() > 60) {
                    allFiles.add(user);
                }
            }
        }
        return allFiles;
    }

    public List<FirstActivityCluster> readFirstActivityClusterData(String fileName) throws FileNotFoundException, IOException {
        List<FirstActivityCluster> firstActivityCluster = new ArrayList();
        FirstActivityCluster fac;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        if (file.exists()) {
            while ((line = reader.readLine()) != null) {
                fac = new FirstActivityCluster();
                String[] splittedContent = line.split(" ");
                fac.setUserID(Integer.valueOf(splittedContent[0].toString()));
                String splittedTimeVector = splittedContent[1].toString();
                int timeVector[] = fac.getPostTimeVector();

                for (int i = 0; i < 6; i++) {
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")) {
                        timeVector[i] = 1;
                    }
                }
                fac.setPostTimeVector(timeVector);
                firstActivityCluster.add(fac);
            }
        }
        return firstActivityCluster;
    }

    public void writeFirstActivityClusterData(List<FirstActivityCluster> firstActivityCluster) throws IOException {
        checkAndCreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.FIRST_ACTIVITY_FOLDER_NAME);
        String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.FIRST_ACTIVITY_FOLDER_NAME
                + "\\" + IOProperties.FIRST_ACTIVITY_FILE_NAME + IOProperties.FIRST_ACTIVITY_FILE_EXTENSION;

        File file = checkAndCreateFile(completeFileNameNPath);
        int[] timeVector;
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        for (FirstActivityCluster fac : firstActivityCluster) {
            timeVector = fac.getPostTimeVector();
            String timeVectorAsString = "";
            for (int i = 0; i < timeVector.length; i++) {
                timeVectorAsString = timeVectorAsString + String.valueOf(timeVector[i]);
            }
            String toWriteContent = String.valueOf(fac.getUserID()) + " " + timeVectorAsString;
            output.write(toWriteContent);
            output.newLine();
        }
        output.close();
    }

    public void writeSleepingClusterData(List<SleepingCluster> sleepingCluster, String cluster) throws IOException {
        String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SLEEPING_FOLDER_NAME
                + "\\" + IOProperties.SLEEPING_FILE_NAME + cluster + IOProperties.SLEEPING_FILE_EXTENSION;
        File file = checkAndCreateFile(completeFileNameNPath);
        int[] timeVector;
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        for (SleepingCluster scObj : sleepingCluster) {
            timeVector = scObj.getPostTimeVector();
            String timeVectorAsString = "";
            for (int i = 0; i < timeVector.length; i++) {
                timeVectorAsString = timeVectorAsString + String.valueOf(timeVector[i]);
            }
            String toWriteContent = String.valueOf(scObj.getUserID()) + " " + timeVectorAsString;
            output.write(toWriteContent);
            output.newLine();
        }
        output.close();
    }

    public List<SleepingCluster> readSleepingClusterData(String fileName) throws FileNotFoundException, IOException {
        List<SleepingCluster> sleepingCluster = new ArrayList();
        SleepingCluster scObj;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        if (file.exists()) {
            while ((line = reader.readLine()) != null) {
                scObj = new SleepingCluster();
                String[] splittedContent = line.split(" ");
                scObj.setUserID(Integer.valueOf(splittedContent[0].toString()));
                String splittedTimeVector = splittedContent[1].toString();
                int timeVector[] = scObj.getPostTimeVector();

                for (int i = 0; i < 6; i++) {
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")) {
                        timeVector[i] = 1;
                    }
                }
                scObj.setPostTimeVector(timeVector);
                sleepingCluster.add(scObj);
            }
        }
        return sleepingCluster;
    }

    public void writeSecondActivityClusterData(List<List<SecondActivityCluster>> allsecondActivityCluster, int folderName) throws IOException {
        checkAndCreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.SECOND_ACTIVITY_FOLDER_NAME);
        checkAndCreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SECOND_ACTIVITY_FOLDER_NAME, String.valueOf(folderName));
        for (int i = 0; i < allsecondActivityCluster.size(); i++) {
            List<SecondActivityCluster> secondActivityCluster = allsecondActivityCluster.get(i);
            String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SECOND_ACTIVITY_FOLDER_NAME + "\\" + String.valueOf(folderName)
                    + "\\" + IOProperties.SECOND_ACTIVITY_FILE_NAME + String.valueOf(i + 1) + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
            File file = checkAndCreateFile(completeFileNameNPath);
            int[] timeVector;
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            for (SecondActivityCluster sacObj : secondActivityCluster) {
                timeVector = sacObj.getPostTimeVector();
                String timeVectorAsString = "";
                for (int j = 0; j < timeVector.length; j++) {
                    timeVectorAsString = timeVectorAsString + String.valueOf(timeVector[j]);
                }
                String toWriteContent = String.valueOf(sacObj.getUserID()) + " " + timeVectorAsString;
                output.write(toWriteContent);
                output.newLine();
            }
            output.close();
        }
    }

    /**
     * This method returns the total number of unique users in second activity
     * cluster
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public int returnSecondActivityClusterUserSize(String filePath) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List sacFolders = new ArrayList();
        sacFolders = ioRW.getAllDirectories(filePath);
        List allsacFiles = new ArrayList();
        Collection allUsers = new HashSet();

        for (int i = 0; i < sacFolders.size(); i++) {
            String folderName = sacFolders.get(i).toString();
            allsacFiles = ioRW.getAllFilesInADirectory(filePath + "\\" + folderName);
            for (int j = 0; j < allsacFiles.size(); j++) {
                String fileName = allsacFiles.get(j).toString();
                File file = new File(filePath + "\\" + folderName + "\\" + fileName + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION);
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line = null;
                if (file.exists()) {
                    while ((line = reader.readLine()) != null) {
                        String[] splittedContent = line.split(" ");
                        Integer user = Integer.valueOf(splittedContent[0].toString());
                        allUsers.add(user);
                    }
                }
            }
        }
        for (Object users : allUsers) {
            System.out.println("Final UserID: " + users);
        }
        int userSize = allUsers.size();
        return userSize;
    }

    /**
     * Sirji ek choti yo part heri deu na hai...
     *
     * @param filePath
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void readSecondActivityClusterData(String filePath) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List sacFolders = new ArrayList();
        sacFolders = ioRW.getAllDirectories(filePath);
        List allsacFiles = new ArrayList();
        Set firstCluster = new HashSet();
        Set secondCluster = new HashSet();
        Set thirdCluster = new HashSet();
        Set fourthCluster = new HashSet();
        Set fifthCluster = new HashSet();
        Set sixthCluster = new HashSet();
        StylometricMatching styloMetric = new StylometricMatching();
        List<Float> styloValue = new ArrayList<Float>();
         
       

        for (int i = 0; i < sacFolders.size(); i++) {
            String folderName = sacFolders.get(i).toString();
            allsacFiles = ioRW.getAllFilesInADirectory(filePath + "\\" + folderName);

            for (int j = 0; j < allsacFiles.size(); j++) {
                String fileName = allsacFiles.get(j).toString();
                File file = new File(filePath + "\\" + folderName + "\\" + fileName + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION);
                BufferedReader reader = new BufferedReader(new FileReader(file));

                String line = null;

                if (file.exists()) {
                    while ((line = reader.readLine()) != null) {
                        String tempfolderName = "";
                        String[] splittedContent = line.split(" ");
                        int userID = Integer.valueOf(splittedContent[0].toString());
                        String ClusterInfo = splittedContent[1];
                        List<Integer> clusterNumber = returnDigits(ClusterInfo);
                        
                        //this should be done in Stylometric sectio
                        // object pass na garne...we will be just pasing ID..we can make user object
                        // inside stylometric class
                        // Why would you pass only the id when you have all the content??.
                        //because later on if someone just passed ID then we can work 
                        // otherwise always the object will be the requirement for that class
                        // Well that makes perfect sense, but your whole project now is fucked up and
                        // you want to think that??
                        
                        // auta yesto method banaunu paryo...jasle primitive datatype liyos na ki object and
                        // is capable of returning OK OK i got it
                        // ok i am doing your way it looks easy that way
                        
                           
                            tempfolderName = getFolderName(Integer.valueOf(userID).toString());
                            User objUser = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, tempfolderName,
                                   Integer.valueOf(userID).toString(), IOProperties.USER_FILE_EXTENSION);

                        if (clusterNumber.get(0) == 1) {
                            firstCluster.add(userID); // yo list pass garnu paryo.
                        }
                        if (clusterNumber.get(1) == 1) {
                            secondCluster.add(userID);
                        }
                        if (clusterNumber.get(2) == 1) {
                            thirdCluster.add(userID);
                        }
                        if (clusterNumber.get(3) == 1) {
                            fourthCluster.add(userID);
                        }
                        if (clusterNumber.get(4) == 1) {
                            fifthCluster.add(userID);
                        }
                        if (clusterNumber.get(5) == 1) {
                            sixthCluster.add(userID);
                        }
                    }
                }
                
                // 
            }
        }
        // Ok i have to go now i will back after 1 hr.
        // people coming bye ok
        
        for (int i=0; i<6; i++){
            if (i == 0) styloMetric.getStyloMetric(firstCluster, i+1);
            if (i == 1) styloMetric.getStyloMetric(secondCluster, i+1);
            if (i == 2) styloMetric.getStyloMetric(thirdCluster, i+1);
            if (i == 3) styloMetric.getStyloMetric(fourthCluster, i+1);
            if (i == 4) styloMetric.getStyloMetric(fifthCluster, i+1);
            if (i == 5) styloMetric.getStyloMetric(sixthCluster, i+1);
            
        }
         
                           
        
        //calculate Stylometric of each individual users by passing the user ID
        System.out.println("First Cluster");
        for (Object user : firstCluster) { 
            // this is the list of users in first cluster we will be passing this list of user for stylometrci
            // Now you want id to be passed??
            //tyo ta loop lagayera huncha but existing stylometric ley userID lidaina
            // ok ok i understand 
            System.out.println(user);
        }
        System.out.println("Second Cluster");
        for(Object user : secondCluster){
            System.out.println(user);
        }
        System.out.println("Third Cluster");
        for(Object user : thirdCluster){
            System.out.println(user);
        }
        System.out.println("Fourth Cluster");
        for(Object user : fourthCluster){
            System.out.println(user);
        }
        System.out.println("Fifth Cluster");
        for(Object user : fifthCluster){
            System.out.println(user);
        }
        System.out.println("Sixth Cluster");
        for(Object user : sixthCluster){
            System.out.println(user);
        }
    }

    public List<Integer> returnDigits(String cluster) {
        LinkedList<Integer> digits = new LinkedList<Integer>();
        char[] cArray = cluster.toCharArray();
        for (int i = 0; i < cArray.length; i++) {
            String tempCharacter = String.valueOf(cArray[i]);
            int singleNumber = Integer.valueOf(tempCharacter);
            digits.add(i, singleNumber);
        }
        return digits;
    }
}