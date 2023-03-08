package io.github.rk22000;


import org.junit.jupiter.api.Test;

import static io.github.rk22000.Riot.riotExpression;
import static io.github.rk22000.RiotCollections.chars;
import static io.github.rk22000.RiotQuantifiers.number;
import static io.github.rk22000.RiotQuantifiers.oneOrMore;
import static io.github.rk22000.RiotTokens.*;

class RiotTest {

    @Test
    void create() {
        var riotex =
                Riot.create("Rahul")
                        .and(" Kandekar")
                        .as("myName")
                        .or(
                                Riot.create("Saptrashi")
                                        .wholeTimes(2)
                                        .as("profsName")
                        );
        var expectedResult = "((?<myName>Rahul Kandekar)|(?<profsName>(Saptrashi){2}))";
        assert riotex.toString().equals(expectedResult): riotex + "!=" + expectedResult;
    }

    @Test
    void decimalNumber() {
        /*
        oneOrMore(DIGIT)
            .and(DOT)
            .and(oneOrMore(DIGIT)

        (\\d)+\\.(\\d)+
        4764763.5675
         */
        var riotex = oneOrMore(DIGIT())
                .and(DOT())
                .and(oneOrMore(DIGIT()));
        var expectedResult = "(\\d)+\\.(\\d)+";
        assert riotex.toString().equals(expectedResult): riotex + "!=" +expectedResult;
    }
    @Test
    void phoneNumber() {
        /*
        number(3).times(DIGIT)
            .and("-")
            .times(2)
            .and(number(4).times(DIGIT))
        ((\d){3}-){2}(\d){4}
        111-111-1111
         */
        final String REGEX = "(\\d{3}-){2}\\d{4}";
        var riotex = number(3).times(DIGIT())
                .and("-")
                .wholeTimes(2)
                .and(number(4).times(DIGIT()));
        assert riotex.toString().equals(REGEX): riotex + "!=" + REGEX;

        riotex = DIGIT().times(3)
                .and("-")
                .wholeTimes(2)
                .and(DIGIT().times(4));
        assert riotex.toString().equals(REGEX): riotex + "!=" + REGEX;
    }

    @Test
    void filmsBefore1990() {
        /*
        Match titles of all films produced before 1990.
            1 The Shawshank Redemption (1994)
            2 The Godfather (1972)
            3 The Godfather: Part II (1974)
        oneOrMore(ANY_CHAR())
            .and("1")
            .and(
                charsIn("0-8").and(DIGIT()).and(DIGIT())
                    .or(riotExpression("9").and(charsIn("0-8").and(DIGIT()))
            )
          .+1([0-8]\d\d|9[0-8]\d)
         */
        var riotex = oneOrMore(ANY_CHAR())
                .and("1")
                .and(
                        chars('0').through('8').and(DIGIT()).and(DIGIT())
                                .or(
                                        riotExpression("9").and(chars('0').through('8').and(DIGIT()))
                                )
                )
                .and(ANY_CHAR());
    }
}