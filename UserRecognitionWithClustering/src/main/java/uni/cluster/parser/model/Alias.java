/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.cluster.parser.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Alias {

    private List<Float> featureVector;
    private int nrOfFeatures;
    private String user;
    private String type;
    private ArrayList<ArrayList<Float>> featureVectorPostList;
    public List<String> posts;
    public List<String> postTime;
    public List<String> postDate;

    public Alias(String userID) throws SQLException {
        this.user = userID;
        //setNrOfFeatures(293 + 20 + 26 + 21);
        // setNrOfFeatures(293 + 20 + 26 + 21 + 6);
        //setNrOfFeatures(26 + 21 + 293 + 20);
        // setNrOfFeatures(26 + 21 + 293 + 20 + 1);
        //setNrOfFeatures(293);
        featureVector = new ArrayList<>();
        featureVectorPostList = new ArrayList<>();
        // timeVector = new ArrayList();
    }

    public Alias() {
        setNrOfFeatures(293 + 20 + 26 + 21);
    }

    @Override
    public String toString() {
        return user;
    }

    public ArrayList<ArrayList<Float>> initializeFeatureVectorPostList() {
        ArrayList<ArrayList<Float>> list = new ArrayList<>();
        for (int j = 0; j < posts.size(); j++) {
            ArrayList<Float> featList = new ArrayList<>();
            for (int i = 0; i < nrOfFeatures; i++) {
                featList.add(0.0f);
            }
            list.add(featList);
        }
        return list;
    }

    public String getUserID() {
        return user;
    }

    public void setUserID(String userID) {
        this.user = userID;
    }

    public List<String> getPosts() {
        return posts;
    }

    public void setPosts(List<String> posts) {
        this.posts = posts;
    }

    public void addPost(String post) {
        posts.add(post);
    }

    public void addToFeatureVectorPostList(ArrayList<Float> freqDist, int startIndex, int index) {
        for (int i = 0; i < freqDist.size(); i++) {
            featureVectorPostList.get(index).set(startIndex, freqDist.get(i));
            startIndex++;
        }
    }

    public List<Float> getFeatureVector() {
        return featureVector;
    }

    public void setFeatureVector(List<Float> featureVector) {
        this.featureVector = featureVector;
    }

    public ArrayList<ArrayList<Float>> getFeatureVectorPosList() {
        return featureVectorPostList;
    }

    public void setFeatureVectorPosList(ArrayList<ArrayList<Float>> list) {
        featureVectorPostList = list;
    }

    public void setFeatureValue(int index, float newValue) {
        featureVector.set(index, newValue);
    }

    public int getNrOfFeatures() {
        return nrOfFeatures;
    }

    public final void setNrOfFeatures(int nrOfFeatures) {
        this.nrOfFeatures = nrOfFeatures;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double[] getTimeVector() throws SQLException {
        double[] rr = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        
        for(String postHour : postTime){
            String[] time = postHour.split(":");
            int hr = Integer.parseInt(time[0]);
            rr[hr]++;
        }
        return rr;
    }

    public static double[] getTimeVectorArray(List<String> postTime) throws SQLException {

        double[] rr = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

         for(String postHour : postTime){
            String[] time = postHour.split(":");
            int hr = Integer.parseInt(time[0]);
            rr[hr]++;
        }
        return rr;
    }

    public void setPostTime(List postTime) {
        this.postTime = postTime;
    }

    public List getPostTime() {
        return postTime;
    }
    
     public void setPostDate(List postDate) {
        this.postDate = postDate;
    }

    public List getPostDate() {
        return postDate;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
