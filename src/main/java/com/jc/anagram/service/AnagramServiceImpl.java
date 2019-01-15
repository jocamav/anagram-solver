package com.jc.anagram.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the algorithm to get all the anagrams of a phrase based on a Divide and conquer algorithm.
 * - Base condition: If a sentence can't be split in two words at least, the anagrams are the anagrams of the word
 * - Otherwise: Get the anagrams of all the permutation of the phrase, moving one character to the left and calculating
 * all the combinations. If any of the two words doesn't have anagrams, don't continue
 * <p>
 * For example: 'Aschheim' is changed to 'acehhims'. Then the word is split as follows:
 * - <a, cehhims>
 * - <c, aehhims>
 * - <e, achhims>
 * - <h, acehims>
 * - <h, acehims> (This iteration will be skipped for improve the performance)
 * - <i, acehhms>
 * - <m, acehhis>
 * - <s, acehhim>
 * <p>
 * Each of this combinations, will be checked again, till we check all the combinations
 * <p>
 * We don't need to go till the end of all the combinations. Because some combinations are checked in another parallel
 * iteration:
 * <p>
 * - <acehh, ims>
 * - <ims, acehh>
 * <p>
 * Have the same anagrams. So we skip one of the iterations.
 * <p>
 * For performance reasons, there's a local HashMap which contain the anagrams already calculated. If we find we already
 * calculated the anagrams for one word, we take the value already calculated. As improvement, this map could be stored
 * in a Database instead and use a cache.
 *
 * @author Jose Carlos Martinez
 */
@Service
public class AnagramServiceImpl implements AnagramService {

    private static final Logger log = LoggerFactory.getLogger(AnagramServiceImpl.class);

    @Autowired
    DictionaryService dictionaryService;

    @Autowired
    private UtilsService utilsService;

    public Set<String> getAllAnagramsPhrases(String word) {
        log.info(String.format("Getting all anagrams phrases of %s", word));
        String wordToSearch = getWordWithoutSpecialCharactersAndSpacesAndSorted(word);
        HashMap<String, HashSet<String>> combinationsChecked = new HashMap<>();
        return getAllAnagramsPhrasesWithoutSpecialChars(wordToSearch, combinationsChecked);
    }

    private String getWordWithoutSpecialCharactersAndSpacesAndSorted(String word) {
        String wordClean = getWordWithoutSpecialCharactersAndSpaces(word);
        String wordCleanAndSorted = utilsService.getSortedChars(wordClean);
        return wordCleanAndSorted;
    }

