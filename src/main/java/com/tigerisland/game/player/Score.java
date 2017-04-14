package com.tigerisland.game.player;

import com.tigerisland.game.InvalidMoveException;

public class Score {

    public static final int TOTORO_POINT_VALUE = 200;
    public static final int VILLAGER_POINT_VALUE = 1;
    public static final int TIGER_POINT_VALUE = 75;

    private int score;

    public Score() {
        score = 0;
    }

    public Score(int scoreInt){
        score = scoreInt;
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
