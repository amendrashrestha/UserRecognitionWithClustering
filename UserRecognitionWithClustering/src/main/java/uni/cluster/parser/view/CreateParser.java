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
                String postDateTime = post.getCreatedPost();
                String postContent = post.getContent();
                
                String[] parts = postDateTime.split("T");
                post.setCreatedPostDate(parts[0]);
                post.setCreatedPost(parts[1].replace("Z", ""));
                String postCreatedDate = post.getCreatedPostDate();
                String postTime = post.getCreatedPost();

                //write both time and text
                //String getText = createContentToWrite(postTime, postCreatedDate, postContent);

                //writing only date and time
                String getText = createContentToWrite(postTime, postCreatedDate);
                ioRW.writeToFile(userID, getText);
            }
        }
    }

    public String createContentToWrite(String createdPostTime, String postCreatedDate, String message) {
        return createdPostTime + " " + postCreatedDate + " " + message;
    }

    public String createContentToWrite(String time, String date) {
        return time + " " + date;
    }
}
