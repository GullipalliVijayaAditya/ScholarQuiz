package com.example.adity.scholarquiz.Model;

public class Ranking {
    private String userId;
    private long score;

    public Ranking() {
    }

    public Ranking(String userId, long score) {
        this.userId = userId;
        this.score = score;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
