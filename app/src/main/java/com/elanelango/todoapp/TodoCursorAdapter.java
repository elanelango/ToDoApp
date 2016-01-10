package com.elanelango.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import java.util.Date;


/**
 * Created by eelango on 12/28/15.
 */
public class TodoCursorAdapter extends CursorAdapter {

    private Context context;

    public TodoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return new EditTodoView(context, this);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Extract properties from cursor
        String text = cursor.getString(cursor.getColumnIndexOrThrow("text"));
        Date dueDate = new Date(cursor.getLong(cursor.getColumnIndexOrThrow("due_date")));

        // Populate fields with extracted properties
        EditTodoView editTodoView = (EditTodoView) view;
        editTodoView.setText(text);
    }

    public void refreshAfterChange() {
        this.changeCursor(TodoItem.fetchResultCursor());
        this.notifyDataSetChanged();
    }

   /* public void replace(TodoItem original, TodoItem replace) {
        int position = getPosition(original);
        remove(original);
        insert(replace, position);
    }*/
}
