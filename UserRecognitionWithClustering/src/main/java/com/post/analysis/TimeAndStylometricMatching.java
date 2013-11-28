package com.post.analysis;

import com.post.parser.model.Alias;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This is some code for doing stylometric matching of aliases based on posts
 * (such as discussion board messages). Features: letters (26), digits (10),
 * punctuation (11), function words (293), word length (20), sentence length
 * (6). Except for freq. of sentence lengths, this is a subset of the features
 * used in Narayanan et al. (On the Feasibility of Internet-Scale Author
 * Identification)
 *
 * Some problems to consider: The more features, the more "sparse" the feature
 * vectors will be (many zeros) in case of few posts --> similar feature vectors
 * due to a majority of zeros
 *
 * Since all features are not of the same "dimension", it makes sense to
 * normalize/standardize the features to have mean 0 and variance 1, as in
 * Narayanan et al. The above standardization works when finding the best
 * matching candidate, but may be problematic since the "similarity" between two
 * aliases will depend on the features of other aliases (since the
 * standardization works column/(feature)-wise).
 *
 * If we do not use normalization/standardization, we cannot use feature which
 * are not frequencies, since the features with large magnitudes otherwise will
 * dominate completely!!! Even if we do only use frequencies, the results
 * without normalization seems poor (good with normalization) Try to improve the
 * unnormalized version before using it on real problems...
 *
 * Observe that the obtained similarity values cannot be used directly as a
 * measure of the "match percentage"!
 *
 *
 * @author frejoh
 *
 */
public class TimeAndStylometricMatching {

    public List<String> functionWords; // Contains the function words we are using
    private static String path = "C:/Users/ITE/Documents/NetBeansProjects/BoardAliasMatching/src/Utilities/function_words.txt"; //Change to the correct path;
    // private static String path = "/Users/amendrashrestha/NetBeansProjects/BoardAliasMatching/src/Utilities/function_words.txt"; //Change to the correct path;
    public List<Alias> aliases; // The aliases we are interested in to compare
    List userList;
    List username;
    private List<List<Float>> featVectorForAllAliases;
    List tempDisplayInfo;
    List<Integer> rank;
    int rankArray[] = {0, 0, 0, 0};
    int top1, top3, other;
    HashSet matchedUserSet = new HashSet();
    static HashMap<Integer, HashSet<Integer>> userResult = new HashMap<Integer, HashSet<Integer>>();

    public TimeAndStylometricMatching() throws SQLException {
        functionWords = new ArrayList<String>();
        loadFunctionWords();
        rank = new ArrayList<Integer>();
    }

    public void executeStylo(List<Alias> aliasList) throws SQLException {
        this.aliases = aliasList;
        createFeatureVectors();
        compareAllPairsOfAliases();
    }

