package edu.uga.cs.statecapitalquiz;

import java.sql.Time;
import java.util.Locale;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This class represents a single capital quiz(POJO), including the id, capital name,
 * phone number, URL, and some comments.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Quiz {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    private int id;
    private Date date;
    private String time;
    private int result;

    public Quiz()
    {
        this.id = -1;
        this.date = null;
        this.time = "";
        this.result = -1;
    }

    public Quiz(Date date, int result, String time ) {
        this.date = date;
        this.time = time;
        this.result = result;
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

    public String getTime() {
        return time;
    }
    public void setTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        this.time = timeFormat.format(new Date());
    }
}