    private String getWordWithoutSpecialCharactersAndSpaces(String word) {
        return word.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    private HashSet<String> getAllAnagramsPhrasesWithoutSpecialChars(String word, HashMap<String, HashSet<String>> combinationsChecked) {
        HashSet<String> phraseAnagrams = combinationsChecked.get(word);
        if (phraseAnagrams == null) {
            phraseAnagrams = dictionaryService.getAnagrams(word);
            addAllAnagramsRecursiveToList(phraseAnagrams, "", word, combinationsChecked);
            saveCalculatedAnagramsInLocalHashSet(word, phraseAnagrams, combinationsChecked);
        }
        return phraseAnagrams;
    }

    private void saveCalculatedAnagramsInLocalHashSet(String word, HashSet<String> anagrams, HashMap<String, HashSet<String>> combinationsChecked) {
        String wordKey = utilsService.getSortedChars(word);
        combinationsChecked.put(wordKey, anagrams);
    }

    private void addAllAnagramsRecursiveToList(Set<String> list, String wordLeft, String wordRight, HashMap<String, HashSet<String>> combinationsChecked) {
        if (isPossibleToPermuteWords(wordLeft, wordRight)) {
            addAllAnagramsFromWordsAndSwapToList(list, wordLeft, wordRight, combinationsChecked);
        }
    }

    private boolean isPossibleToPermuteWords(String wordsLeft, String wordsRight) {
        return wordsLeft.length() > 0 || wordsRight.length() >= (DictionaryService.MIN_LETTERS * 2);
    }

    private void addAllAnagramsFromWordsAndSwapToList(Set<String> list, String wordLeft, String wordRight, HashMap<String, HashSet<String>> combinationsChecked) {
        addAllPossibleAnagramsPhrasesToList(list, wordLeft, wordRight, combinationsChecked);
        if (shouldContinueSwapping(wordLeft, wordRight)) {
            addAnagramsFromSwappingOfWordsToList(list, wordLeft, wordRight, combinationsChecked);
        }
    }


    private void addAllPossibleAnagramsPhrasesToList(Set<String> list, String wordLeft, String wordRight, HashMap<String, HashSet<String>> combinationsChecked) {
        if (canBeAWord(wordLeft)) {
            Set<String> anagramsLeftWord = dictionaryService.getAnagrams(wordLeft);
            if (anagramsLeftWord.size() > 0) {
                Set<String> anagramsRightWord = getAllAnagramsPhrasesWithoutSpecialChars(wordRight, combinationsChecked);
                mergeAnagramsAndAllToList(list, anagramsLeftWord, anagramsRightWord);
            }
        }
    }


    private void mergeAnagramsAndAllToList(Set<String> list, Set<String> anagramsLeft, Set<String> anagramsRight) {
        if (anagramsRight.size() > 0) {
            //Create phrases
            for (String wordsLeft : anagramsLeft) {
                for (String wordsRight : anagramsRight) {
                    String string = wordsLeft + " " + wordsRight;
                    String[] allAnagrams = string.split(" ");
                    Arrays.sort(allAnagrams);
                    string = allAnagrams[0];
                    for (int i = 1; i < allAnagrams.length; i++) {
                        string += " " + allAnagrams[i];
                    }
                    list.add(string);
                }
            }
        }
    }

    /**
     * This check improve the performance of the recursive algorithm. The recursive algorithm is based and the swapping
     * of words. But it's not needed to check all the combinations, because some of them could be duplicated.
     * <p>
     * e.g: 'secrets bet' will be checked as well as 'bet secrets'
     * <p>
     * For avoid this, we give preference to the "left" word (less iterations).
     * The algorithm continue if any of these conditions:
     * - Left word can't be a word
     * - If the right word has 1 character more than the min size of a word
     *
     * @param wordLeft  Left word of the iterations
     * @param wordRight Right word of the iterations
     * @return true if the algorithm must continue; false if not;
     */
    private boolean shouldContinueSwapping(String wordLeft, String wordRight) {
        return (!canBeAWord(wordLeft)) || (wordRight.length() > (DictionaryService.MIN_LETTERS + 1));
    }

    private boolean canBeAWord(String word) {
        return dictionaryService.canBeAWord(word);
    }

    private void addAnagramsFromSwappingOfWordsToList(Set<String> list, String wordLeft, String wordRight, HashMap<String, HashSet<String>> combinationsChecked) {
        String nextLeftWord;
        String nextRightWord;
        int wordRightLength = wordRight.length();
        for (int i = 0; i < wordRightLength; i++) {
            if (!wordJustChecked(i, wordRight)) {
                nextLeftWord = wordLeft + wordRight.charAt(i);
                nextRightWord = wordRight.substring(0, i) + wordRight.substring(i + 1, wordRightLength);
                addAllAnagramsRecursiveToList(list, nextLeftWord, nextRightWord, combinationsChecked);
            }
        }
    }

    private boolean wordJustChecked(int i, String wordRight) {
        return (i > 0 && (wordRight.charAt(i) == wordRight.charAt(i - 1)));
    }


    public Set<String> getAllAnagramsWords(String word) {
        log.info(String.format("Getting anagrams of %s", word));
        String wordToSearch = getWordWithoutSpecialCharactersAndSpaces(word);
        return dictionaryService.getAnagrams(wordToSearch);
    }


}
