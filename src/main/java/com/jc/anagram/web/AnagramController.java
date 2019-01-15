package com.jc.anagram.web;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jc.anagram.service.AnagramService;

@RestController
@RequestMapping("/api/anagrams")
public class AnagramController {
	@Autowired
	AnagramService anagramService;
	
    @RequestMapping(method=RequestMethod.GET)
    public Set<String> greeting(@RequestParam(value="word") String word) {
        return anagramService.getAllAnagramsPhrases(word);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    public String unexpectedError(Exception e) {
      return "This is an unexpected error: " + e.getMessage();
    }
}
