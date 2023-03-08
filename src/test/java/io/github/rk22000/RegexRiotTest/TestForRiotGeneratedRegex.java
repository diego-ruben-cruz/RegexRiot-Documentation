package io.github.rk22000.RegexRiotTest;

import io.github.rk22000.RegexRiot.RiotString;
import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.chars;
import static io.github.rk22000.RegexRiot.RiotTokens.*;

class TestForRiotGeneratedRegex {
    private RiotString ritex;
    private String answer;
    private void check() {
        assert answer.equals(ritex.toString()): "Expected: "+answer+"\n" +
                "Actual  : "+ritex;
    }
    @Test
    void nameGroups() {
        ritex = riot("Bugs")
                .and(" Bunny")
                .as("myName")
                .or(
                        riot("Donald")
                                .times(2)
                                .as("profsName")
                );
        answer = "(?<myName>Bugs Bunny)|(?<profsName>(Donald){2})";
        check();
    }
    @Test
    void decimalNumber() {
        ritex = oneOrMore(DIGIT)
                .and(DOT)
                .and(oneOrMore(DIGIT));
        answer = "\\d+\\.\\d+";
        check();
    }
    @Test
    void phoneNumber() {
        ritex = DIGIT.times(3)
                .and("-")
                .wholeTimes(2)
                .and(DIGIT)
                .times(4);
        answer = "(\\d{3}-){2}\\d{4}";
        check();
    }
    @Test
        /*
        Match titles of all films produced before 1990.
            1 The Shawshank Redemption (1994)
            2 The Godfather (1972)
            3 The Godfather: Part II (1974)
         */
    void filmsBefore1990() {
        answer = "^.+\\((19[0-8]\\d|\\d{3}|\\d{2}|\\d{1})\\)";
        ritex = LINE_START
                .and(oneOrMore(ANY_CHAR))
                .and(OPEN_BRACKET)
                .and(
                        riot("19").and(
                                chars('0').through('8')
                        ).and(DIGIT).or(
                                DIGIT.times(3)
                        ).or(
                                DIGIT.times(2)
                        ).or(
                                DIGIT.times(1)
                        )
                ).grouped()
                .and(CLOSE_BRACKET);
        check();
        ritex = LINE_START
                .and(oneOrMore(ANY_CHAR))
                .and(OPEN_BRACKET)
                .and(
                        riot("19").and(
                                chars('0').through('8')
                        ).and(DIGIT).or(
                                DIGIT.times(3)
                        ).or(
                                DIGIT.times(2)
                        ).or(
                                DIGIT.times(1)
                        ).wholeThingGrouped()
                )
                .and(CLOSE_BRACKET);
        check();
    }
}
