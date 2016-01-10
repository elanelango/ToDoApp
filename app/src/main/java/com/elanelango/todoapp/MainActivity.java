package com.elanelango.todoapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TodoCursorAdapter todoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        Cursor cursor = TodoItem.fetchResultCursor();
        todoAdapter = new TodoCursorAdapter(this, cursor, 0);
        lvItems.setAdapter(todoAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        //Delete item when it's long pressed
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tvEntry = (TextView) view;
                String text = tvEntry.getText().toString();
                TodoItem.deleteItem(text);
                todoAdapter.refreshAfterChange();
                return false;
            }
        });
    }

    private void addItem() {
        String text = etEditText.getText().toString();
        if (!text.isEmpty()) {
            TodoItem item = TodoItem.addItem(text, null);
            todoAdapter.refreshAfterChange();
            etEditText.setText("");

        } else {
            Toast.makeText(this, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddItem(View view) {
        addItem();
    }
}
