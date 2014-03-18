/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.stylo.featureVector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.Alias;

/**
 *
 * @author ITE
 */
public class FeatureVector {

    public void create() throws FileNotFoundException, IOException, SQLException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        String aliasFolderPath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
        List<String> folderName = new ArrayList<>();
        List<String> filesName = new ArrayList<>();

        System.out.println("User Data Path: " + aliasFolderPath);
        folderName = ioReadWrite.getAllDirectories(aliasFolderPath);
        for (int i = 0; i < folderName.size(); i++) {
            String folder = folderName.get(i);
            filesName = ioReadWrite.getAllFilesInADirectory(aliasFolderPath + "\\" + folder);
            userFile(filesName);
        }
    }

    public void userFile(List<String> files) throws FileNotFoundException, IOException, SQLException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        String basePath = IOProperties.INDIVIDUAL_USER_FILE_PATH;
        String ext = IOProperties.USER_FILE_EXTENSION;

        for (String file : files) {
            String aliasID = file.toString();
            String directoryName = ioReadWrite.getFolderName(aliasID);
            Alias alias = ioReadWrite.convertTxtFileToAliasObjAndDivide(basePath, directoryName, aliasID, ext);
            int NoOfPosts = alias.getPosts().size();
            if (NoOfPosts > 30) {
                FeatureVectorofUserPost init = new FeatureVectorofUserPost();
                List<Float> userFeatureVector = init.returnFeatureVector(alias);
                ioReadWrite.writeFVToFile(alias.getUserID(), userFeatureVector);
            }
        }
    }
}
