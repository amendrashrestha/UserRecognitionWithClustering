package com.post.parser.IOHandler;

import com.post.parser.clustering.FirstActivityCluster;
import com.post.parser.clustering.SecondActivityCluster;
import com.post.parser.clustering.SleepingCluster;
import com.post.parser.controller.FileDirectoryHandler;
import com.post.parser.model.Posts;
import com.post.parser.model.User;
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
import java.util.List;

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
        if (!file.exists()) {
            file.createNewFile();
        }
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
    
    public File checkAndCreateFile(String fileName) throws IOException{
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

    public List<User> getAllUsersAsObject() throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFiles = new ArrayList();
        List allFilesSize = new ArrayList();
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList.get(i));
            for (int j = 0; j < allFilesSize.size(); j++) {
                User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, directoryList.get(i).toString(), allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);
                if (user.getUserPost().size() > 5) {
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
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")){
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
                + "\\" + IOProperties.SLEEPING_FILE_NAME+ cluster + IOProperties.SLEEPING_FILE_EXTENSION;
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
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")){
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
        checkAndCreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH+"\\"+IOProperties.SECOND_ACTIVITY_FOLDER_NAME, String.valueOf(folderName) );
        for(int i=0; i<allsecondActivityCluster.size(); i++){
            List<SecondActivityCluster> secondActivityCluster = allsecondActivityCluster.get(i);
            String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SECOND_ACTIVITY_FOLDER_NAME + "\\" + String.valueOf(folderName)
                + "\\" + IOProperties.SECOND_ACTIVITY_FILE_NAME+ String.valueOf(i+1) + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
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
     // This function has to be modified based on the recent changes
     public List<SecondActivityCluster> readSecondActivityClusterData(String fileName) throws FileNotFoundException, IOException {
        List<SecondActivityCluster> secondActivityCluster = new ArrayList();
        SecondActivityCluster sacObj;
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        if (file.exists()) {
            while ((line = reader.readLine()) != null) {
                sacObj = new SecondActivityCluster();
                String[] splittedContent = line.split(" ");
                sacObj.setUserID(Integer.valueOf(splittedContent[0].toString()));
                String splittedTimeVector = splittedContent[1].toString();
                int timeVector[] = sacObj.getPostTimeVector();
                
                for (int i = 0; i < 6; i++) {
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")){
                        timeVector[i] = 1;
                    }
                }
                sacObj.setPostTimeVector(timeVector);
                secondActivityCluster.add(sacObj);
            }
        }
        return secondActivityCluster;
    }
    
}