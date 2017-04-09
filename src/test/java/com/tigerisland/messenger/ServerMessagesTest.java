package com.tigerisland.messenger;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class ServerMessagesTest {

    private String message;
    private Pattern protocol;

    private Boolean attemptMatchAndReturnSuccess(String message, Pattern protocol) {
        Matcher matcher = protocol.matcher(message);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    @Test
    public void welcomeTest() {
        message = "WELCOME TO ANOTHER EDITION OF THUNDERDOME!";
        protocol = ServerMessages.authWelcomePattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void authAcceptTest() {
        message = "TWO SHALL ENTER, ONE SHALL LEAVE";
        protocol = ServerMessages.authAcceptPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void authWaitTest() {
        message = "WAIT FOR THE TOURNAMENT TO BEGIN 7";
        protocol = ServerMessages.authWaitPlayerIDPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void newChallengeVariantOneTest() {
        message = "NEW CHALLENGE 1 YOU WILL PLAY 1 MATCH";
        protocol = ServerMessages.challengeNewPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void newChallengeVariantTwoTest() {
        message = "NEW CHALLENGE 2 YOU WILL PLAY 5 MATCHES";
        protocol = ServerMessages.challengeNewPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void beginRoundTest() {
        message = "BEGIN ROUND 1 OF 5";
        protocol = ServerMessages.roundBeginPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void newMatchBeginningTest() {
        message = "NEW MATCH BEGINNING NOW YOUR OPPONENT IS PLAYER 13";
        protocol = ServerMessages.matchStartPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void makeMoveVariantOneTest() {
        message = "MAKE YOUR MOVE IN GAME A WITHIN 1 SECOND: MOVE 1 PLACE ROCKY+LAKE";
        protocol = ServerMessages.makeMovePattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void makeMoveVariantTwoTest() {
        message = "MAKE YOUR MOVE IN GAME A WITHIN 1.75 SECONDS: MOVE 1 PLACE ROCKY+LAKE";
        protocol = ServerMessages.makeMovePattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void endOfRoundTest() {
        message = "END OF ROUND 5 OF 5";
        protocol = ServerMessages.roundEndPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void endOfRoundAndWaitTest() {
        message = "END OF ROUND 1 OF 5 WAIT FOR THE NEXT MATCH";
        protocol = ServerMessages.roundEndWaitPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void endOfChallengesTest() {
        message = "END OF CHALLENGES";
        protocol = ServerMessages.challengeEndPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void waitForNextChallengeTest() {
        message = "WAIT FOR THE NEXT CHALLENGE TO BEGIN";
        protocol = ServerMessages.challengeWaitPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void thankYouForPlayingTest() {
        message = "THANK YOU FOR PLAYING! GOODBYE";
        protocol = ServerMessages.tournamentEndPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void forfeightTilePlacementTest() {
        message = "GAME A MOVE 1 PLAYER 2 FORFEITED: ILLEGAL TILE PLACEMENT";
        protocol = ServerMessages.forfeitIllegalTilePlacementPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void forfeightIllegalBuildTest() {
        message = "GAME A MOVE 1 PLAYER 2 FORFEITED: ILLEGAL BUILD";
        protocol = ServerMessages.forfeitIllegalBuildPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void forfeightTimeoutTest() {
        message = "GAME A MOVE 1 PLAYER 2 FORFEITED: TIMEOUT";
        protocol = ServerMessages.forfeitTimeoutPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }

    @Test
    public void lostUnableToBuildTest() {
        message = "GAME A MOVE 1 PLAYER 2 LOST: UNABLE TO BUILD";
        protocol = ServerMessages.serverLostUnableToBuildPattern;
        assertTrue(attemptMatchAndReturnSuccess(message, protocol));
    }
}
