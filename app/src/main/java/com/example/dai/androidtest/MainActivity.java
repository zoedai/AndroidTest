package com.example.dai.androidtest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    EditText inputName, inputNumber;
    Button ok;
    ListView listView;
    MyDbHelper dbHelper;
    byte[] defaultImg;
    Cursor cursor;
    SimpleCursorAdapter mCursorAdapter;
    String[] fromColumns = {MyDBEntry.COLUMN_NAME_NAME, MyDBEntry.COLUMN_NAME_NUMBER};
    //MyDBEntry.COLUMN_NAME_IMG};
    int[] toViews = {R.id.name, R.id.number};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDbHelper(getApplicationContext());

        ok = (Button) findViewById(R.id.button);
        inputName = (EditText) findViewById(R.id.editText);
        inputNumber = (EditText) findViewById(R.id.editText2);
        listView = (ListView) findViewById(R.id.listView);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.avatar);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        defaultImg = stream.toByteArray();

        cursor = dbHelper.retrieve();

        mCursorAdapter = new SimpleCursorAdapter(
                MainActivity.this,
                R.layout.listview_row,
                null,
                fromColumns, toViews,
                0);
/*
        mCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.avatar) {
                    ImageView IV = (ImageView) view;
                    int resID = getApplicationContext().getResources().getIdentifier(
                            cursor.getString(columnIndex),
                            "drawable", getApplicationContext().getPackageName());
                    IV.setImageDrawable(
                            getApplicationContext().getResources().getDrawable(resID));
                    return true;
                }
                return false;
            }
        });

*/
        listView.setAdapter(mCursorAdapter);



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = inputName.getText().toString();
                String number = inputNumber.getText().toString();

                long id = dbHelper.insert(name, number, defaultImg);
                if (id > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Successfully added new entry",
                            Toast.LENGTH_LONG);
                }
            }
        });
    }


}
