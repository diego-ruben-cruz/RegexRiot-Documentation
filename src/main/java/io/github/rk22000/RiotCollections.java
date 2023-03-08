package io.github.rk22000;

public class RiotCollections {
    public static RiotString charIn(String chars) {
        return new BasicRiotString("[" + chars + "]");
    }
    public static RiotRange chars(char inclusiveStartChar) {
        return new RiotRange(inclusiveStartChar);
    }
    public static class RiotRange {
        private char startChar;
        RiotRange(char inclusiveStartChar) {
            startChar = inclusiveStartChar;
        }
        public RiotString through(char inclusiveEndChar) {
            return new BasicRiotString("["+startChar+"-"+inclusiveEndChar+"]");
        }
    }
}