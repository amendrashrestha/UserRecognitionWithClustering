/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.post.stylo.featureVector;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import uni.cluster.analysis.TimeAndStylometricMatching;
import uni.cluster.parser.model.Alias;

/**
 *
 * @author ITE
 */
public class FeatureVectorofUserPost {

    public List<Float> returnFeatureVector(Alias alias) throws SQLException {
        TimeAndStylometricMatching init = new TimeAndStylometricMatching();
        int cnt = 0;
        alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
        // Calculate each part of the "feature vector" for each individual post
        for (String post : alias.getPosts()) {
            List<String> wordsInPost = TimeAndStylometricMatching.extractWords(post);
            alias.addToFeatureVectorPostList(init.countFunctionWords(wordsInPost), 0, cnt);
            alias.addToFeatureVectorPostList(init.countWordLengths(wordsInPost), 293, cnt);
            alias.addToFeatureVectorPostList(init.countCharactersAZ(post), 313, cnt);
            alias.addToFeatureVectorPostList(init.countSpecialCharacters(post), 339, cnt);
            cnt++;
        }

        ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

        int numberOfPosts = alias.getPosts().size();
        int nrOfFeatures = (featureVectorList.size() > 0) ? featureVectorList.get(0).size() : 0;

        List<Float> featureVector = new ArrayList<>(Collections.nCopies(nrOfFeatures, 0.0f));
        // Now we average over all posts to create a single feature vector for each alias
        for (int i = 0; i < nrOfFeatures; i++) {
            float value = 0.0f;
            for (int j = 0; j < numberOfPosts; j++) {
                value += featureVectorList.get(j).get(i);
            }
            value /= numberOfPosts;
            featureVector.set(i, value);
        }
        return featureVector;
    }
}
