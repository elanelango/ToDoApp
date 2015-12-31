# ToDoApp
My first Android App done as a part of CodePath course registration

The goal is to add editing feature to a todo list app.

I decided to go with inline editing of an item as opposed to editing an item in a separate activity.
I though inline editing is more intuitive for the user and is also a bit challenging to implement.



Here is a GIF demo of the app:

![Demo GIF](https://raw.githubusercontent.com/elanelango/ToDoApp/master/demo.gif)

I implemented this by having a custom view inside the ListView and using a custom ArrayAdapter.

The custom EditTodoView has a regular TextView on top and a EditText with Button (initially invisible)
underneath it. When the user clicks on the TextView, the TextView is made invisible and the EditText
and tick image become visible. When the user clicks the tick image, the item is saved,
and the visibility is reversed making the TextView visible again.

The custom TodoArrayAdapter handles the creation of EditTodoView for the ListView.

After implementing this and reading about the issues I faced while implementing this, it looks like embedding
a EditText inside a ListItem is not a great idea. Things do work as expected after I did
android:windowSoftInputMode="adjustPan" in the manifest xml with a slight UX awkwardness of the top items
getting moved out when the soft keyboard appears.

Known Issue:
If you add the same text as multiple entries and tried editing one of those entries that is further
down in the list, it would end up editing the first item with that text, as opposed to editing the
intended item. This is because of the way I search and find items based on the original text that was
edited as opposed to the position of the item that was edited.
