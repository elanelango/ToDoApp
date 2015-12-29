package com.elanelango.todoapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by eelango on 12/28/15.
 */
public class EditTodoView extends RelativeLayout {

    private TextView tvEntry;
    private EditText etEditEntry;
    private ImageView ivDone;

    public EditTodoView(Context context) {
        super(context);
        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        layoutInflater.inflate(R.layout.edit_todo_view, this);
        this.tvEntry = (TextView) findViewById(R.id.tvEntry);
        this.etEditEntry = (EditText) findViewById(R.id.etEditEntry);
        this.ivDone = (ImageView) findViewById(R.id.ivDone);

        this.tvEntry.setOnClickListener(new TextView.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditTodoView.this.editMode();
            }
        });

        this.ivDone.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditTodoView.this.reset();
            }
        });
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
}
