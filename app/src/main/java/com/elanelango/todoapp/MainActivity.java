package com.elanelango.todoapp;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TodoCursorAdapter todoAdapter;
    ListView lvItems;
    EditText etEditText;
    Calendar dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        Cursor cursor = TodoItem.fetchResultCursor();
        todoAdapter = new TodoCursorAdapter(this, cursor, 0);
        lvItems.setAdapter(todoAdapter);

        etEditText = (EditText) findViewById(R.id.etEditText);

        dueDate = Calendar.getInstance();
        dueDate.set(dueDate.get(Calendar.YEAR),
                dueDate.get(Calendar.MONTH),
                dueDate.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);

        //Delete item when it's long pressed
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String text = "";
                if(view instanceof EditTodoView) {
                    text = ((EditTodoView)view).getText().toString();
                } else if(view instanceof TextView) {
                    text = ((TextView)view).getText().toString();
                }
                TodoItem.deleteItem(text);
                todoAdapter.refreshAfterChange();
                return false;
            }
        });
    }

    private void addItem() {
        String text = etEditText.getText().toString();
        if (!text.isEmpty()) {
            TodoItem item = TodoItem.addItem(text, dueDate);
            todoAdapter.refreshAfterChange();
            etEditText.setText("");

        } else {
            Toast.makeText(this, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onAddItem(View view) {
        addItem();
    }

    public void onAddDue(View view) {
        DueDateDialog dueDialog = new DueDateDialog();
        dueDialog.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        dueDate = Calendar.getInstance();
        dueDate.clear();
        dueDate.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
    }
}
