package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.BasicRiotString.emptyBasicRiotString;

class SimpleRiotTokens {
    public static SimpleRiotString DIGIT() {
        return new BasicRiotString("\\d", true);
    }
    public static SimpleRiotString DOT() {
        return new BasicRiotString("\\.", true);
    }
    public static SimpleRiotString ANY_CHAR() {
        return new BasicRiotString(".", true);
    }
    public static SimpleRiotString WORD_CHAR() {
        return new BasicRiotString("\\w", true);
    }
    public static SimpleRiotString QUESTION_CHAR() {
        return new BasicRiotString("\\?", true);
    }
    public static SimpleRiotString EMPTY() {
        return emptyBasicRiotString();
    }
}
