package com.tigerisland.messenger;

import java.util.EnumSet;

public enum  MessageType {

    ENTERTOURNAMENT("CONNECT"),
    AUTHENTICATETEAM("CONNECT"),
    PLAYERID("CONNECT"),
    CHALLENGESTARTED("CHALLENGE"),
    LASTCHALLENGEOVER("CHALLENGE"),
    ROUNDSTARTED("ROUND"),
    ROUNDENDED("ROUND"),
    MAKEMOVE("MAKEMOVE"),
    LASTROUNDOVER("ROUND"),
    MATCHSTARTED("MATCH"),
    MATCHOVER("MATCH"),
    FORFEITTILE("GAMEOVER"),
    FORFEITBUILD("GAMEOVER"),
    FORFEITTIMEOUT("GAMEOVER"),
    LOSTNOBUILD("GAMEOVER"),
    GAMEOVER("GAMEOVER"),
    FOUNDSETTLEMENT("BUILDACTION"),
    EXPANDSETTLEMENT("BUILDACTION"),
    BUILDTOTORO("BUILDACTION"),
    BUILDTIGER("BUILDACTION"),
    PROCESSED("STATUS");

    private String subtype;

    MessageType(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }

}
