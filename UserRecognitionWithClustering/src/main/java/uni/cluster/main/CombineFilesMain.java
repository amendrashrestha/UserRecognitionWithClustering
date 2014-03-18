/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import uni.post.parser.controller.CopyFileContent;

/**
 *
 * @author ITE
 */
public class CombineFilesMain {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        CopyFileContent init = new CopyFileContent();
        init.copyContents();
    }
}
