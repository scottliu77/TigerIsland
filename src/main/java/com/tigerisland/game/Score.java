package com.tigerisland.game;

import com.tigerisland.InvalidMoveException;

public class Score {

    private int score;

    public Score() {
        score = 0;
    }

    public Score(Score score) {
        this.score = score.getScoreValue();
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
