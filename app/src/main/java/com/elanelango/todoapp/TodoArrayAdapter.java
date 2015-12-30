package com.elanelango.todoapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by eelango on 12/28/15.
 */
public class TodoArrayAdapter extends ArrayAdapter<String> {

    private Context context;

    public TodoArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EditTodoView editTodoView = null;
        if (convertView == null) {
            editTodoView = new EditTodoView(context, this);
        } else {
            editTodoView = (EditTodoView) convertView;
        }

        String todoText = getItem(position);
        editTodoView.setText(todoText);

        return editTodoView;
    }

    public void replace(String original, String replace) {
        int position = getPosition(original);
        remove(original);
        insert(replace, position);
    }
}
