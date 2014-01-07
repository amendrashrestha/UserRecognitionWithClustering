package com.user.cluster.IOHandler;

import com.user.cluster.clustering.FirstActivityCluster;
import com.user.cluster.clustering.SecondActivityCluster;
import com.user.cluster.clustering.SleepingCluster;
import com.post.parser.controller.FileDirectoryHandler;
import com.user.cluster.parser.model.Alias;
import com.user.cluster.parser.model.Posts;
import com.user.cluster.parser.model.User;
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
 * @author ITE
 */
public class IOReadWrite {

    public IOReadWrite() {
    }

    public void writeToFile(String fileName, String content) throws IOException {
        String getUserFolderName = getFolderName(fileName);
        CreateDirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH, getUserFolderName);
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

        if (userID > 0 && userID <= 25000) {
            folderName = "25K";
        } else if (userID > 25000 && userID <= 50000) {
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

    public void CreateDirectory(String path, String folderName) {
        File directory = new File(path + "\\" + folderName); //for mac use / and for windows use "\\"
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /*
     * Delete directory if it exists and will create new
     */
    public void checkAndCreateDirectory(String path, String folderName) {
        File directory = new File(path + "\\" + folderName); //for mac use / and for windows use "\\"
        if (directory.exists()) {
            deleteDir(directory);
        }
        directory.mkdirs();
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete(); // The directory is empty now and can be deleted.
    }

    public File checkAndCreateFile(String fileName) throws IOException {
        File file = new File(fileName);
        /* if (file.exists()) {
         file.delete();
         }*/
        file.createNewFile();
        return file;
    }

    public File CreateFile(String fileName) throws IOException {
        File file = new File(fileName);
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

    public String readTxtFileAsString(String basePath, String fileName, String extension) throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(basePath + "/" + fileName + extension);
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
    
     /*public String readTxtFileAsString(List<String> document) throws FileNotFoundException, IOException {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(document);
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
    }*/

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
    
    public User convertDocumentToObj(int userID, String post, String seperator) throws FileNotFoundException, IOException {
        String temp[] = null;
        User user = new User();
        List postList = new ArrayList();
        user.setId(userID);
        if (post.contains(seperator)) {
            temp = post.split(seperator);
        } else {
            temp = new String[1];
            temp[0] = post;

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
            } else {
                continue;

            }
        }
        user.setUserPost(postList);
        return user;
    }
    
    
    public List<User> getAllDocumentObj(List<String> document, String seperator) throws FileNotFoundException, IOException {
        List<User> userList = new ArrayList();
        for(int i = 0; i < document.size(); i++){
            int ID = i + 1;
            User user = convertDocumentToObj(ID, document.get(i), seperator);
            userList.add(user);
        }
        return userList;
    }

    public User convertTxtFileToUserObj(String basePath, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, fileName, extension);
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
//                posts.setContent(temp[i].substring(9, temp[i].length()));
                posts.setContent("");
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
        System.out.println(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFiles = new ArrayList();
        List allFilesSize = new ArrayList();
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList.get(i));
//        allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH);
            for (int j = 0; j < allFilesSize.size(); j++) {
                User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, directoryList.get(i).toString(),
                        allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);
                /*User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                 allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);*/
                if (user.getUserPost().size() > 60) {
                    allFiles.add(user);
                }
            }
        }
        return allFiles;
    }
    
    /**
     * Return users as an object and returns only those users who has posted
     * more than 60 messages in discussion board
     *
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<User> getAllUsersAsObject(String path) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        System.out.println(path);
        List directoryList = ioRW.getAllDirectories(path);
        List allFiles = new ArrayList();
        List allFilesSize = new ArrayList();
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(path + directoryList.get(i));
//        allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH);
            for (int j = 0; j < allFilesSize.size(); j++) {
                User user = ioRW.convertTxtFileToUserObj(path, directoryList.get(i).toString(),
                        allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);
                /*User user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                 allFilesSize.get(j).toString(), IOProperties.USER_FILE_EXTENSION);*/
                if (user.getUserPost().size() > 20) {
                    allFiles.add(user);
                }
            }
        }
        return allFiles;
    }

    public User getUsersAsObject(int userID) throws FileNotFoundException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        List directoryList = ioRW.getAllDirectories(IOProperties.INDIVIDUAL_USER_FILE_PATH);
        List allFilesSize = new ArrayList();
        User user = new User();
        String tempUserID = String.valueOf(userID);
        for (int i = 0; i < directoryList.size(); i++) {
            allFilesSize = ioRW.getAllFilesInADirectory(IOProperties.INDIVIDUAL_USER_FILE_PATH + directoryList.get(i));
            for (int j = 0; j < allFilesSize.size(); j++) {
                user = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, directoryList.get(i).toString(),
                        tempUserID, IOProperties.USER_FILE_EXTENSION);
            }
        }
        return user;
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
                int timeVector[] = fac.getUserCluster();

                for (int i = 0; i < 6; i++) {
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")) {
                        timeVector[i] = 1;
                    }
                }
                fac.setUserCluster(timeVector);
                firstActivityCluster.add(fac);
            }
        }
        return firstActivityCluster;
    }

    public void writeFirstActivityClusterData(List<FirstActivityCluster> firstActivityCluster) throws IOException {
        CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.FIRST_ACTIVITY_FOLDER_NAME);
        String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.FIRST_ACTIVITY_FOLDER_NAME
                + "\\" + IOProperties.FIRST_ACTIVITY_FILE_NAME + IOProperties.FIRST_ACTIVITY_FILE_EXTENSION;

        File file = checkAndCreateFile(completeFileNameNPath);
        int[] timeVector;
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
        for (FirstActivityCluster fac : firstActivityCluster) {
            timeVector = fac.getUserCluster();
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
            timeVector = scObj.getUserCluster();
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
                int timeVector[] = scObj.getUserCluster();

                for (int i = 0; i < 6; i++) {
                    if (!String.valueOf(splittedTimeVector.charAt(i)).equals("0")) {
                        timeVector[i] = 1;
                    }
                }
                scObj.setUserCluster(timeVector);
                sleepingCluster.add(scObj);
            }
        }
        return sleepingCluster;
    }

    public void writeSecondActivityClusterData(List<List<SecondActivityCluster>> allsecondActivityCluster, int folderName) throws IOException {
        CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH, IOProperties.SECOND_ACTIVITY_FOLDER_NAME);
        CreateDirectory(IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SECOND_ACTIVITY_FOLDER_NAME, String.valueOf(folderName));
        for (int i = 0; i < allsecondActivityCluster.size(); i++) {
            List<SecondActivityCluster> secondActivityCluster = allsecondActivityCluster.get(i);
            String completeFileNameNPath = IOProperties.All_ACTIVITY_BASE_PATH + "\\" + IOProperties.SECOND_ACTIVITY_FOLDER_NAME + "\\" + String.valueOf(folderName)
                    + "\\" + IOProperties.SECOND_ACTIVITY_FILE_NAME + String.valueOf(i + 1) + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
            File file = checkAndCreateFile(completeFileNameNPath);
            int[] timeVector;
            BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
            for (SecondActivityCluster sacObj : secondActivityCluster) {
                timeVector = sacObj.getUserCluster();
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
                        int userID = Integer.valueOf(splittedContent[0].toString());
                        String ClusterInfo = splittedContent[1];
                        List<Integer> clusterNumber = returnDigits(ClusterInfo);

                        if (clusterNumber.get(0) == 1) {
                            writeStylometricClusterData(userID, 1);
                        }
                        if (clusterNumber.get(1) == 1) {
                            writeStylometricClusterData(userID, 2);
                        }
                        if (clusterNumber.get(2) == 1) {
                            writeStylometricClusterData(userID, 3);
                        }
                        if (clusterNumber.get(3) == 1) {
                            writeStylometricClusterData(userID, 4);
                        }
                        if (clusterNumber.get(4) == 1) {
                            writeStylometricClusterData(userID, 5);
                        }
                        if (clusterNumber.get(5) == 1) {
                            writeStylometricClusterData(userID, 6);
                        }
                    }
                }
            }
        }
    }

    /**
     * split string into character and returns
     *
     * @param cluster
     * @return
     */
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

    /**
     * create files with clustering info of each users
     *
     * @param User
     * @param fileName
     * @throws IOException
     */
    public void writeStylometricClusterData(int User, int fileName) throws IOException {
        CreateDirectory(IOProperties.XML_DATA_FILE_PATH, IOProperties.CLUSTER_FOLDER_NAME);
        String completeFileNameNPath = IOProperties.XML_DATA_FILE_PATH + String.valueOf(IOProperties.CLUSTER_FOLDER_NAME)
                + "\\" + String.valueOf(fileName)
                + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
        File file = CreateFile(completeFileNameNPath);
        BufferedWriter output = new BufferedWriter(new FileWriter(file, true));

        String toWriteContent = String.valueOf(User);
        output.write(toWriteContent);
        output.newLine();
        output.close();
    }

    /**
     * read clustered files and return of List of object of user
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
//    HttpURl Http:www.amendra.com.np/something.html
//            
//    String basePath, package, className, ()
    public List<String> readClusterData(String fileName) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        List<String> dataList = new ArrayList<String>();
        String line = "";
        removeDuplicateRowsFromFile(file);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }
        }
        return dataList;
    }

    /**
     * removes duplicate users from the row
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void removeDuplicateRowsFromFile(File file) throws FileNotFoundException, IOException {
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Set<String> lines = new HashSet<String>(100000);
            String line = null;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String unique : lines) {
                writer.write(unique);
                writer.newLine();
            }
            writer.close();
        }
    }

    public Alias convertTxtFileToAliasObj(String basePath, String directoryName, String fileName, String extension) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;
        Alias alias = new Alias();
        List<String> postList = new ArrayList<String>();
        List<String> timeList = new ArrayList<String>();
        alias.setUserID(fileName);
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
            String date = temp[i].substring(0, 8);
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                timeList.add(date);
                postList.add(temp[i].substring(9, temp[i].length()));
            } else {
                continue;
            }
        }
        alias.setPostTime(timeList);
        alias.setPosts(postList);
        return alias;
    }

    public List<Alias> convertTxtFileToAliasObjAndDivide(int divisionFlag, String basePath, String directoryName,
            String fileName, String extension, List<Alias> aliasList) throws FileNotFoundException, IOException {
        String userPostAsString = readTxtFileAsString(basePath, directoryName, fileName, extension);
        String temp[] = null;
        Alias aliasA = new Alias();
        Alias aliasB = new Alias();
        List<String> postListA = new ArrayList<String>();
        List<String> timeListA = new ArrayList<String>();
        List<String> postListB = new ArrayList<String>();
        List<String> timeListB = new ArrayList<String>();
        aliasA.setUserID(fileName);
        aliasB.setUserID(fileName);
        aliasA.setType("A");
        aliasB.setType("B");
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
            String date = temp[i].substring(0, 8);
            String content = "";
            if (date.matches("[0-9]{2}:[0-9]{2}:[0-9]{2}")) {
                content = temp[i].substring(9, temp[i].length());
                if (i % 2 == 0 && divisionFlag == 2) {
                    timeListA.add(date);
                    postListA.add(content);
                } else if (i % 2 != 0) {
                    timeListB.add(date);
                    postListB.add(content);
                }
            } else {
                continue;
            }
        }

        if (divisionFlag == 2) {
            aliasA.setPostTime(timeListA);
            aliasA.setPosts(postListA);
            aliasList.add(0, aliasA);
        }

        aliasB.setPostTime(timeListB);
        aliasB.setPosts(postListB);
        aliasList.add(aliasB);

        return aliasList;
    }

    /**
     * returns user as an object
     *
     * @param userIDList
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<Alias> getUserAsAlias(List<String> userIDList) throws FileNotFoundException, IOException {

        Alias alias = new Alias();
        List<Alias> aliasList = new ArrayList<Alias>();
        IOReadWrite ioReadWrite = new IOReadWrite();

        for (int i = 0; i < userIDList.size(); i++) {
            alias = ioReadWrite.convertTxtFileToAliasObj(IOProperties.INDIVIDUAL_USER_FILE_PATH,
                    ioReadWrite.getFolderName(userIDList.get(i)), userIDList.get(i), IOProperties.USER_FILE_EXTENSION);
            aliasList.add(alias);
        }
        return aliasList;
    }

}