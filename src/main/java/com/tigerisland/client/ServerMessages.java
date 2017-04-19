package com.tigerisland.client;

import java.util.regex.Pattern;

public final class ServerMessages {

    public static final Pattern authWelcomePattern = Pattern.compile("WELCOME TO ANOTHER EDITION OF THUNDERDOME!");
    public static final Pattern authAcceptPattern = Pattern.compile("TWO SHALL ENTER, ONE SHALL LEAVE");
    public static final Pattern authWaitPlayerIDPattern = Pattern.compile("WAIT FOR THE TOURNAMENT TO BEGIN \\w+");

    public static final Pattern challengeNewPattern = Pattern.compile("NEW CHALLENGE \\w+ YOU WILL PLAY \\d+ MATCH[ES]?");
    public static final Pattern challengeWaitPattern = Pattern.compile("WAIT FOR THE NEXT CHALLENGE TO BEGIN");
    public static final Pattern challengeEndPattern = Pattern.compile("END OF CHALLENGES");

    public static final Pattern roundBeginPattern = Pattern.compile("BEGIN ROUND \\d+ OF \\d+");
    public static final Pattern roundEndWaitPattern = Pattern.compile("END OF ROUND \\d+ OF \\d+ WAIT FOR THE NEXT MATCH");
    public static final Pattern roundEndPattern = Pattern.compile("END OF ROUND \\d+ OF \\d+");

    public static final Pattern matchStartPattern = Pattern.compile("NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER \\w+");
    public static final Pattern gameOverPattern = Pattern.compile("GAME \\w+ OVER PLAYER \\w+ \\w+ PLAYER \\w+ \\w+");

    public static final Pattern makeMovePattern = Pattern.compile("MAKE YOUR MOVE IN GAME \\w+ WITHIN \\d+(.\\d+)? SECOND(S)?: MOVE \\w+ PLACE \\w+[\\+ ]?\\w+");
    public static final Pattern tournamentEndPattern = Pattern.compile("THANK YOU FOR PLAYING! GOODBYE");

    public static final Pattern forfeitIllegalTilePlacementPattern = Pattern.compile("FORFEITED: ILLEGAL TILE PLACEMENT");
    public static final Pattern forfeitIllegalBuildPattern = Pattern.compile("FORFEITED: ILLEGAL BUILD");
    public static final Pattern forfeitTimeoutPattern = Pattern.compile("FORFEITED: TIMEOUT");
    public static final Pattern forfeitMalformedMovePattern = Pattern.compile("FORFEITED: MALFORMED MOVE");

    public static final Pattern serverLostUnableToBuildPattern = Pattern.compile("LOST: UNABLE TO BUILD");

    public static final Pattern serverRequestsGameOutcomePattern = Pattern.compile("GAME \\w+ OVER SEND OUTCOME");
    public static final Pattern playerDoesNotKnowOutcomePattern = Pattern.compile("GAME \\w+ PLAYER \\w+ FORFEITED: DOES NOT KNOW OUTCOME");


}

