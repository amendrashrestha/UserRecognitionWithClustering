/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.graph.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;

/**
 *
 * @author ITE
 */
public class CreateActivityGraph {

    public void FirstActivityGraph(String filePath) throws FileNotFoundException, IOException {
        //HashMap<Integer, String> facClusterMap = new HashMap<>();
        getFirstActivityData(filePath);
        //createGraph(facClusterMap);
    }

    public void SleepingClusterGraph(String filePath) throws FileNotFoundException, IOException {
        HashMap<Integer, String> sClusterMap = new HashMap<>();
        sClusterMap = getSleepingActivityData(filePath);
        printClusterMap(sClusterMap);
        //createGraph(sClusterMap);
    }

    public void SecondActivityGraph(String filePath) {
        createSACGraph(filePath);
    }

    private void createGraph(HashMap<Integer, String> clusterMap) {
        System.out.println("");
    }

    private void createSACGraph(String filePath) {
    }

    private void getFirstActivityData(String filePath) throws FileNotFoundException, IOException {
        //HashMap<Integer, String> facData = new HashMap<>();
        Set<Integer> firstClusterList = new TreeSet<>();
        Set<Integer> secondClusterList = new TreeSet<>();
        Set<Integer> thirdClusterList = new TreeSet<>();
        Set<Integer> fourthClusterList = new TreeSet<>();
        Set<Integer> fifthClusterList = new TreeSet<>();
        Set<Integer> sixthClusterList = new TreeSet<>();
        
        IOReadWrite io = new IOReadWrite();
        List allFACFiles;
        allFACFiles = io.getAllFilesInADirectory(filePath);

        for (int i = 0; i < allFACFiles.size(); i++) {
            String fileName = allFACFiles.get(i).toString();
            String completeFilePath = filePath + "\\" + fileName + IOProperties.FIRST_ACTIVITY_FILE_EXTENSION;
            File file = new File(completeFilePath);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;

            if (file.exists()) {
                while ((line = reader.readLine()) != null) {
                    String[] splittedContent = line.split(" ");
                    int userID = Integer.valueOf(splittedContent[0].toString());
                    String ClusterInfo = splittedContent[1];
                    List<Integer> clusterNumber = io.returnDigits(ClusterInfo);

                        if (clusterNumber.get(0) == 1) {
                            firstClusterList.add(userID);
                        }
                        if (clusterNumber.get(1) == 1) {
                            secondClusterList.add(userID);
                        }
                        if (clusterNumber.get(2) == 1) {
                            thirdClusterList.add(userID);
                        }
                        if (clusterNumber.get(3) == 1) {
                            fourthClusterList.add(userID);
                        }
                        if (clusterNumber.get(4) == 1) {
                            fifthClusterList.add(userID);
                        }
                        if (clusterNumber.get(5) == 1) {
                            sixthClusterList.add(userID);
                    }
                }
            }
            System.out.println("1st Cluster Size: " + firstClusterList.size());
            System.out.println("2nd Cluster Size: " + secondClusterList.size());
            System.out.println("3rd Cluster Size: " + thirdClusterList.size());
            System.out.println("4th Cluster Size: " + fourthClusterList.size());
            System.out.println("5th Cluster Size: " + fifthClusterList.size());
            System.out.println("6th Cluster Size: " + sixthClusterList.size());
        }
    }

    private HashMap<Integer, String> getSleepingActivityData(String filePath) throws FileNotFoundException, IOException {
        HashMap<Integer, String> scData = new HashMap<>();
        IOReadWrite io = new IOReadWrite();
        List allSCFiles;
        allSCFiles = io.getAllFilesInADirectory(filePath);
        for (int i = 0; i < allSCFiles.size(); i++) {
            String filename = allSCFiles.get(i).toString();
            String clusterID = filename.replaceAll("[\\D]", "");
            String completeFilePath = filePath + "\\" + filename + IOProperties.SECOND_ACTIVITY_FILE_EXTENSION;
            File file = new File(completeFilePath);
            FileReader fr = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(fr);

            int lineNumber = 0;
            while (lnr.readLine() != null) {
                lineNumber++;
            }
            scData.put(lineNumber, clusterID);

        }
        return scData;
    }

    private void printClusterMap(HashMap<Integer, String> clusterMap) {
        Iterator<Integer> cluster = clusterMap.keySet().iterator();
        while (cluster.hasNext()) {
            Integer k = cluster.next();
            String value = clusterMap.get(k);
            System.out.println(k + "\t" + value);
        }
    }
}