    /**
     * Extract words from text string, remove punctuation etc.
     *
     * @param text
     * @return
     */
    public static List<String> extractWords(String text) {
        List<String> wordList = new ArrayList<String>();
        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("[^\\w]", "");
            wordList.add(words[i]);
        }
        return wordList;
    }

    /**
     * Load the list of function words from file
     */
    public void loadFunctionWords() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));

            String strLine;
            while ((strLine = br.readLine()) != null) {
                functionWords.add(strLine);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a list containing the number of occurrences of the various
     * function words in the post (list of extracted words)
     *
     * @param words
     * @return
     */
    public ArrayList<Float> countFunctionWords(List<String> words) {
        ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(functionWords.size(), 0.0f));	// Initialize to zero
        //System.out.println("Nr of function words is: " + functionWords.size());
        for (int i = 0; i < words.size(); i++) {
            if (functionWords.contains(words.get(i))) {
                int place = functionWords.indexOf(words.get(i));
                float value = (Float) tmpCounter.get(place);
                value++;
                tmpCounter.set(place, value);
            }
        }
        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) words.size());
        }
        return tmpCounter;
    }

    /**
     * Create a list containing the number of occurrences of letters a to z in
     * the text
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countCharactersAZ(String post) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }
        // "Normalize" the values by dividing with total nr of characters in the post (excluding white spaces)
        int length = post.replaceAll(" ", "").length();
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
        return tmpCounter;
    }

    /**
     * Create a list containing the number of special characters in the text
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countSpecialCharacters(String post) {
        post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
        char[] ch = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '?', '!', ',', ';', ':', '(', ')', '"', '-', '¥'};
        ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(ch.length, 0.0f));
        for (int i = 0; i < ch.length; i++) {
            int value = countOccurrences(post, ch[i]);
            tmpCounter.set(i, (float) value);
        }
        // "Normalize" the values by dividing with total nr of characters in the post (excluding whitespaces)
        int length = post.replaceAll(" ", "").length();
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) length);
        }
        return tmpCounter;
    }

    /**
     * Counts the frequency of various word lengths in the list of words.
     *
     * @param words
     * @return
     */
    public ArrayList<Float> countWordLengths(List<String> words) {
        ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(20, 0.0f));	// Where 20 corresponds to the number of word lengths of interest
        int wordLength = 0;
        for (String word : words) {
            wordLength = word.length();
            // We only care about wordLengths in the interval 1-20
            if (wordLength > 0 && wordLength <= 20) {
                float value = (Float) tmpCounter.get(wordLength - 1);	// Observe that we use wordLength-1 as index!
                value++;
                tmpCounter.set(wordLength - 1, value);
            }
        }
        // "Normalize" the values by dividing with length of the post (nr of words in the post)
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) words.size());
        }
        return tmpCounter;
    }

    /**
     * Counts the frequency of various sentence lengths in the post.
     *
     * @param post
     * @return
     */
    public ArrayList<Float> countSentenceLengths(String post) {
        ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(6, 0.0f));	// Where 6 corresponds to the number of sentence lengths of interest
        // Split the post into a number of sentences
        List<String> sentences = splitIntoSentences(post);
        int nrOfWords = 0;
        for (String sentence : sentences) {
            // Get number of words in the sentence
            List<String> words = extractWords(sentence);
            nrOfWords = words.size();
            if (nrOfWords > 0 && nrOfWords <= 10) {
                tmpCounter.set(0, tmpCounter.get(0) + 1);
            } else if (nrOfWords <= 20) {
                tmpCounter.set(1, tmpCounter.get(1) + 1);
            } else if (nrOfWords <= 30) {
                tmpCounter.set(2, tmpCounter.get(2) + 1);
            } else if (nrOfWords <= 40) {
                tmpCounter.set(3, tmpCounter.get(3) + 1);
            } else if (nrOfWords <= 50) {
                tmpCounter.set(4, tmpCounter.get(4) + 1);
            } else if (nrOfWords >= 51) {
                tmpCounter.set(5, tmpCounter.get(5) + 1);
            }
        }
        // "Normalize" the values by dividing with nr of sentences in the post
        for (int i = 0; i < tmpCounter.size(); i++) {
            tmpCounter.set(i, tmpCounter.get(i) / (float) sentences.size());
        }
        return tmpCounter;
    }

    /**
     * Splits a post/text into a number of sentences
     *
     * @param text
     * @return
     */
    public List<String> splitIntoSentences(String text) {
        List<String> sentences = new ArrayList<String>();
        BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
        iterator.setText(text);
        int start = iterator.first();
        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
            sentences.add(text.substring(start, end));
        }
        return sentences;
    }

    /**
     * Count the number of occurrences of certain character in a String
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }

    /**
     * Loops through all aliases and construct their feature vectors
     */
    public void createFeatureVectors() {
        featVectorForAllAliases = new ArrayList<List<Float>>();
        for (Alias alias : aliases) {
            int cnt = 0;
            alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
            // Calculate each part of the "feature vector" for each individual post
            for (String post : alias.getPosts()) {
                List<String> wordsInPost = extractWords(post);
                alias.addToFeatureVectorPostList(countFunctionWords(wordsInPost), 0, cnt);
                alias.addToFeatureVectorPostList(countWordLengths(wordsInPost), 293, cnt);
                alias.addToFeatureVectorPostList(countCharactersAZ(post), 313, cnt);
                alias.addToFeatureVectorPostList(countSpecialCharacters(post), 339, cnt);
                //alias.addToFeatureVectorPostList(countSentenceLengths(post), 359, cnt);
                cnt++;
            }

            ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

            int numberOfPosts = alias.getPosts().size();
            int nrOfFeatures = (featureVectorList.size() > 0) ? featureVectorList.get(0).size() : 0;

            List<Float> featureVector = new ArrayList<Float>(Collections.nCopies(nrOfFeatures, 0.0f));
            // Now we average over all posts to create a single feature vector for each alias
            for (int i = 0; i < nrOfFeatures; i++) {
                float value = 0.0f;
                for (int j = 0; j < numberOfPosts; j++) {
                    value += featureVectorList.get(j).get(i);
                }
                value /= numberOfPosts;
                featureVector.set(i, value);
            }
            alias.setFeatureVector(featureVector);
            featVectorForAllAliases.add(featureVector);
        }
        normalizeFeatureVector();
    }

    /**
     * Used for comparing two feature vectors
     *
     * @param featVector1
     * @param featVector2
     * @return
     */
    public double compareFeatureVectors(List<Float> featVector1, List<Float> featVector2) {
        if (featVector1.isEmpty() || featVector2.isEmpty()) {
            return 0;
        }
        List<Float> floatList = featVector1;
        float[] floatArray1 = new float[floatList.size()];

        for (int i = 0; i < floatList.size(); i++) {
            Float f = floatList.get(i);
            floatArray1[i] = (f != null ? f : Float.NaN);
        }

        List<Float> floatList2 = featVector2;
        float[] floatArray2 = new float[floatList2.size()];

        for (int i = 0; i < floatList2.size(); i++) {
            Float f = floatList2.get(i);
            floatArray2[i] = (f != null ? f : Float.NaN);
        }
        return calculateSimilarity(floatArray1, floatArray2);
    }

    /**
     * Calculates cosine similarity between two real vectors
     *
     * @param value1
     * @param value2
     * @return
     */
    public double calculateSimilarity(float[] value1, float[] value2) {
        float sum = 0.0f;
        float sum1 = 0.0f;
        float sum2 = 0.0f;
        for (int i = 0; i < value1.length; i++) {
            float v1 = value1[i];
            float v2 = value2[i];
            if ((!Float.isNaN(v1)) && (!Float.isNaN(v2))) {
                sum += v2 * v1;
                sum1 += v1 * v1;
                sum2 += v2 * v2;
            }
        }
        if ((sum1 > 0) && (sum2 > 0)) {
            double result = sum / (Math.sqrt(sum1) * Math.sqrt(sum2));
            // result can be > 1 (or -1) due to rounding errors for equal vectors, but must be between -1 and 1
            return Math.min(Math.max(result, -1d), 1d);
            //return result;
        } else if (sum1 == 0 && sum2 == 0) {
            return 1d;
        } else {
            return 0d;
        }
    }

    /**
     * For test purpose Calculate similarity between 1st user of list with rest
     * of the user in list. magnitude of posted time is normalized before
     * calculating Manhattan distance between two users
     */
    public void compareAllPairsOfAliases() throws SQLException {
        tempDisplayInfo = new ArrayList<List>();

        for (int i = 1; i < aliases.size(); i++) {
            List tempList = new ArrayList();
            double styloMatch = 0.0;
            String user1 = aliases.get(0).getUser();
            String user2 = aliases.get(i).getUser();

            /**
             * calculate stylo for alias
             */
            List user1featVector = aliases.get(0).getFeatureVector();
            List user2featVector = aliases.get(i).getFeatureVector();
            styloMatch = compareFeatureVectors(user1featVector, user2featVector);
            float stylo = (float) styloMatch;

            /**
             * calculating time vector for alias
             */
            double timeMatch = 0.0;
            double[] user1timeVector = aliases.get(0).getTimeVector();
            double[] user2timeVector = aliases.get(i).getTimeVector();

            int timeVectorSize = 24;

            double sum1 = 0;
            double sum2 = 0;
            for (int k = 0; k < timeVectorSize; k++) {
                sum1 = sum1 + user1timeVector[k];
                sum2 = sum2 + user2timeVector[k];
            }
            for (int k = 0; k < timeVectorSize; k++) {
                user1timeVector[k] = user1timeVector[k] / sum1;
                user2timeVector[k] = user2timeVector[k] / sum2;
            }

            timeMatch = calculateManhattanTimeVector(user1timeVector, user2timeVector);
            float time = (float) timeMatch;

            tempList.add(user1);
            tempList.add(user2);
            tempList.add(stylo);
            tempList.add(time);

            tempDisplayInfo.add(tempList);
        }
        fuseTimeAndStylo(tempDisplayInfo);
        tempDisplayInfo.clear();
    }

    public void fuseTimeAndStylo(List info) {
        List tempInfo = new ArrayList();
        tempInfo.addAll(info);
        System.out.println("List: ");
        for(Object a : info){
            System.out.println(a);
        }
        List sortedStyloList = new ArrayList();
        List sortedTimeList = new ArrayList();

        sortedStyloList = getsortedStylo(tempInfo);
        sortedTimeList = getsortedTime(tempInfo);
        
        System.out.println("Stylo List");
        for(Object a : sortedStyloList){
            System.out.println(a);
        }
        System.out.println("Time List");
        for(Object a : sortedTimeList){
            System.out.println(a);
        }

        Iterator<List> itr = tempInfo.iterator();
        while (itr.hasNext()) {
            List secondList = itr.next();

            String tempUser = secondList.get(1).toString();
            int foundStylo = -1;
            int foundTime = -1;

            Iterator<List> styloItr = sortedStyloList.iterator();
            Iterator<List> timeItr = sortedTimeList.iterator();

            int counter = 1;
            while (styloItr.hasNext() && foundStylo == -1) {
                List tempStyloList = styloItr.next();
                String tempStyloUser = tempStyloList.get(1).toString();

                if (tempStyloUser.equals(tempUser)) {
                    foundStylo = counter;
                }
                counter++;
            }

            int timeCounter = 1;
            while (timeItr.hasNext() && foundTime == -1) {
                List tempTimeList = timeItr.next();
                String tempTimeUser = tempTimeList.get(1).toString();

                if (tempTimeUser.equals(tempUser)) {
                    foundTime = timeCounter;
                }
                timeCounter++;
            }
            int fusionValue = 0;
            fusionValue = (foundStylo + foundTime) / 2;
            secondList.add(fusionValue);
        }
        sortListWithFusionValue(sortedStyloList);
    }

    /**
     * this method sort the list according to fusion and sets value to table
     *
     * @param info
     */
    public void sortListWithFusionValue(List info) {
        List tempdisplayInfo = new ArrayList();
        tempdisplayInfo.addAll(info);
        System.out.println("Temp Display Info: ");
        
        for(Object a :  tempdisplayInfo){
            System.out.println(a);
        }

        Collections.sort(tempdisplayInfo, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                Double firstNumber = Double.parseDouble(o1.get(4).toString());
                Double secondNumber = Double.parseDouble(o2.get(4).toString());
                return firstNumber.compareTo(secondNumber);
            }
        });

        createRank(tempdisplayInfo);
        // List firstRow = (List) tempdisplayInfo.get(0);
        //  System.out.println("Similar Row: " + firstRow);
