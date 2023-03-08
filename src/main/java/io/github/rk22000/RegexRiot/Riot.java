package io.github.rk22000.RegexRiot;

public class Riot {
    public static SimpleRiotString create(String seed) {
        return new BasicRiotString(seed);
    }
    public static SimpleRiotString riotExpression(String seed) {
        return new BasicRiotString(seed);
    }
    static RiotString riot(String seed, boolean isUnitChain) {
        return new ChildRiotString(new BasicRiotString(seed, isUnitChain));
    }
    public static RiotString riot(String seed) {
        return new ChildRiotString(new BasicRiotString(seed));
    }
    public static RiotString riot(SimpleRiotString seed) {
        return new ChildRiotString(seed);
    }
}
