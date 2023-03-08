package io.github.rk22000.RegexRiot;

class SimpleRiotQuantifiers {
    public static SimpleRiotString oneOrNone(SimpleRiotString seed) {
        return seed.wholeThingGrouped().wholeThingOptional();
    }
    public static SimpleRiotString oneOrNone(String seed) {
        return oneOrNone(Riot.create(seed));
    }
    public static SimpleRiotString zeroOrMore(SimpleRiotString seed) {
        return seed.wholeThingGrouped().and("*");
    }
    public static SimpleRiotString zeroOrMore(String seed) {
        return zeroOrMore(Riot.create(seed));
    }
    public static SimpleRiotString oneOrMore(SimpleRiotString seed) {
        return seed.wholeThingGrouped().and("+");
    }
    public static SimpleRiotString oneOrMore(String seed) {
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
    public SimpleRiotString times(SimpleRiotString riotExp) {
        return riotExp.wholeTimes(count);
    }
    public SimpleRiotString times(String seedExp) {
        return Riot.create(seedExp).wholeTimes(count);
    }
}