package uni.cluster.main;

import java.io.IOException;
import java.sql.SQLException;
import uni.cluster.parser.view.CreateParser;

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