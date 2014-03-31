/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.User;

/**
 *
 * @author ITE
 */
public class CountNumberOfUsers {
    
    public CountNumberOfUsers() throws FileNotFoundException, IOException{
        count();
    }
    
    public static void main(String args[]) throws FileNotFoundException, IOException{
        CountNumberOfUsers init = new CountNumberOfUsers();
    }

    private void count() throws FileNotFoundException, IOException {
        IOReadWrite ioReadWrite = new IOReadWrite();
        List<User> unDividedUserList = ioReadWrite.getAllUsersAsObject();
        System.out.println("User Size: " + unDividedUserList.size());
    }
    
}
