/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.post.stylometric;

import com.post.parser.model.Alias;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * This is some code for doing stylometric matching of aliases based on posts 
 * (such as discussion board messages).
 * Features: letters (26), digits (10), punctuation (11), function words (293), 
 * word length (20), sentence length (6).
 * Except for freq. of sentence lengths, this is a subset of the features used 
 * in Narayanan et al. (On the Feasibility of Internet-Scale Author Identification)
 * 
 * Some problems to consider: 
 * The more features, the more "sparse" the feature vectors will be (many zeros) 
 * in case of few posts --> similar feature vectors due to a majority of zeros
 * 
 * Since all features are not of the same "dimension", it makes sense to 
 * normalize/standardize the features to have mean 0 and variance 1, as in 
 * Narayanan et al.
 * The above standardization works when finding the best matching candidate, 
 * but may be problematic since the "similarity" between two aliases
 * will depend on the features of other aliases (since the standardization works 
 * column/(feature)-wise).
 * 
 * If we do not use normalization/standardization, we cannot use feature which 
 * are not frequencies, since the features with large magnitudes otherwise will 
 * dominate completely!!!
 * Even if we do only use frequencies, the results without normalization seems 
 * poor (good with normalization)
 * Try to improve the unnormalized version before using it on real problems...
 * 
 * Observe that the obtained similarity values cannot be used directly as a 
 * measure of the "match percentage"!
 * 
 *  
 * @author frejoh
 *
 */

public class StylometricMatching {
	private Set<String> functionWords;			// Contains the function words we are using
	private static String path = "C:/Users/ITE/Documents/NetBeansProjects/BoardAliasMatching/src/Utilities/function_words.txt"; 	// TODO: Change to the correct path;
	private List<Alias> aliases;				// The aliases we are interested in to compare
        // What List<List<Float>> is this?? what does it hold??
        //feature vector is a list of float and this is the list of feature vector so it is a list of list of float
        // So this is a property of a user?? NO
        // So one user has one featVectorForAllAliases ?NO ... name heera na...feat vector for all alieases
        // OKOK
        // so this is for all the list of users YE?S
        // so that a user has List<Float> this value as featVectorforsingleAliases; OKs
        // And each user have their own?? yes...each has one feature vector
        // this file is in different package and different project
        // OK than first of all have you read the second activity cluster?? 
        //YES
        // and I have kept them in a Li
        // Ok than we will have to create a user class here like that in provius project but without the time posts vector
        // Let me do that
        // time post pani rakha...coz we need that too for time vector calculation
        // ook ok lets go the proviuos project first
        
	private List<List<Float>> featVectorForAllAliases;
        private User user;
        //vayo sirji
        // Don't you need other classes?? I need IOReadWite class,, I will be passing users from that class

	public StylometricMatching(){
           ////
            // userid pathau yaha// aba tyo user ko object banaune yaha
            // Tyo ta maile bujhe
		functionWords = new LinkedHashSet<String>();
		loadFunctionWords();
		aliases = new ArrayList<Alias>();
	}


	/**
	 * Extract words from text string, remove punctuation etc.
	 * @param text
	 * @return
	 */
	public static List<String> extractWords(String text){
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
	public void loadFunctionWords(){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(path));

			String strLine;
			while((strLine = br.readLine())!=null)
				functionWords.add(strLine);
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create a list containing the number of occurrences of the various 
         * function words in the post (list of extracted words)
	 * @param words
	 * @return
	 */
	public ArrayList<Float> countFunctionWords(List<String> words){
		ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(functionWords.size(), 0.0f));	// Initialize to zero
		for (int i = 0; i < words.size(); i++){
			if(functionWords.contains(words.get(i))){
				float value = (Float) tmpCounter.get(i);
				value++;
				tmpCounter.set(i, value);
			}
		}
		// "Normalize" the values by dividing with length of the post (nr of words in the post)
		for (int i = 0; i < tmpCounter.size(); i++){
			tmpCounter.set(i, tmpCounter.get(i)/(float)words.size());
		}
		return tmpCounter;
	}

