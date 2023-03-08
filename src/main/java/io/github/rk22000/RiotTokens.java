package io.github.rk22000;

public class RiotTokens {
    public static RiotString DIGIT() {
        return new BasicRiotString("\\d", true);
    }
    public static RiotString DOT() {
        return new BasicRiotString("\\.", true);
    }
    public static RiotString ANY_CHAR() {
        return new BasicRiotString(".", true);
    }
}
