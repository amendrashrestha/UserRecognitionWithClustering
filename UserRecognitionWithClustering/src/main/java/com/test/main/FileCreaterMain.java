package com.test.main;

import com.post.parser.view.CreateParser;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Batman
 */
public class FileCreaterMain {
     public static void main(String args[]) throws SQLException, IOException{
        CreateParser init = new CreateParser();
        init.createUserFile();
    }
}