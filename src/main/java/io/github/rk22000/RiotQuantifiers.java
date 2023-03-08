package io.github.rk22000;

public class RiotQuantifiers {
    public static RiotString oneOrNone(RiotString seed) {
        return seed.grouped().optional();
    }
    public static RiotString oneOrNone(String seed) {
        return oneOrNone(Riot.create(seed));
    }
    public static RiotString zeroOrMore(RiotString seed) {
        return seed.grouped().and("*");
    }
    public static RiotString zeroOrMore(String seed) {
        return zeroOrMore(Riot.create(seed));
    }
    public static RiotString oneOrMore(RiotString seed) {
        return seed.grouped().and("+");
    }
    public static RiotString oneOrMore(String seed) {
        return oneOrMore(Riot.create(seed));
    }

    public static RiotNumber number(int times) {
        return new RiotNumber(times);
    }

}

class RiotNumber {
    int count;
    RiotNumber(int times) {
        count = times;
    }
    public RiotString times(RiotString riotExp) {
        return riotExp.times(count);
    }
    public RiotString times(String seedExp) {
        return Riot.create(seedExp).times(count);
    }
}