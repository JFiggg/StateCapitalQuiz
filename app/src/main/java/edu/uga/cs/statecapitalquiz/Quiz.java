package edu.uga.cs.statecapitalquiz;

import java.util.Date;

/**
 * This class represents a single capital quiz(POJO), including the id, capital name,
 * phone number, URL, and some comments.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Quiz {

    private int id;
    private Date date;
    private int result;
    private int questionsAnswered;

    public Quiz()
    {
        this.id = -1;
        this.date = null;
        this.result = -1;
        this.questionsAnswered = -1;
    }

    public Quiz(Date date, int result, int questionsAnswered ) {
        this.date = date;
        this.result = result;
        this.questionsAnswered = questionsAnswered;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getResult() {
        return result;
    }
    public void setResult(int result) {
        this.result = result;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }
    public void setQuestionsAnswered(int setQuestionsAnswered) {
        this.questionsAnswered = setQuestionsAnswered;
    }
}
