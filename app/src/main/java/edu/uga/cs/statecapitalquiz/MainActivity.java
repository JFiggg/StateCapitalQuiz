package edu.uga.cs.statecapitalquiz;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.opencsv.CSVReader;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initially populates the database with the CSV file
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isInitialized = prefs.getBoolean("is_db_initialized", false);
        if (!isInitialized) {
            populateDB(); // method that reads the CSV and inserts data
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("is_db_initialized", true);
            editor.apply();
        }

        testQuery();
    }

    public void populateDB() {
        QuizDBHelper dbHelper = QuizDBHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            InputStream is = getAssets().open("state_capitals_updated.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(is));
            String[] nextLine;
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                String state = nextLine[0];
                String capital = nextLine[1];
                String wrongCapital1 = nextLine[2];
                String wrongCapital2 = nextLine[3];


                ContentValues values = new ContentValues();
                values.put("state", state);
                values.put("capital", capital);
                values.put("wrong_capital1", wrongCapital1);
                values.put("wrong_capital2", wrongCapital2);
                db.insert("questions", null, values);
            }

            is.close();
            reader.close();
            db.close();
            Log.d("CSVLoader", "Database successfully populated from CSV!");
        } catch (Exception e) {
            Log.e("CSVLoader", "Error loading CSV", e);
        }
    }

    private void testQuery() {
        SQLiteDatabase db = QuizDBHelper.getInstance(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuizDBHelper.TABLE_QUESTIONS, null);

        Log.d("DEBUG", "Row count: " + cursor.getCount());

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                String state = cursor.getString(cursor.getColumnIndexOrThrow("state"));
                String capital = cursor.getString(cursor.getColumnIndexOrThrow("capital"));
                Log.d("DEBUG", "Row -> id: " + id + ", state: " + state + ", capital: " + capital);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
}