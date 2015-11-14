import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Payam
 *
 */

public class AnagramFinder {
	private static final String FILE_NAME="/Users/payam.javanmardi/Downloads/wordlist";
	private static final String FILTERES_FILE_NAME="/Users/payam.javanmardi/Downloads/wordlist_filtered";
	private static final String ANAGRAM="poultry outwits ants";
	private static final String MD5_HASH="4624d200580677270a54ccff86b9610e";

	private static final String PATTERN = "[poultrywisan]+";
	
	private static String phrase_found;

	public static void main(String[] args) {
		try {
			System.out.println("========== START ===========");
			long startTime=System.currentTimeMillis();
			List<String> filteredWords=new LinkedList<>();
			clearWordList(filteredWords);
			
			boolean found = findPhrase(filteredWords);
			
			if(found){
				System.out.println("Phrase found =>"+phrase_found);
			}else{
				System.out.println("Sorry, phrase not found");
			}
			
			System.out.println("========== END ===========");
			long endTime=System.currentTimeMillis();
			
			long totalTime = (endTime-startTime)/1000;
			
			System.out.println("Total Time in seconds: "+totalTime);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}

	private static void clearWordList(List<String> filteredWords) throws IOException{
		BufferedReader br=new BufferedReader(new FileReader(new File(FILE_NAME)));

		String line;

		Set<String> words=new TreeSet<>();

		while((line=br.readLine())!=null){

			if(!words.contains(line.trim())){
				words.add(line.trim());
			}
		}

		br.close();

		BufferedWriter bw=new BufferedWriter(new FileWriter(new File(FILTERES_FILE_NAME)));

		int i=0;
		for(String word:words){
			if (word.matches("^"+PATTERN+"$")) {
				if(i!=0)
					bw.write("\n");
				bw.write(word);
				i++;
				filteredWords.add(word);
			}
		}

		bw.close();
	}

	private static String getMD5Hash(String phrase) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest m = MessageDigest.getInstance("MD5");

		byte[] hashedBytes = m.digest(phrase.getBytes("UTF-8"));

		StringBuffer sb = new StringBuffer();

		for (byte b : hashedBytes) {
			sb.append(String.format("%02x", b & 0xff));
		}

		return sb.toString();
	}

	private static boolean findPhrase(List<String> filteredWords) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		String word1;
		String word2;
		String word3;
		
		for(int i=0;i<filteredWords.size()-2;i++){
			System.out.println("--- Iteration "+i);
			word1=filteredWords.get(i);
			for(int j=i+1;j<filteredWords.size()-1;j++){
				word2=filteredWords.get(j);
				for(int k=j+1;k<filteredWords.size();k++){
					word3=filteredWords.get(k);
					
					String phrase1=word1+" "+word2+" "+word3;
					
					if(areAnagrams(phrase1, ANAGRAM) && getMD5Hash(phrase1).equals(MD5_HASH)){
						phrase_found=phrase1;
						return true;
					}
					
					String phrase2=word1+" "+word3+" "+word2;
					
					if(areAnagrams(phrase2, ANAGRAM) && getMD5Hash(phrase2).equals(MD5_HASH)){
						phrase_found=phrase2;
						return true;
					}
					
					String phrase3=word2+" "+word1+" "+word3;
					
					if(areAnagrams(phrase3, ANAGRAM) && getMD5Hash(phrase3).equals(MD5_HASH)){
						phrase_found=phrase3;
						return true;
					}
					
					String phrase4=word2+" "+word3+" "+word1;
					
					if(areAnagrams(phrase4, ANAGRAM) && getMD5Hash(phrase4).equals(MD5_HASH)){
						phrase_found=phrase4;
						return true;
					}
					
					String phrase5=word3+" "+word1+" "+word2;
					
					if(areAnagrams(phrase5, ANAGRAM) && getMD5Hash(phrase5).equals(MD5_HASH)){
						phrase_found=phrase5;
						return true;
					}
					
					String phrase6=word3+" "+word2+" "+word1;
					
					if(areAnagrams(phrase6, ANAGRAM) && getMD5Hash(phrase6).equals(MD5_HASH)){
						phrase_found=phrase6;
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static boolean areAnagrams(String phrase1, String phrase2){
		if(phrase1==null || phrase2==null)
			return false;
		if(phrase1.length()!=phrase2.length())
			return false;

		char[] word1 = phrase1.toCharArray();
		char[] word2 = phrase2.toCharArray();
		Arrays.sort(word1);
		Arrays.sort(word2);
		return Arrays.equals(word1, word2);
	}
}
