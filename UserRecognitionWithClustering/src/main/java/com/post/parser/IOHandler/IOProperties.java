package com.post.parser.IOHandler;

/**
 *
 * @author Batman
 */
public class IOProperties {

    public static final String XML_DATA_FILE_PATH = "C:\\Users\\ITE\\Downloads\\2008\\posts\\";
//    public static final String XML_DATA_FILE_PATH = "C:\\Users\\ITE\\Downloads\\1998\\";
    public static final String All_ACTIVITY_BASE_PATH = "C:\\Users\\ITE\\Downloads\\2008\\ActivityData\\";
//    public static final String All_ACTIVITY_BASE_PATH = "C:\\Users\\ITE\\Downloads\\1998\\ActivityData\\";
    /**
     * This is the base path for all the created data throughout the experiment. 
     * This path has to exist before running the program
     */
    //For creating first activity peak you need to pass the folder name too
//    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\2008\\UsersPost\\150K\\";
    
    //For other activity thing
    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Desktop\\UsersPost_06_07_08\\";

//    public static final String INDIVIDUAL_USER_FILE_PATH = "C:\\Users\\ITE\\Downloads\\1998\\UsersPost\\";
    
    public static final String CLUSTER_FOLDER_NAME = "ClusterData";
    /**
     * Following are the text file and text seperator for reading the data from XML and writing it to text file
     */
    public static final String USER_FILE_EXTENSION = ".txt";
    public static final String DATA_SEPERATOR = "This seperates users";
    /**
     * Following are the files and folder associated with the first activity cluster
     */
    
    public static final String FIRST_ACTIVITY_DATA_SEPERATOR = " ";
    public static final String FIRST_ACTIVITY_FOLDER_NAME = "First Activity";
    public static final String FIRST_ACTIVITY_FILE_NAME = "firstactivitycluster";
    public static final String FIRST_ACTIVITY_FILE_EXTENSION = ".txt";
    
    /**
     * Following are the files and folder associated with the sleeping cluster
     */
    public static final String SLEEPING_DATA_SEPERATOR = " ";
    public static final String SLEEPING_FOLDER_NAME = "Sleeping Cluster";
    public static final String SLEEPING_FILE_NAME = "sleepingcluster";
    public static final String SLEEPING_FILE_EXTENSION = ".txt";
    
     /**
     * Following are the files and folder associated with the second activity cluster
     */
    public static final String SECOND_ACTIVITY_DATA_SEPERATOR = " ";
    public static final String SECOND_ACTIVITY_FOLDER_NAME = "Second Activity";
    public static final String SECOND_ACTIVITY_FILE_NAME = "secondactivitycluster";
    public static final String SECOND_ACTIVITY_FILE_EXTENSION = ".txt";
}