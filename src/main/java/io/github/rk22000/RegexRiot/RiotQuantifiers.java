package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotQuantifiers {
    static RiotString oneOrNone(RiotString ritex) {
        return ritex.wholeThingOptional();
    }
    static RiotString oneOrNone(String expression) {
        return oneOrNone(riot(expression));
    }
    static RiotString zeroOrMore(RiotString ritex) {
        if (ritex.isNotUnitChain()) ritex = ritex.wholeThingGrouped();
        return ritex.and("*");
    }
    static RiotString zeroOrMore(String expression) {
        return zeroOrMore(riot(expression));
    }
    static RiotString oneOrMore(RiotString ritex) {
        if (ritex.isNotUnitChain()) ritex = ritex.wholeThingGrouped();
        return ritex.and("+");
    }
    static RiotString oneOrMore(String expression) {
        return oneOrMore(riot(expression));
    }

}
