package io.github.rk22000;

public class Riot {
    public static RiotString create(String seed) {
        return new BasicRiotString(seed);
    }
    public static RiotString riotExpression(String seed) {
        return new BasicRiotString(seed);
    }
}
