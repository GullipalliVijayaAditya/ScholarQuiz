package com.example.adity.scholarquiz.Model;

public class Ranking {
    private String slackId;
    private long score;

    public Ranking() {
    }

    public Ranking(String slackId, long score) {
        this.slackId = slackId;
        this.score = score;
    }


    public String getSlackId() {
        return slackId;
    }

    public void setSlackId(String slackId) {
        this.slackId = slackId;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
