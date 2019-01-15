package com.jc.anagram;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jc.anagram.service.AnagramService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnagramServiceTest {

    private static final Logger log = LoggerFactory.getLogger(AnagramServiceTest.class);

    @Autowired
    private AnagramService anagramService;

    @Test
    public void getListOfWordAnagramsTest() {
        String word = "best";
        Set<String> anagrams = anagramService.getAllAnagramsWords(word);
        String[] validAnagrams = {
                "bets",
                "best"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    @Test
    public void getListOfAnagramPhrasesBestSecretTest() {
        String word = "Best Secret";
        Set<String> anagrams = anagramService.getAllAnagramsPhrases(word);
        logAnagrams(word, anagrams);
        String[] validAnagrams = {
                "beet crests",
                "beets crest",
                "beret sects",
                "berets sect",
                "beset crest",
                "best erects",
                "best secret",
                "bests crete",
                "bests erect",
                "bet erst sec",
                "bet rest sec",
                "bet secrets",
                "bets erects",
                "bets secret",
                "better cess",
                "betters sec"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    @Test
    public void getListOfAnagramPhrasesSecretsTest() {
        String word = "Secrets";
        Set<String> anagrams = anagramService.getAllAnagramsPhrases(word);
        logAnagrams(word, anagrams);
        String[] validAnagrams = {
                "erst sec",
                "rest sec",
                "secrets"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    @Test
    public void getListOfAnagramPhrasesAschheimTest() {
        String word = "Aschheim";
        Set<String> anagrams = anagramService.getAllAnagramsPhrases(word);
        logAnagrams(word, anagrams);
        String[] validAnagrams = {
                "aches him",
                "ash chime",
                "chase him",
                "chime has",
                "hash mice",
                "hic shame",
                "mice shah"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    @Test
    public void getListOfAnagramPhrasesItCrowdTest() {
        String word = "IT-Crowd";
        Set<String> anagrams = anagramService.getAllAnagramsPhrases(word);
        logAnagrams(word, anagrams);
        String[] validAnagrams = {
                "cod writ",
                "cord wit",
                "cow dirt",
                "doc writ",
                "tic word"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    @Test
    public void getListOfAnagramPhrasesSetSetSetSetTest() {
        String word = "Set set set set";
        Set<String> anagrams = anagramService.getAllAnagramsPhrases(word);
        logAnagrams(word, anagrams);
        String[] validAnagrams = {
                "sees stet stet",
                "sees test test",
                "sets tees test",
                "settees tests",
                "set setts tees",
                "set set tsetse",
                "est set set set",
                "see setts test",
                "set set testes",
                "see setts stet",
                "est tees tests",
                "set set set set",
                "est est est est",
                "sees stet test",
                "est setts tees",
                "est est testes",
                "est est tsetse",
                "sets tee tests",
                "tsetse tsetse",
                "sets stet tees",
                "see stet tests",
                "est set tsetse",
                "see test tests",
                "est set testes",
                "est est set set",
                "sets setts tee",
                "testes tsetse",
                "settees setts",
                "testes testes",
                "set tees tests",
                "est est est set"
        };
        checkAnagramsFromList(anagrams, validAnagrams);
    }

    private void logAnagrams(String word, Collection<String> anagrams) {
        log.info(String.format("Anagrams for <%s>", word));
        for (String anagram : anagrams) {
            log.info(String.format("Anagram <%s>", anagram));
        }
    }


    private void checkAnagramsFromList(Collection<String> collection, String[] array) {
        for (int i = 0; i < array.length; i++) {
            boolean found = false;
            for (String anagram : collection) {
                if (anagram.equals(array[i])) {
                    found = true;
                    break;
                }
            }
            assertTrue(String.format("Anagram <%s> should be in the list", array[i]), found);
        }
        assertTrue("Size of list should be the same", collection.size() == array.length);
    }
}
