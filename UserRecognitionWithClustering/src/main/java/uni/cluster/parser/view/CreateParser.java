package uni.cluster.parser.view;

import java.io.IOException;
import java.sql.SQLException;
import uni.cluster.IOHandler.IOProperties;
import uni.cluster.IOHandler.IOReadWrite;
import uni.cluster.parser.model.PostBean;
import uni.post.parser.controller.PostParser;

/**
 *
 * @author ITE
 */
public class CreateParser {

    public CreateParser() {
    }

    public void createUserFile() throws SQLException, IOException {
        IOReadWrite ioRW = new IOReadWrite();
        String[] allXmlFileName = ioRW.getAllFileNameWithPath(IOProperties.XML_DATA_FILE_PATH);
        String userID;
        String initialFileName;
        PostBean post;
        PostParser parser = new PostParser();

        for (int i = 0; i < allXmlFileName.length; i++) {
            initialFileName = allXmlFileName[i];
            System.out.println("FileName : " + initialFileName);
            post = parser.parsePost(initialFileName);
            userID = post.getUserID(post.getUser());
            if (Integer.valueOf(userID) != 0) {
                String userPost = post.getCreatedPost();
                String[] parts = userPost.split("T");
                post.setCreatedPostDate(parts[0]);
                post.setCreatedPost(parts[1].replace("Z", ""));

                //write both time and text
//                String getText = createContentToWrite(post.getCreated(), post.getContent());

                //writing only time
                String getText = createContentToWrite(post.getCreatedPost());
                ioRW.writeToFile(userID, getText);
            }
        }
    }

    public String createContentToWrite(String createdPostTime, String message) {
        return createdPostTime + " " + message;
    }

    public String createContentToWrite(String createdPostTime) {
        return createdPostTime;
    }
}
