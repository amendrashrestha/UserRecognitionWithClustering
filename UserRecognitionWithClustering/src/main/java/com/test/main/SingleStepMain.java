/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test.main;

import com.post.parser.clustering.Cluster;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Batman
 */
public class SingleStepMain {
    
    public static void main(String [] args) throws FileNotFoundException, IOException{
        Cluster cluster = new Cluster();
        cluster.getAllClusterizedUser();
    }
}
