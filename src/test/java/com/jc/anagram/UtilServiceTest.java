package com.jc.anagram;

import com.jc.anagram.service.UtilsService;
import com.jc.anagram.service.UtilsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UtilsServiceImpl.class})
public class UtilServiceTest {

    @Autowired
    UtilsService utilsService;

    @Test
    public void sortWordWelcome() {
        String wordSorted = utilsService.getSortedChars("welcome");
        assertEquals("The word should match", "ceelmow", wordSorted);
    }

    @Test(expected = NullPointerException.class)
    public void sortWordNull() {
        utilsService.getSortedChars(null);
    }

}