	/**
	 * Create a list containing the number of occurrences of letters a to z in the text
	 * @param post
	 * @return
	 */
	public ArrayList<Float> countCharactersAZ(String post){
		post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
		char[] ch = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(ch.length, 0.0f));
		for(int i = 0; i < ch.length; i++){
			int value = countOccurrences(post, ch[i]);
			tmpCounter.set(i, (float)value);
		}
		// "Normalize" the values by dividing with total nr of characters in the post (excluding white spaces)
		int length = post.replaceAll(" ", "").length();
		for (int i = 0; i < tmpCounter.size(); i++){
			tmpCounter.set(i, tmpCounter.get(i)/(float)length);
		}
		return tmpCounter;
	}

	/**
	 * Create a list containing the number of special characters in the text
	 * @param post
	 * @return
	 */
	public ArrayList<Float> countSpecialCharacters(String post){
		post = post.toLowerCase();	// Upper or lower case does not matter, so make all letters lower case first...
		char[] ch = {'0','1','2','3','4','5','6','7','8','9','.', '?', '!', ',', ';', ':', '(', ')', '"', '-', '´'};
		ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(ch.length, 0.0f));
		for(int i = 0; i < ch.length; i++){
			int value = countOccurrences(post, ch[i]);
			tmpCounter.set(i, (float)value);
		}
		// "Normalize" the values by dividing with total nr of characters in the post (excluding whitespaces)
		int length = post.replaceAll(" ", "").length();
		for (int i = 0; i < tmpCounter.size(); i++){
			tmpCounter.set(i, tmpCounter.get(i)/(float)length);
		}
		return tmpCounter;
	}

	/**
	 * Counts the frequency of various word lengths in the list of words.
	 * @param words
	 * @return
	 */
	public ArrayList<Float> countWordLengths(List<String> words){
		ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(20, 0.0f));	// Where 20 corresponds to the number of word lengths of interest 
		int wordLength = 0;
		for(String word: words){
			wordLength = word.length();
			// We only care about wordLengths in the interval 1-20
			if(wordLength > 0 && wordLength <= 20){
				float value = (Float) tmpCounter.get(wordLength-1);	// Observe that we use wordLength-1 as index!
				value++;
				tmpCounter.set(wordLength-1, value);
			}		
		}
		// "Normalize" the values by dividing with length of the post (nr of words in the post)
		for (int i = 0; i < tmpCounter.size(); i++){
			tmpCounter.set(i, tmpCounter.get(i)/(float)words.size());
		}
		return tmpCounter;
	}

	/**
	 * Counts the frequency of various sentence lengths in the post.
	 * @param post
	 * @return
	 */
	public ArrayList<Float> countSentenceLengths(String post){
		ArrayList<Float> tmpCounter = new ArrayList<Float>(Collections.nCopies(6, 0.0f));	// Where 6 corresponds to the number of sentence lengths of interest
		// Split the post into a number of sentences
		List<String> sentences = splitIntoSentences(post);
		int nrOfWords = 0;
		for(String sentence: sentences){
			// Get number of words in the sentence
			List<String> words = extractWords(sentence);
			nrOfWords = words.size();
			if(nrOfWords > 0 && nrOfWords <= 10)
				tmpCounter.set(0, tmpCounter.get(0)+1);
			else if(nrOfWords <= 20)
				tmpCounter.set(1, tmpCounter.get(1)+1);
			else if(nrOfWords <= 30)
				tmpCounter.set(2, tmpCounter.get(2)+1);
			else if(nrOfWords <= 40)
				tmpCounter.set(3, tmpCounter.get(3)+1);
			else if(nrOfWords <= 50)
				tmpCounter.set(4, tmpCounter.get(4)+1);
			else if(nrOfWords >= 51)
				tmpCounter.set(5, tmpCounter.get(5)+1);				
		}
		// "Normalize" the values by dividing with nr of sentences in the post
		for (int i = 0; i < tmpCounter.size(); i++){
			tmpCounter.set(i, tmpCounter.get(i)/(float)sentences.size());
		}
		return tmpCounter;
	}

	/**
	 * Splits a post/text into a number of sentences
	 * @param text
	 * @return
	 */
	public List<String> splitIntoSentences(String text)
	{
		List<String> sentences = new ArrayList<String>();
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
		iterator.setText(text);
		int start = iterator.first();
		for (int end = iterator.next();	end != BreakIterator.DONE;start = end, end = iterator.next()) {
			sentences.add(text.substring(start,end));
		}
		return sentences;
	}

	/**
	 * Count the number of occurrences of certain character in a String
	 * @param haystack
	 * @param needle
	 * @return
	 */
	public static int countOccurrences(String haystack, char needle)
	{
		int count = 0;
		for (int i=0; i < haystack.length(); i++)
		{
			if (haystack.charAt(i) == needle)
			{
				count++;
			}
		}
		return count;
	}


	/**
	 * Loops through all aliases and construct their feature vectors
	 */
       // Tyeso garda sabai function ma change garnu parne huncha.
        // Look at all the funciton he has implmented it all has variables in green color 
        // Which means that are all defined as class varaibles
        // global variable haru ho
        // yes exactly
        // Now when you define golbal varible you never know when and where he has set it and again reset it
        // because it has effect in all the functions
        // bujheu??
        // lamo sirji 
        // featVectorForAllAliases yo 
        // are you getting what i am saying??
        // tyo global vaariable ko use testo dherai chaina
        // Timi tyaha dekhayeu hola tala use bhayeko 
        /// ma bhanchu wait
        // that is for normalizing ..means hami lai harek user ko feature vector calculate garnu parcha
        // ani tyo featurevector auta list ma save garera rakhaula ani end ma tyo feature vector ley
        //harek individual user ko feature vector lai divide garaula...
        // Tyo ta euta matra variable ho tyesto yo class ma aru variable pani ha 
      // what if i explain the requirement
        //mj requirement nai bhujeko chainau
        // ok than go head and explain
       
	public void createFeatureVectors(){
             // For now lets go with the assumption that we be passing user object which has all the post a
            // and all the detail of that user.
            // what is this featVectorForAllAliases? can you explain?
            // hami harek user ko feature Vector calcualte garchau...
            // ani tyo chai saabai user ko feature vector ko list ho
            // Ok than lets do this way
		featVectorForAllAliases = new ArrayList<List<Float>>();
                // You have to remove this loop
                // Only yhe for loop not its content
                // ekchin hai let me see
		for(Alias alias : aliases){
			int cnt = 0;
			alias.setFeatureVectorPosList(alias.initializeFeatureVectorPostList());
			// Calculate each part of the "feature vector" for each individual post
			for(String post: alias.getPosts()){ 
				List<String> wordsInPost = extractWords(post);			
				alias.addToFeatureVectorPostList(countFunctionWords(wordsInPost), 0, cnt);
				alias.addToFeatureVectorPostList(countWordLengths(wordsInPost), 293, cnt);
				alias.addToFeatureVectorPostList(countCharactersAZ(post), 313, cnt);
				alias.addToFeatureVectorPostList(countSpecialCharacters(post), 339, cnt);
				alias.addToFeatureVectorPostList(countSentenceLengths(post), 359, cnt);
				cnt++;
			}

			ArrayList<ArrayList<Float>> featureVectorList = alias.getFeatureVectorPosList();

			int numberOfPosts = alias.getPosts().size();
			int nrOfFeatures = featureVectorList.get(0).size();
			List<Float> featureVector = new ArrayList<Float>(Collections.nCopies(nrOfFeatures, 0.0f));
			// Now we average over all posts to create a single feature vector for each alias
			for(int i = 0; i < nrOfFeatures; i++){
				float value = 0.0f;
				for(int j = 0; j < numberOfPosts; j++){
					value+=featureVectorList.get(j).get(i);
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
	 * @param featVector1
	 * @param featVector2
	 * @return
	 */
	public double compareFeatureVectors(List<Float> featVector1, List<Float> featVector2){
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
			// result can be > 1 (or -1) due to rounding errors for equal vectors, 
                        //but must be between -1 and 1
			return Math.min(Math.max(result, -1d), 1d);
			//return result;
		} else if (sum1 == 0 && sum2 == 0) {
			return 1d;
		} else {
			return 0d;
		}
	}
	
	/**
	 * Calculate similarity between all pairs of aliases (a lot of comparisons 
         * if there are many aliases)
	 */
	public void compareAllPairsOfAliases(){
		for(int i = 0; i < aliases.size(); i++){
			for(int j = i+1; j < aliases.size(); j++){
				double sim = compareFeatureVectors(aliases.get(i).getFeatureVector(), aliases.get(j).getFeatureVector());
				System.out.println("Similarity between alias " + aliases.get(i).getName() + " and " + aliases.get(j).getName() + " is: " + sim);
			}
		}
	}
	
	/**
	 * Find the index of the alias that is most similar to the selected alias.
	 * @param index
	 * @return
	 */
	public int findBestMatch(int index){
		double highestSimilarity = -10.0;
		int indexMostSimilar = 0;
		for(int i = 0; i < aliases.size(); i++){
			if(i!=index){
				double sim = compareFeatureVectors(aliases.
                                        get(i).getFeatureVector(), 
                                        aliases.get(index).getFeatureVector());
				if(sim > highestSimilarity){
					highestSimilarity = sim;
					indexMostSimilar = i;
				}
			}
		}
		return indexMostSimilar;
	}
	
	/**
	 * Standardize/normalize the feature vectors for all aliases.
	 * Aim is mean 0 and variance 1 for each feature vector.
	 * Please note that this will result in feature vectors that depend on the feature vectors of the other aliases...
	 * TODO: This code has not been double checked, it may be flawed! Double check before use!!!
	 */
	public void normalizeFeatureVector(){
		int nrOfFeatures = featVectorForAllAliases.get(0).size();
		List<Double> avgs = new ArrayList<Double>(nrOfFeatures);
		List<Double> stds = new ArrayList<Double>(nrOfFeatures);

		// Calculate avg (mean) for each feature
		for(int i = 0; i < nrOfFeatures; i++){
			double sum = 0.0;
			for(int j = 0; j < aliases.size(); j++){
				sum+=featVectorForAllAliases.get(j).get(i);
			}
			avgs.add(sum/aliases.size());
		}
		
		// Calculate std for each feature
		for(int i = 0; i < nrOfFeatures; i++){
			double avg = avgs.get(i);
			double tmp = 0.0;
			for(int j = 0; j < aliases.size(); j++){
				tmp += (avg-featVectorForAllAliases.get(j).get(i))*(avg-featVectorForAllAliases.get(j).get(i));
			}
			stds.add(Math.sqrt(tmp/aliases.size()));
		}
		
		// Do the standardization of the feature vectors
		for(int i = 0; i < nrOfFeatures; i++){
			for(int j = 0; j < aliases.size(); j++){
				if(stds.get(i)==0)
					aliases.get(j).setFeatureValue(i, 0.0f);
				else
					aliases.get(j).setFeatureValue(i, (float)((featVectorForAllAliases.get(j).get(i)-avgs.get(i))/stds.get(i)));
			}
		}
		
	}

        //this is the main classare the /// lambu speak
        

	public static void main(String[] args) {
		StylometricMatching test = new StylometricMatching();
		Alias kalle = new Alias("Kalle"); // this 
		kalle.addPost("This is a litte test.");
		kalle.addPost("This is the second little text. I wonder if this will work out okay.");
		test.aliases.add(kalle);
		Alias kolle = new Alias("Ram");
		kolle.addPost("Hi, how are you? This is a test...");
		kolle.addPost("You, have you seen this video? Goooh!");
		test.aliases.add(kolle);
		Alias kolle2 = new Alias("Sita");
		kolle2.addPost("Hi, how area you? This is a test...");
		kolle2.addPost("You, have you seen this video? GooohHoo!");
		test.aliases.add(kolle2);
                // open the feature vect
                // remove the loop from the createFeatureVectors function
		test.createFeatureVectors();

		for(Alias alias : test.aliases){
			List<Float> featVec = alias.getFeatureVector();
			for(float value:featVec)
				System.out.println(value);
			System.out.println();
		}

		test.compareAllPairsOfAliases();
		System.out.println("The best matching alias is: " + test.findBestMatch(0));
		
		JaroWinkler test2 = new JaroWinkler(0.70, 4);
		System.out.println(test2.proximity(test.aliases.get(0).getName(), test.aliases.get(1).getName()));
		System.out.println(test2.proximity(test.aliases.get(0).getName(), test.aliases.get(2).getName()));

	}
        
        // Let this function give you all the user from the file
        // The original use
        // kina chaiyo ni??
        // We need to get all the posts and info the aht user
        // if we have the userID then we can calculate it by 
    //tempfolderName = getFolderName(Integer.valueOf(userID).toString());
    //                        User objUser = ioRW.convertTxtFileToUserObj(IOProperties.INDIVIDUAL_USER_FILE_PATH, tempfolderName,
    //                                Integer.valueOf(userID).toString(), IOProperties.USER_FILE_EXTENSION);
       // Tyo ta miale bujhe tata tyo project ko kaam second ACtivity cluster dine matra ho 
        // End that project there
        // Consider this as new project and start from here
        //kasto mj sirji.....tehi user haru liyera ta tini haru ko Stylometric calculate garne ho....
        // this will be the continuity
        // Ok if you want to do this way.
        // Tyeso bhaye ta kehi pani garo chaina
       // baru yo stylometric file lai tyo project bhitra copy paste garum ...ani kaam suru garau...
        // ok make a different package and copy than..ok
    
        
        // For now lets assume this does the job of calcualting stylo metrics. 
        // So lets call this function first
        
        public void getStyloMetric(List<Set> cluster){
            // This cluster will give you will all the user of all the cluster.
            List<List<Float>> firstCluster = new ArrayList<List<Float>>();
            List<List<Float>> sCluster = new ArrayList<List<Float>>();
            List<List<Float>> tCluster = new ArrayList<List<Float>>();
            List<List<Float>> foCluster = new ArrayList<List<Float>>();
            List<List<Float>> fiCluster = new ArrayList<List<Float>>();
            List<List<Float>> siCluster = new ArrayList<List<Float>>();
            //
            if (clusterNo == 1){
                // For now do this way make your own funciton and do the stuff 
                // But set all the value in aliases class not in user class Yo bhaneko bujheu??
                // YES Exactly, So that we don't have to modify anything inside the class
                // OK
                // OK So where are you stuck??
                // Hold on Don't pass the userID for now
                // Call the funciton of the class which all the value set in aliases that will do the trick 
                // You first set the values and call the funciton with that particular object which has the value.
                createFeatureVectors
                        
                 // For example
                 // Aliases a = new Aliases();
                       // a.set(everthing here); set all the attribute at a
                        // than call a.createFeatureVector();
            }
            if (clusterNo == 2){
                
            }
            if (clusterNo == 3){
                
            }
            if (clusterNo == 4){
                
            }
            if (clusterNo == 5){
                
            }
            if (clusterNo == 6){
                
            }
                
            
        }
        
        public 

}

