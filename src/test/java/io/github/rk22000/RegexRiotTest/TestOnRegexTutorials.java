package io.github.rk22000.RegexRiotTest;

import io.github.rk22000.RegexRiot.RiotString;
import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotGroupings.group;
import static io.github.rk22000.RegexRiot.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RegexRiot.RiotSet.chars;
import static io.github.rk22000.RegexRiot.RiotSet.riotSet;
import static io.github.rk22000.RegexRiot.RiotTokens.*;

/**
 * This test class is to test if RegexRiot is good enough to generate answers for the exercises on
 * <a href="http://regextutorials.com/index.html">Regex Tutorials</a>
 */
public class TestOnRegexTutorials {
    private RiotString ritex;
    private String answer;
    private void check() {
        assert ritex.toString().equals(answer): "Expected: "+answer+"\n" +
                "Actual:   "+ritex;
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Floating%20point%20numbers">
     *     Floating Point Numbers
     * </a>
     * Match numbers containing floating point. Skip those that don't.
     */
    @Test
    void FloatingPointNumbers() {
        answer = "\\d+\\.\\d+";
        ritex = oneOrMore(DIGIT).and(DOT).and(oneOrMore(DIGIT));
        System.out.println(ritex);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Years%20before%201990">
     *     Years before 1990
     * </a>
     * Match titles of all films produced before 1990.
     * <p/>
     * 1 The Shawshank Redemption (1994)
     * 2 The Godfather (1972)
     * 3 The Godfather: Part II (1974)
     * 4 Pulp Fiction (1994)
     * 5 The Good, the Bad and the Ugly (1966)
     * 6 The Dark Knight (2008)
     * 7 12 Angry Men (1957)
     * 8 Schindler's List (1993)
     * 9 The Lord of the Rings: The Return of the King (2003)
     * 10 Fight Club (1999)
     */
    @Test
    void filmsBefore1990() {
        answer = "^.+\\((19[0-8]\\d|\\d{3}|\\d{2}|\\d)\\)";
        ritex = LINE_START.and(oneOrMore(ANY_CHAR))
                .and(OPEN_BRACKET)
                .and(
                        riot("19").and(
                                chars('0').through('8')
                        ).and(DIGIT).or(
                                DIGIT.times(3)
                        ).or(
                                DIGIT.times(2)
                        ).or(
                                DIGIT
                        ).wholeThingGrouped()
                )
                .and(CLOSE_BRACKET);
        System.out.println(ritex);
        check();
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Hexadecimal%20colors">
     *     Hexadecimal colors
     * </a>
     * Match 24-bit hexadecimal colors. Skip 12 bit colors.
     * <p/>
     * 24 bit:
     * AliceBlue #F0F8FF
     * AntiqueWhite #FAEBD7
     * Aqua #00FFFF
     * Aquamarine #7FFFD4
     * Azure #F0FFFF
     * 12 bit:
     * White #FFF
     * Red #F00
     * Green #0F0
     * Blue #00F
     */
    @Test
    void hexadecimalColors() {
        ritex = riot("#")
                .and(
                        DIGIT.or(
                                chars('A').through('F').toRiotString()
                        )
                ).times(6);
        // TODO: Default grouping should be grouping without remembering like #(?:\d|[A-F]){6} instead of #(\d|[A-F]){6}
        System.out.println(ritex);
    }

    /**
     * <a href="http://regextutorials.com/excercise.html?Grayscale%20colors">
     *     Grayscale colors
     * </a>
     * In this exercise you need to match 12 and 24 bit colors whose red, green and blue components are equal.
     * Colors start with a '#'.
     * <p/>
     * 24 bit:
     * Alice Blue #F0F8FF
     * Black #000000
     * Antique White #FAEBD7
     * Dark Grey #a9a9a9
     * Aqua #00FFFF
     * Azure #F0FFFF
     * Battleship grey #848484
     * 12 bit:
     * White #FFF
     * Red #F00
     * Green #0F0
     * Black #000
     *
     */
    @Test
    void greyScaleColors() {
        answer = "#((\\d|[A-F]|[a-f]){1,2})\\1{2}";
        ritex = riot("#")
                .and(
                        DIGIT.or(
                                chars('A').through('F').toRiotString()
                        ).or(
                                chars('a').through('f').toRiotString()
                        )
                ).times(1, 2)
                .grouped()
                .and(group(1))
                .times(2);
        System.out.println(ritex);
        check();
        answer = "#([\\dA-Fa-f]{1,2})\\1{2}";
        ritex = riot("#")
                .and(
                        riotSet(DIGIT).union(
                                chars('A').through('F')
                        ).union(
                                chars('a').through('f')
                        )
                ).times(1, 2)
                .grouped()
                .and(group(1))
                .times(2);
        System.out.println(ritex);
        check();
    }

}

