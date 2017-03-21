package com.tigerisland;

public class Score {

    private int score;

    Score() {
        score = 0;
    }

    public int getScoreValue() {
        return score;
    }

    public void addPoints(int points) throws InvalidMoveException {
        if (points > 0) {
            score += points;
        } else {
            throw new InvalidMoveException("Cannot add zero or fewer points");
        }
    }
}
