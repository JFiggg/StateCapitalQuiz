package edu.uga.cs.statecapitalquiz;

public class Question {
    private int id;
    private String state;
    private String capital;

    public Question(){
        id = -1;
        state = null;
        capital = null;
    }

    public Question(String state, String capital) {
        this.state = state;
        this.capital = capital;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
