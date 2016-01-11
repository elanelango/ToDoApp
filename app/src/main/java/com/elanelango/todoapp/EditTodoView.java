package com.elanelango.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eelango on 12/28/15.
 */
public class EditTodoView extends RelativeLayout {

    private Context context;
    private TextView tvEntry;
    private TextView tvDays;
    private EditText etEditEntry;
    private Button btDays;
    private ImageView ivDone;
    private TodoCursorAdapter todoAdapter;

    public EditTodoView(Context context) {
        super(context);
    }

    public EditTodoView(Context context, final TodoCursorAdapter todoAdapter) {
        super(context);
        this.context = context;
        this.todoAdapter = todoAdapter;
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        layoutInflater.inflate(R.layout.edit_todo_view, this);
        tvEntry = (TextView) findViewById(R.id.tvEntry);
        tvDays = (TextView) findViewById(R.id.tvDays);
        etEditEntry = (EditText) findViewById(R.id.etEditEntry);
        btDays = (Button) findViewById(R.id.btDays);
        ivDone = (ImageView) findViewById(R.id.ivDone);

        tvEntry.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditTodoView.this.editMode();
            }
        });

        ivDone.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveEdit();
            }
        });

    }

    public void reset() {
        etEditEntry.setVisibility(INVISIBLE);
        btDays.setVisibility(INVISIBLE);
        ivDone.setVisibility(INVISIBLE);
        tvEntry.setVisibility(VISIBLE);
        tvDays.setVisibility(VISIBLE);
    }

    public void editMode() {
        tvEntry.setVisibility(INVISIBLE);
        tvDays.setVisibility(INVISIBLE);
        etEditEntry.setVisibility(VISIBLE);
        btDays.setVisibility(VISIBLE);
        ivDone.setVisibility(VISIBLE);
    }

    public String getText() {
        return this.tvEntry.getText().toString();
    }

    public void setText(String text) {
        this.tvEntry.setText(text);
        this.etEditEntry.setText(text);
    }

    private void saveEdit() {
        String replace = etEditEntry.getText().toString();
        if(!replace.isEmpty()) {
            String original = tvEntry.getText().toString();
            TodoItem.updateItem(original, replace);
            todoAdapter.refreshAfterChange();
            EditTodoView.this.reset();
        } else {
            Toast.makeText(context, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
            etEditEntry.requestFocus();
        }
    }
}
