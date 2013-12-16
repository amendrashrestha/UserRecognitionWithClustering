package com.user.cluster.main;

import com.user.cluster.parser.view.CreateParser;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author ITE
 */
public class FileCreaterMain {
     public static void main(String args[]) throws SQLException, IOException{
        CreateParser init = new CreateParser();
        init.createUserFile();
    }
}