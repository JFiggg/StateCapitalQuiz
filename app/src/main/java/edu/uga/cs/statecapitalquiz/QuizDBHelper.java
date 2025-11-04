package edu.uga.cs.statecapitalquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class QuizDBHelper extends SQLiteOpenHelper {
    private static final String DEBUG_TAG = "CapitalQuizDBHelper";

    private static final String DB_NAME = "state_capital_quiz.db";
    private static final int DB_VERSION = 6;
    private final Context context;

    // Define all names (strings) for tables.
    public static final String TABLE_QUIZZES = "quizzes";
    public static final String TABLE_QUESTIONS = "questions";
    public static final String TABLE_QUIZ_QUESTIONS = "quiz_questions";

    // Quiz Table Columns
    public static final String QUIZZES_COLUMN_ID = "_id";
    public static final String QUIZZES_COLUMN_DATE = "date";
    public static final String QUIZZES_COLUMN_RESULTS = "results";
    public static final String QUIZZES_COLUMN_TIME = "time";

    // Question Table Columns
    public static final String QUESTIONS_COLUMN_ID = "_id";
    public static final String QUESTIONS_COLUMN_STATE = "state";
    public static final String QUESTIONS_COLUMN_CAPITAL = "capital";
    public static final String QUESTIONS_COLUMN_WRONG_CAPITAL1 = "wrong_capital1";
    public static final String QUESTIONS_COLUMN_WRONG_CAPITAL2 = "wrong_capital2";


    // Quiz Questions Relationship Table Columns.
    public static final String QUIZ_QUESTIONS_COLUMN_QUIZ_ID = "quiz";
    public static final String QUIZ_QUESTIONS_COLUMN_QUESTION_ID = "question";
    public static final String QUIZ_QUESTIONS_COLUMN_IS_CORRECT = "is_correct";

    // This is a reference to the only instance for the helper.
    private static QuizDBHelper helperInstance;

    // A Create table SQL statement to create a tables for quizzes table and questions table. .
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUIZZES =
            "create table " + TABLE_QUIZZES + " ("
                    + QUIZZES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUIZZES_COLUMN_DATE + " TEXT, "
                    + QUIZZES_COLUMN_TIME+ " TEXT, "
                    + QUIZZES_COLUMN_RESULTS + " TEXT "
                    + ")";

    private static final String CREATE_QUESTIONS =
            "create table " + TABLE_QUESTIONS + " ("
                    + QUESTIONS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + QUESTIONS_COLUMN_STATE + " TEXT, "
                    + QUESTIONS_COLUMN_CAPITAL + " TEXT, "
                    + QUESTIONS_COLUMN_WRONG_CAPITAL1 + " TEXT, "
                    + QUESTIONS_COLUMN_WRONG_CAPITAL2 + " TEXT "
                    + ")";

    private static final String CREATE_QUIZ_QUESTIONS =
            "create table " + TABLE_QUIZ_QUESTIONS + " ("
                    + QUIZ_QUESTIONS_COLUMN_QUIZ_ID + " INTEGER , "
                    + QUIZ_QUESTIONS_COLUMN_QUESTION_ID + " INTEGER, "
                    + QUIZ_QUESTIONS_COLUMN_IS_CORRECT + "INTEGER, "
                    + "FOREIGN KEY(" + QUIZ_QUESTIONS_COLUMN_QUIZ_ID + ") REFERENCES "
                    + TABLE_QUIZZES + "(" + QUIZZES_COLUMN_ID + "), "
                    + "FOREIGN KEY(" + QUIZ_QUESTIONS_COLUMN_QUESTION_ID + ") REFERENCES "
                    + TABLE_QUESTIONS + "(" + QUESTIONS_COLUMN_ID + ")"
                    + ")";

    // Note that the constructor is private!
    // So, it can be called only from
    // this class, in the getInstance method.
    private QuizDBHelper(@Nullable Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
        this.context = context;
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public synchronized static QuizDBHelper getInstance(Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuizDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( CREATE_QUIZZES );
        db.execSQL( CREATE_QUESTIONS );
        db.execSQL( CREATE_QUIZ_QUESTIONS );
        Log.d(DEBUG_TAG, "Tables created: " + TABLE_QUIZZES + ", " + TABLE_QUESTIONS);
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_QUESTIONS);
        onCreate( db );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }

    public void insertQuiz(Quiz quiz) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        values.put(QUIZZES_COLUMN_DATE, dateFormat.format(quiz.getDate()));
        values.put(QUIZZES_COLUMN_TIME, quiz.getTime());
        values.put(QUIZZES_COLUMN_RESULTS, quiz.getResult());

        db.insert("quizzes", null, values);
        db.close();
    }

    public ArrayList<Quiz> getAllQuizzes() {
        ArrayList<Quiz> quizList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT _id, date, time, results FROM " + TABLE_QUIZZES, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String dateStr = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String resultStr = cursor.getString(cursor.getColumnIndexOrThrow("results")); // TEXT column

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date date = dateFormat.parse(dateStr);

                    // If your Quiz constructor uses int result, parse it
                    int result = 0;
                    try {
                        result = Integer.parseInt(resultStr);
                    } catch (NumberFormatException ignored) {}

                    Quiz quiz = new Quiz(date, result, time);
                    quiz.setId(id);
                    quizList.add(quiz);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return quizList;
    }


}
