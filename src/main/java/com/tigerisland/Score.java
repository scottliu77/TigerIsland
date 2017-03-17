package com.tigerisland;

public class Score {

    private int score;

    Score() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        score += Math.abs(points);
    }
}
