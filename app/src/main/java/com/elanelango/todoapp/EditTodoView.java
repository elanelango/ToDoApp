package com.elanelango.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eelango on 12/28/15.
 */
public class EditTodoView extends RelativeLayout {

    private Context context;
    private TextView tvEntry;
    private EditText etEditEntry;
    private ImageView ivDone;
    private TodoArrayAdapter todoAdapter;

    public EditTodoView(Context context, final TodoArrayAdapter todoAdapter) {
        super(context);
        this.context = context;
        this.todoAdapter = todoAdapter;
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        layoutInflater.inflate(R.layout.edit_todo_view, this);
        tvEntry = (TextView) findViewById(R.id.tvEntry);
        etEditEntry = (EditText) findViewById(R.id.etEditEntry);
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

        /*etEditEntry.setOnKeyListener(new EditText.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER ||
                                keyCode == KeyEvent.KEYCODE_DPAD_CENTER))
                    saveEdit();

                return false;
            }
        });*/

    }

    public void reset() {
        etEditEntry.setVisibility(INVISIBLE);
        ivDone.setVisibility(INVISIBLE);
        tvEntry.setVisibility(VISIBLE);
    }

    public void editMode() {
        tvEntry.setVisibility(INVISIBLE);
        etEditEntry.setVisibility(VISIBLE);
        ivDone.setVisibility(VISIBLE);
    }
    public void setText(String text) {
        this.tvEntry.setText(text);
        this.etEditEntry.setText(text);
    }

    private void saveEdit() {
        String replace = etEditEntry.getText().toString();
        if(!replace.isEmpty()) {
            String original = tvEntry.getText().toString();
            todoAdapter.replace(original, replace);
            ((MainActivity) EditTodoView.this.context).writeItems();
            EditTodoView.this.reset();
        } else {
            Toast.makeText(context, "ToDo item can't be empty!", Toast.LENGTH_SHORT).show();
            etEditEntry.requestFocus();
        }
    }
}