//        tableModel.SetValue(tempdisplayInfo); // sending data to display in test table
    }

    /**
     * find similar user within the list
     */
    public void createRank(List tempdisplayInfo) {
        int infoSize = tempdisplayInfo.size();
        System.out.println("Sorted with Fusion");
        for(Object a : tempdisplayInfo){
            System.out.println(a);
        }
        int index = 0;

        for (int i = 0; i < infoSize; i++) {
            String User = tempdisplayInfo.get(i).toString();
            String[] strArray = User.split(",");
            String strfirstUser = strArray[0].substring(1, strArray[0].length()).trim();
            String strsecondUser = strArray[1].trim();
            System.out.println("Similar Users: " + strfirstUser + " with " + strsecondUser);
            int user1 = Integer.parseInt(strfirstUser);
            int user2 = Integer.parseInt(strsecondUser);

            if (user1 == user2) {
                rank.add(i + 1);
                index = i + 1;
                System.out.println("Matched At: " + (i + 1));
                addIndexUser(index, user1);
                break;
            }
        }
        frequencyCount(index);
        System.out.println("\nResult: " + userResult);
        
        for (Map.Entry<Integer, HashSet<Integer>> MapEntry : userResult.entrySet()) {
            Integer tempRank = MapEntry.getKey();
            if (tempRank.equals(1)) {
                HashSet users = MapEntry.getValue();
                int userSize = users.size();
                System.out.println("*********************");
                System.out.println("Top 1: " + userSize);
            }
        }
    }

    /**
     * add user and their rank in hash map
     *
     * @param key
     * @param value
     */
    private void addIndexUser(int key, int value) {
        HashSet tempList = null;
        if (userResult.containsKey(key)) {
            tempList = userResult.get(key);

            if (tempList == null) {
                tempList = new HashSet();
            }
            tempList.add(value);
        } else {
            tempList = new HashSet();
            tempList.add(value);
        }
        userResult.put(key, tempList);
    }

    /**
     * Calculate percentage of top 1 and top 3matched users
     *
     * @param rank
     */
    public void frequencyCount(int rank) {

        if (rank == 1) {
            rankArray[0]++;
        } else if (rank == 2) {
            rankArray[1]++;
        } else if (rank == 3) {
            rankArray[2]++;
        } else {
            rankArray[3]++;
        }

        int totalUser = rankArray[0] + rankArray[1] + rankArray[2] + rankArray[3];
        int top3User = rankArray[0] + rankArray[1] + rankArray[2];
        int others = rankArray[3];

        top1 = (rankArray[0] * 100) / totalUser;
        top3 = (top3User * 100) / totalUser;
        other = others * 100 / totalUser;

        System.out.println("Total Users: " + totalUser);
        System.out.println("Top 1 -> " + top1 + "%");
        System.out.println("Top 3 -> " + top3 + "%");
        System.out.println("Others -> " + other + "%");
    }

    /**
     * Sort the data according to highest match between 2 users and passing the
     * sorted data to table model
     *
     * @param info
     */
    public List getsortedStylo(List Styloinfo) {
        List tempStyloinfo = new ArrayList();
        tempStyloinfo.addAll(Styloinfo);
        // System.out.println("Stylo Info: " + tempStyloinfo);

        Collections.sort(tempStyloinfo, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                Double firstNumber = Double.parseDouble(o1.get(2).toString());
                Double secondNumber = Double.parseDouble(o2.get(2).toString());
                return firstNumber.compareTo(secondNumber);
            }
        });

        Collections.reverse(tempStyloinfo);
        return tempStyloinfo;
    }

    /**
     * sort list of time
     *
     * @param Timeinfo
     * @return
     */
    public List getsortedTime(List Timeinfo) {
        List tempTimeinfo = new ArrayList();
        tempTimeinfo.addAll(Timeinfo);
        //System.out.println("Time list: " + tempTimeinfo);

        Collections.sort(tempTimeinfo, new Comparator<List>() {
            @Override
            public int compare(List o1, List o2) {
                Double firstNumber = Double.parseDouble(o1.get(3).toString());
                Double secondNumber = Double.parseDouble(o2.get(3).toString());
                return firstNumber.compareTo(secondNumber);
            }
        });
        //System.out.println("After sorting: " + tempTimeinfo);
        return tempTimeinfo;
    }

    /**
     * Calculate Manhattan distance between two users
     *
     * @param sequence1
     * @param sequence2
     * @return
     */
    public static double calculateManhattanTimeVector(double[] sequence1, double[] sequence2) {

        double manhattanDistance = 0.0;
        for (int i = 0; i < sequence1.length; i++) {
            double firstElementsequence1 = sequence1[i];
            double firstElementsequence2 = sequence2[i];
            manhattanDistance = manhattanDistance + Math.abs(firstElementsequence2 - firstElementsequence1);
        }
        return manhattanDistance;
    }

    /**
     * Calculate Euclidean distance between two users
     *
     * @param sequence1
     * @param sequence2
     * @return
     */
    public static double calculateTimeVector(double[] sequence1, double[] sequence2) {

        double sum = 0.0;
        for (int i = 0; i < sequence1.length; i++) {
            double firstElementsequence1 = sequence1[i];
            double firstElementsequence2 = sequence2[i];
            sum = sum + Math.pow(firstElementsequence2 - firstElementsequence1, 2);
        }
        return Math.sqrt(sum);
    }

    /**
     * Find the index of the alias that is most similar to the selected alias.
     *
     * @param index
     * @return
     */
    public int findBestMatch(int index) {
        double highestSimilarity = -10.0;
        int indexMostSimilar = 0;

        for (int i = 0; i < aliases.size(); i++) {
            if (i != index) {
                double sim = compareFeatureVectors(aliases.get(i).getFeatureVector(),
                        aliases.get(index).getFeatureVector());

                if (sim > highestSimilarity) {
                    highestSimilarity = sim;
                    indexMostSimilar = i;
                }
            }
        }
        return indexMostSimilar;
    }

    public List<Float> getJ(int j) {
        List<Float> a = featVectorForAllAliases.get(j);
        return a;
    }

    public List<Float> getAliase(List<List<Float>> tmp, int index) {
        if (tmp.size() <= index) {
            return null;
        }
        return tmp.get(index);
    }

    /**
     * Standardize/normalize the feature vectors for all aliases. Aim is mean 0
     * and variance 1 for each feature vector. Please note that this will result
     * in feature vectors that depend on the feature vectors of the other
     * aliases...
     */
    public void normalizeFeatureVector() {
        int nrOfFeatures = featVectorForAllAliases.get(0).size();
        List<Double> avgs = new ArrayList<Double>(nrOfFeatures);
        List<Double> stds = new ArrayList<Double>(nrOfFeatures);
        // Calculate avg (mean) for each feature
        for (int i = 0; i < nrOfFeatures; i++) {
            double sum = 0.000;
            try {
                for (int j = 0; j < aliases.size(); j++) {
                    if (featVectorForAllAliases.size() <= j) {
                        break;
                    } //List<Float> a = featVectorForAllAliases.get(j);
                    sum += getJ(j).get(i);
                    //System.out.println("Sum: " + sum);
                }
                avgs.add(sum / aliases.size());
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Error: " + e);
            }
        }
        // Calculate std for each feature
        for (int i = 0; i < nrOfFeatures; i++) {
            double avg = avgs.get(i);
            double tmpX = 0.0;

            for (int j = 0; j < aliases.size(); j++) {
                List<Float> tmpval = getAliase(featVectorForAllAliases, j);
                if (tmpval == null) {
                    continue;
                }
                tmpX += (avg - (tmpval.get(i))
                        * (avg - (tmpval.get(i))));
            }
            stds.add(Math.sqrt(tmpX / aliases.size()));


        }
        // Do the standardization of the feature vectors
        for (int i = 0; i < nrOfFeatures; i++) {
            for (int j = 0; j < aliases.size(); j++) {
                if (stds.get(i) == 0f) {
                    aliases.get(j).setFeatureValue(i, 0.0f);
                } else {
                    if (featVectorForAllAliases.size() <= j) {
                        break;
                    }
                    aliases.get(j).setFeatureValue(i, (float) ((getJ(j).get(i) - avgs.get(i)) / stds.get(i)));
//                    aliases.get(j).setTimeVector(userList);
                }
            }
        }
    }
}
