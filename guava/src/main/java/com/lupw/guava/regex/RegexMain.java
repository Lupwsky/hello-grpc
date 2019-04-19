package com.lupw.guava.regex;

import lombok.extern.slf4j.Slf4j;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * @author v_pwlu 2019/1/24
 */
@Slf4j
public class RegexMain {

    public static void main(String[] args) {
        VerbalExpression verbalExpression = VerbalExpression.regex()
                .startOfLine()
                .then("http")
                .maybe("s")
                .then("://")
                .maybe("www.")
                .anythingBut(" ")
                .endOfLine()
                .build();
        log.info("[VerbalExpression] regex = {}", verbalExpression.toString());
    }

}
