package uni.cluster.clustering;

import java.util.ArrayList;
import java.util.List;
import uni.cluster.parser.model.Posts;
import uni.cluster.parser.model.User;
import uni.cluster.parser.model.UserType;

/**
 *
 * @author ITE
 */
public class UserDivision {

    /**
     * @Desc This function divides all the user into two users as A and B where
     * (user:A + user:B) = total number of posts of the original (user:U)
     * @param userList
     * @return List<User>
     */
    public List<User> divideAllUser(List<User> userList) {
        List finalList = new ArrayList();
        for (int i = 0; i < userList.size(); i++) {
            User user = (User) userList.get(i);
            List firstPostList = new ArrayList();
            List secondPostList = new ArrayList();
            List postList = user.getUserPost();
            for (int j = 0; j < postList.size(); j++) {
                if (j % 2 == 0) {
                    firstPostList.add((Posts) postList.get(j));
                } else {
                    secondPostList.add((Posts) postList.get(j));
                }
            }
            User userA = new User();
            User userB = new User();
            userA.setType(UserType.A);
            userB.setType(UserType.B);
            userA.setId(user.getId());
            userB.setId(user.getId());
            userA.setUserPost(firstPostList);
            userB.setUserPost(secondPostList);
            finalList.add(userA);
            finalList.add(userB);
        }
        return finalList;
    }

    public List divideUser(User user) {
        List finalList = new ArrayList();
        List firstPostList = new ArrayList();
        List secondPostList = new ArrayList();
        List postList = user.getUserPost();
        for (int j = 0; j < postList.size(); j++) {
            if (j % 2 == 0) {
                firstPostList.add((Posts) postList.get(j));
            } else {
                secondPostList.add((Posts) postList.get(j));
            }
        }
        User userA = new User();
        User userB = new User();
        userA.setType(UserType.A);
        userB.setType(UserType.B);
        userA.setId(user.getId());
        userB.setId(user.getId());
        userA.setUserPost(firstPostList);
        userB.setUserPost(secondPostList);
        finalList.add(userA);
        finalList.add(userB);

        return finalList;
    }

    /**
     * @Desc This function divides the first user into two users as A and B
     * where (user:A + user:B) = total number of posts of the original (user:U).
     * Rest of the other users contain only half of the original post whereas
     * other post has been removed.
     * @param userList
     * @return List<User>
     */
    public List<User> divideFirstUser(List<User> userList, int index) {
        List finalList = new ArrayList();
        for (int i = 0; i < userList.size(); i++) {
            User user = (User) userList.get(i);
            List firstUserList = null;
            if (i == index) {
                firstUserList = new ArrayList();
            }
            List postList = user.getUserPost();
            List toAddPostList = new ArrayList();
            for (int j = 0; j < postList.size(); j++) {
                if (j % 2 == 0 && i == index) {
                    firstUserList.add((Posts) postList.get(j));
                } else if (j % 2 != 0) {
                    toAddPostList.add((Posts) postList.get(j));
                }
            }
            if (i == 0) {
                User firstUser = new User();
                firstUser.setType(UserType.A);
                firstUser.setId(user.getId());
                firstUser.setUserPost(firstUserList);
                finalList.add(0, firstUser);
            }
            User users = new User();
            users.setType(UserType.B);
            users.setId(user.getId());
            users.setUserPost(toAddPostList);
            finalList.add(users);
        }
        return finalList;
    }
    /*public List<Alias> divideFirstAlias(List<Alias> aliasList, int index){
     List finalList = new ArrayList();
     for (int i = 0; i < aliasList.size(); i++) {
     //User user = (User) userList.get(i);
     Alias alias = (Alias) aliasList.get(i);
     List firstAliasList = null;
     if (i == index) {
     firstAliasList = new ArrayList();
     }
            
     List postList = alias.getPosts();
     List timeList = alias.getPostTime();
            
            
     //            List postList = user.getUserPost();
     List toAddPostList = new ArrayList();
     for (int j = 0; j < postList.size(); j++) {
     if (j % 2 == 0 && i == index) {
     firstUserList.add((Posts) postList.get(j));
     } else if (j % 2 != 0) {
     toAddPostList.add((Posts) postList.get(j));
     }
     }
     if (i == 0) {
     User firstUser = new User();
     firstUser.setType(UserType.A);
     firstUser.setId(user.getId());
     firstUser.setUserPost(firstUserList);
     finalList.add(0, firstUser);
     }
     User users = new User();
     users.setType(UserType.B);
     users.setId(user.getId());
     users.setUserPost(toAddPostList);
     finalList.add(users);
     }
     return finalList;
     }*/
}
