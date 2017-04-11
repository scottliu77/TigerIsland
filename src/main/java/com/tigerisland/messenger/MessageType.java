package com.tigerisland.messenger;

import java.util.EnumSet;

public enum  MessageType {

    ENTERTOURNAMENT("CONNECT"),
    AUTHENTICATETEAM("CONNECT"),
    PLAYERID("CONNECT"),

    CHALLENGESTARTED("CHALLENGE"),
    ENDOFCHALLENGE("CHALLENGE"),
    LASTCHALLENGEOVER("CHALLENGE"),

    ROUNDSTARTED("ROUND"),
    ROUNDENDED("ROUND"),
    LASTROUNDOVER("ROUND"),

    MATCHSTARTED("MATCH"),
    MATCHOVER("MATCH"),

    FORFEITTILE("FORFEIT"),
    FORFEITBUILD("FORFEIT"),
    FORFEITTIMEOUT("FORFEIT"),
    LOSTNOBUILD("FORFEIT"),
    FORFEITMALFORMED("FORFEIT"),
    GAMEOVER("GAMEOVER"),

    MAKEMOVE("MAKEMOVE"),

    FOUNDSETTLEMENT("BUILDACTION"),
    EXPANDSETTLEMENT("BUILDACTION"),
    BUILDTOTORO("BUILDACTION"),
    BUILDTIGER("BUILDACTION"),

    PROCESSED("STATUS"),
    UNASSIGNED("STATUS"),

    TOURNAMENTEND("CONNECT");

    private String subtype;

    MessageType(String subtype) {
        this.subtype = subtype;
    }

    public String getSubtype() {
        return subtype;
    }

}
