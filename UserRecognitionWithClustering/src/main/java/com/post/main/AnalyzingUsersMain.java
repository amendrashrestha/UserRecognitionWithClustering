/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.main;

import com.post.analysis.UserAnalysis;
import com.post.parser.IOHandler.IOProperties;
import com.post.parser.IOHandler.IOReadWrite;
import com.post.parser.model.Alias;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ITE
 */
public class AnalyzingUsersMain {

    public static void main(String args[]) throws FileNotFoundException, IOException, SQLException {
        UserAnalysis init = new UserAnalysis();
        init.executeAnalysis();
    }

    public List<Alias> getUserAlias(List<String> userIDList) throws FileNotFoundException, IOException {

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
