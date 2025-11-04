package edu.uga.cs.statecapitalquiz;

public class ScoreItemViewPager extends QuizItemViewPager{
    int scorePercent;

    public ScoreItemViewPager(int scorePercent) {
        super(0, null, null, null, null);
        this.scorePercent = scorePercent;
    }
}
