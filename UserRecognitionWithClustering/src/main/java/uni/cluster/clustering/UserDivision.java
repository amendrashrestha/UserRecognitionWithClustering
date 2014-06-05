package uni.cluster.clustering;

import java.text.ParseException;
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

    /**
     * @throws java.text.ParseException
     * @Desc This function divides all the user into two users as A, B, C D, E
     * where (user:A + user:B + user:C + user:D + user:E) = total number of
     * posts of the original (user:U)
     * @param userList
     * @return List<User>
     */
    public List<User> divideUsersintoFive(List<User> userList) throws ParseException {
        List finalList = new ArrayList();
        for (User userList1 : userList) {
            User user = (User) userList1;
            List firstPostList = new ArrayList();
            List secondPostList = new ArrayList();
            List thirdPostList = new ArrayList();
            List fourthPostList = new ArrayList();
            List fifthPostList = new ArrayList();
            List postList = user.getUserPost();
            //int equalPostSize = 445;

            for (int j = 0; j < postList.size(); j++) {
                Posts userPost = (Posts) postList.get(j);
                int k = j % 5;
                if (k == 0) {
                    firstPostList.add(userPost);
                } else if (k == 1) {
                    secondPostList.add(userPost);
                } else if (k == 2) {
                    thirdPostList.add(userPost);
                } else if (k == 3) {
                    fourthPostList.add(userPost);
                } else if (k == 4) {
                    fifthPostList.add(userPost);
                }
            }
            
            User userA = new User();
            User userB = new User();
            User userC = new User();
            User userD = new User();
            User userE = new User();

            userA.setType(UserType.A);
            userB.setType(UserType.B);
            userC.setType(UserType.C);
            userD.setType(UserType.D);
            userE.setType(UserType.E);

            userA.setId(user.getId());
            userB.setId(user.getId());
            userC.setId(user.getId());
            userD.setId(user.getId());
            userE.setId(user.getId());

            userA.setUserPost(firstPostList);
            userB.setUserPost(secondPostList);
            userC.setUserPost(thirdPostList);
            userD.setUserPost(fourthPostList);
            userE.setUserPost(fifthPostList);

            finalList.add(userA);
            finalList.add(userB);
            finalList.add(userC);
            finalList.add(userD);
            finalList.add(userE);
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
     * @param index
     * @return
     * @Desc This function divides the first user into two users as A and B
     * where (user:A + user:B) = total number of posts of the original (user:U).
     * Rest of the other users contain only half of the original post whereas
     * other post has been removed.
     * @param userList
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
