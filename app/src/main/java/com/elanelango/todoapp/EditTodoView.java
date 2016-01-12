package com.elanelango.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by eelango on 12/28/15.
 */
public class EditTodoView extends RelativeLayout {

    private MainActivity activity;
    private TextView tvEntry;
    private TextView tvDays;
    private EditText etEditEntry;
    private ImageView ivEditDue;
    private ImageView ivDone;
    private TodoCursorAdapter todoAdapter;
    public Calendar originalDueDate;

    public EditTodoView(Context context) {
        super(context);
    }

    public EditTodoView(final Context context, final TodoCursorAdapter todoAdapter) {
        super(context);
        activity = (MainActivity) context;
        this.todoAdapter = todoAdapter;
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        layoutInflater.inflate(R.layout.edit_todo_view, this);
        tvEntry = (TextView) findViewById(R.id.tvEntry);
        tvDays = (TextView) findViewById(R.id.tvDays);
        etEditEntry = (EditText) findViewById(R.id.etEditEntry);
        ivEditDue = (ImageView) findViewById(R.id.ivEditDue);
        ivDone = (ImageView) findViewById(R.id.ivDone);
        originalDueDate = Calendar.getInstance();
        originalDueDate.clear();

        tvEntry.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.dueDate.setTimeInMillis(originalDueDate.getTimeInMillis());
                ListView listView = (ListView) EditTodoView.this.getParent();
                for(int i = 0 ; i < listView.getCount() ; i++) {
                    EditTodoView childItem = (EditTodoView) listView.getChildAt(i);
                    childItem.reset();
                }
                EditTodoView.this.editMode();
            }
        });

        ivDone.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveEdit();
            }
        });

        ivEditDue.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                DueDateDialog dueDialog = new DueDateDialog();
                dueDialog.show(activity.getSupportFragmentManager(), "datePicker");
            }
        });
    }

    public void reset() {
        // Reset EditText in case it was edited and not saved.
        etEditEntry.setText(tvEntry.getText().toString());
        etEditEntry.setVisibility(INVISIBLE);
        ivEditDue.setVisibility(INVISIBLE);
        ivDone.setVisibility(INVISIBLE);
        tvEntry.setVisibility(VISIBLE);
        tvDays.setVisibility(VISIBLE);
    }

    public void editMode() {
        tvEntry.setVisibility(INVISIBLE);
        tvDays.setVisibility(INVISIBLE);
        etEditEntry.setVisibility(VISIBLE);
        ivEditDue.setVisibility(VISIBLE);
        ivDone.setVisibility(VISIBLE);
    }

    public String getText() {
        return this.tvEntry.getText().toString();
    }

    public void setData(String text, long dueMilliSeconds) {
        setText(text);
        originalDueDate.setTimeInMillis(dueMilliSeconds);
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        long diff = (originalDueDate.getTimeInMillis() / 1000) - (today.getTimeInMillis() / 1000);
        Log.i("edittodoview", originalDueDate.getTimeInMillis() + " " + today.getTimeInMillis() + " " + diff);
        diff = diff / 86400;
        tvDays.setText(Long.toString(diff));
    }

    public void setText(String text) {
        this.tvEntry.setText(text);
        this.etEditEntry.setText(text);
    }

    private void saveEdit() {
        String replace = etEditEntry.getText().toString();
        if(!replace.isEmpty()) {
            String original = tvEntry.getText().toString();
            TodoItem.updateItem(original, replace, activity.dueDate);
            todoAdapter.refreshAfterChange();
            EditTodoView.this.reset();
        } else {
            Toast.makeText(activity, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
            etEditEntry.requestFocus();
        }
    }
}
