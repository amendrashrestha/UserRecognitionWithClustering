/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.user.cluster.parser.model;

import java.util.Comparator;

/**
 *
 * @author ITE
 */
public class ReturnSortedUserList implements Comparator<User> {

    /**
     *
     * @param first
     * @param second
     * @return
     */
    public int compare(User first, User second) {
        int firstUseID = first.getId();
        int secondUseID = second.getId();
        return Integer.compare(firstUseID, secondUseID);
    }
    
}
