package com.jc.anagram.service;

import java.util.Arrays;

import org.springframework.stereotype.Service;

@Service
public class UtilsServiceImpl implements UtilsService{

	public String getSortedChars(String word) {
        char[] sortedChars = getSortedWordAsCharArray(word);
        return new String(sortedChars);
	}
	
	private char[] getSortedWordAsCharArray(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return chars;
	}

}
