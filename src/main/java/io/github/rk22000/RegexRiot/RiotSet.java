package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotSet {
    static RiotSet riotSet(RiotString initialElement) {
        return new BasicRiotSet(initialElement);
    }
    RiotSet union(RiotSet oSet);
    RiotString toRiotString();


    static RiotSet charsIn(char[] chars) {
        return riotSet(riot(new String(chars)));
    }
    class RiotCharRange {
        private final char startChar;
        RiotCharRange(char inclusiveStartChar) {
            startChar = inclusiveStartChar;
        }
        public RiotSet through(char inclusiveEndChar) {
            return riotSet(riot(startChar+"-"+inclusiveEndChar, true));
        }
    }

    static RiotCharRange chars(char inclusiveStartChar) {
        return new RiotCharRange(inclusiveStartChar);
    }

}

class BasicRiotSet implements RiotSet{
    RiotString setString;
    BasicRiotSet(RiotString initialElement) {
        setString = initialElement;
    }

    @Override
    public String toString() {
        return setString.toString();
    }

    @Override
    public RiotSet union(RiotSet oSet) {
        return new BasicRiotSet(
                setString.and(oSet.toString())
        );
    }

    @Override
    public RiotString toRiotString() {
        return riot(
                "[" + setString + "]",
                true
        );
    }
}

