package com.jc.anagram.dictionary;

public class Anagram {
    String value;
    Anagram nextAnagram;

    public Anagram(String value) {
        this.value = value;
    }

    public Anagram(String value, Anagram nextAnagram) {
        this.value = value;
        this.nextAnagram = nextAnagram;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Anagram getNextAnagram() {
        return nextAnagram;
    }

    public void setNextAnagram(Anagram nextAnagram) {
        this.nextAnagram = nextAnagram;
    }
}
