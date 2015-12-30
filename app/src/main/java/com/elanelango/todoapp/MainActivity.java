package com.elanelango.todoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> todoItems;
    TodoArrayAdapter aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create ArrayList by initializing it with todo.txt and setup ArrayAdapter
        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        aToDoAdapter = new TodoArrayAdapter(this, R.layout.edit_todo_view, R.id.tvEntry, todoItems);
        lvItems.setAdapter(aToDoAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        //Handle Enter keypress in add item EditText
        etEditText.setOnKeyListener(new EditText.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER ||
                                keyCode == KeyEvent.KEYCODE_DPAD_CENTER))
                    addItem();
                return false;
            }
        });

        //Delete item when it's long pressed
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            if (!file.exists())
                file.createNewFile();
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            Log.e("MainActivity", "Error while reading/creating todo.txt file!");
        }
    }

    public void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            Log.e("MainActivity", "Error while writing todo.txt file!");
        }
    }

    private void addItem() {
        String text = etEditText.getText().toString();
        if (!text.isEmpty()) {
            aToDoAdapter.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
        } else {
            Toast.makeText(this, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddItem(View view) {
        addItem();
    }
}
