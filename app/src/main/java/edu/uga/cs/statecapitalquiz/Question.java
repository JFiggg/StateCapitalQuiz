package edu.uga.cs.statecapitalquiz;

public class Question {
    private int id;
    private String state;
    private String capital;
    private String wrongCapital1;
    private String wrongCapital2;

    public Question(){
        id = -1;
        state = null;
        capital = null;
        wrongCapital1 = null;
        wrongCapital2 = null;
    }

    public Question(String state, String capital, String wrongCapital1, String wrongCapital2) {
        this.state = state;
        this.capital = capital;
        this.wrongCapital1 = wrongCapital1;
        this.wrongCapital2 = wrongCapital2;
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

    public String getWrongCapital1() {
        return wrongCapital1;
    }
    public void setWrongCapital1(String wrongCapital1) {
        this.wrongCapital1 = wrongCapital1;
    }

    public String getWrongCapital2() {
        return wrongCapital2;
    }
    public void setWrongCapital2(String wrongCapital2) {
        this.wrongCapital2 = wrongCapital2;
    }
}
