package com.elanelango.todoapp;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by eelango on 1/7/16.
 */
@Table(name="todo_items")
public class TodoItem extends Model {
    @Column(name = "text")
    public String text;

    @Column(name = "due_date")
    public Date dueDate;

    public TodoItem() {
        super();
    }

    public TodoItem(String text, Date dueDate) {
        super();
        this.text = text;
        this.dueDate = dueDate;
    }

    public static List<TodoItem> getAllItems() {
        return new Select()
                .from(TodoItem.class)
                .orderBy("due_date DSC")
                .execute();
    }

    public static TodoItem addItem(String text, Date dueDate) {
        TodoItem item = new TodoItem();
        item.text = text;
        item.dueDate = dueDate;
        item.save();
        return item;
    }

    // Return cursor for result set for all todo items
    public static Cursor fetchResultCursor() {
        // Query all items sorted by date
        String resultRecords = new Select("rowid _id, *")
                .from(TodoItem.class)
                .orderBy("due_date DESC")
                .toSql();

        // Execute query on the underlying ActiveAndroid SQLite database
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);
        return resultCursor;
    }

    public static void deleteItem(String text) {
        new Delete().from(TodoItem.class).where("text = ?", text).execute();
    }

    public static void updateItem(String original, String replace) {
        new Update(TodoItem.class)
                .set("text = ?", replace)
                .where("text = ?", original)
                .execute();
    }
}
