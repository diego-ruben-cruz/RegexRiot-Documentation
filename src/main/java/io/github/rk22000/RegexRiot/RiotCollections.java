package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotCollections {
    static RiotString charsIn(char[] chars) {
        return riot("[" + new String(chars) + "]", true);
    }
    class RiotCharRange {
        private final char startChar;
        RiotCharRange(char inclusiveStartChar) {
            startChar = inclusiveStartChar;
        }
        public RiotString through(char inclusiveEndChar) {
            return riot("["+startChar+"-"+inclusiveEndChar+"]", true);
        }
    }

    static RiotCharRange chars(char inclusiveStartChar) {
        return new RiotCharRange(inclusiveStartChar);
    }

}
