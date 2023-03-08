package io.github.rk22000.RegexRiot;

import static io.github.rk22000.RegexRiot.Riot.riot;

public interface RiotGroupings {
    static RiotString group(int groupNo) {
        return riot("\\"+groupNo, true);
    }
}
