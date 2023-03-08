package io.github.rk22000.RegexRiot;

import org.junit.jupiter.api.Test;

import static io.github.rk22000.RegexRiot.Riot.riot;
import static io.github.rk22000.RegexRiot.RiotTokens.DIGIT;
import static io.github.rk22000.RegexRiot.RiotTokens.WORD_CHAR;
import static io.github.rk22000.RegexRiot.SimpleRiotTokens.DIGIT;

class RiotStringTest {
    RiotString riotex;
    String result;
    void check() {
        assert result.equals(riotex.toString()): "EXPECTED: "+result+"\nACTUAL:   "+riotex;
    }

    @Test
    void tokens() {
        riotex = DIGIT;
        result = "\\d";
        check();
        riotex = WORD_CHAR;
        result = "\\w";
        check();
    }
    @Test
    void and() {
        riotex = DIGIT.and(WORD_CHAR);
        System.out.println(riotex);
        result = "\\d\\w";
        check();
    }

    @Test
    void or() {
        riotex = DIGIT.or(WORD_CHAR);
        System.out.println(riotex);
        result = "(\\d|\\w)";
        check();
    }

    @Test
    void wholeTimes() {
        riotex = DIGIT.and(WORD_CHAR).wholeTimes(3);
        System.out.println(riotex);
        result = "(\\d\\w){3}";
        check();

        riotex = riot(DIGIT()).and("HelloWorld").wholeTimes(4);
        System.out.println(riotex);
        result = "(\\dHelloWorld){4}";
        check();
    }

    @Test
    void wholeThingOptional() {
        riotex = DIGIT.and(WORD_CHAR).wholeThingOptional();
        System.out.println(riotex);
        result = "(\\d\\w)?";
        check();
    }

    @Test
    void wholeThingGrouped() {
        riotex = DIGIT.and(WORD_CHAR).wholeThingGrouped();
        System.out.println(riotex);
        result = "(\\d\\w)";
        check();
    }

    @Test
    void wholeThingGroupedAs() {
        riotex = DIGIT.and(WORD_CHAR).wholeThingGroupedAs("digitNword");
        System.out.println(riotex);
        result = "(?<digitNword>\\d\\w)";
        check();
    }

    @Test
    void times() {
        riotex = DIGIT.and(WORD_CHAR).times(3);
        System.out.println(riotex);
        result = "\\d\\w{3}";
        check();

        riotex = riot(DIGIT()).and("HelloWorld").times(4);
        System.out.println(riotex);
        result = "\\d(HelloWorld){4}";
        check();
    }

    @Test
    void optionally() {
        riotex = DIGIT.and(WORD_CHAR).optionally();
        System.out.println(riotex);
        result = "\\d\\w?";
        check();
    }

    @Test
    void grouped() {
        riotex = DIGIT.and(WORD_CHAR).grouped();
        System.out.println(riotex);
        result = "\\d(\\w)";
        check();
    }

    @Test
    void groupedAs() {
        riotex = DIGIT.and(WORD_CHAR).groupedAs("digitNword");
        System.out.println(riotex);
        result = "\\d(?<digitNword>\\w)";
        check();
    }
}