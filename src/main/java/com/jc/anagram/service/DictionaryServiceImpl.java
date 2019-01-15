package com.jc.anagram.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.jc.anagram.dictionary.Anagram;

/**
 * This class implements the interface DictionaryService. It loads the dictionary file in memory as a Map.
 * When the Service bean is created, it loads the static Map with all the words of the Dictionary.
 *
 * We use a HashMap to get the anagrams with O(1).
 *
 * We consider one word is anagram of another one if both words sorted are the same:
 * 	- bets -> bets
 * 	- best -> bets
 *
 * The Key of the HashMap for an Anagram is the word sorted. So to look for the anagram of one word:
 *
 * 	1) Sort the word
 * 	2) Look into the HashMap
 *
 * @author Jose Carlos Martinez
 *
 */
@Service
public class DictionaryServiceImpl implements DictionaryService{

    private static final Logger log = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    private UtilsService utilsService;

    private static Map<String, Anagram> anagrams;

	public DictionaryServiceImpl(@Autowired UtilsService utilsService, @Autowired Environment environment) {
		this.utilsService = utilsService;
		String dictionaryFileLocation = environment.getProperty("dictionary.file");
		loadDictionaryFromFile(dictionaryFileLocation);
	}

	private void loadDictionaryFromFile(String fileLocation) {
        BufferedReader bufferedReader = getBufferedDictionary(fileLocation);
        loadWordsFromBufferedReader(bufferedReader);
        log.info("Dictionary Loaded!!");
	}
	
	private BufferedReader getBufferedDictionary(String fileLocation) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileLocation);
        return new BufferedReader(new InputStreamReader(inputStream));
	}
	
	private void loadWordsFromBufferedReader(BufferedReader bufferedReader) {
        anagrams = new HashMap<>();
		String currentFileLine;
		try {
	        while ((currentFileLine = bufferedReader.readLine()) != null) {
	        		addWordToAnagramDictionary(currentFileLine);
	        }
		}
		catch(IOException e) {
			log.error(String.format("Error loading the file!: %s", e.getMessage()));
			throw new RuntimeException("Couldn't load the file");
		}
	}
	
	private void addWordToAnagramDictionary(String word){
        String anagramKey = getAnagramKey(word);
        addAnagramToDictionary(anagramKey, word);
	}
	
	private String getAnagramKey(String word){
		return utilsService.getSortedChars(word);
	}
	
	private void addAnagramToDictionary(String key, String word) {
        log.info(String.format("Adding word <%s> to Anagram: %s", word, key));
        Anagram anaGramFromDictionary = anagrams.get(key);
        anagrams.put(key, new Anagram(word, anaGramFromDictionary));
	}

	
	public HashSet<String> getAnagrams(String word) {
		if(!canBeAWord(word)){
            return new HashSet<>();
        }
		return getAnagramsFromStaticDictionary(word);
	}

	private HashSet<String> getAnagramsFromStaticDictionary(String word){
		String anagramKey = getAnagramKey(word);
		HashSet<String> anagramsOfWord = new HashSet<>();
		Anagram anagram = anagrams.get(anagramKey);
		while(anagram != null){
			anagramsOfWord.add(anagram.getValue());
			anagram = anagram.getNextAnagram();
		}
		return anagramsOfWord;
	}
	
	public boolean canBeAWord(String word) {
		return word.length() >= MIN_LETTERS;
	}


}
