package com.example.loginsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginsystem.data.MyDBHandler;
import com.example.loginsystem.model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNote extends AppCompatActivity {
    EditText editTitle;
    EditText editContent;
    Button addNote;
    TextView shareEmail;
//    MyDBHandler dbHandler;

    boolean isEditMode = false;
    int noteId = -1;//default
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        SharedPreferences getSharedPreferences = getSharedPreferences("demo", MODE_PRIVATE);
        email = getSharedPreferences.getString("email", "default@gmail.com");

        editTitle = findViewById(R.id.addTitle);
        editContent = findViewById(R.id.addContent);
        addNote = findViewById(R.id.buttonAddNote);
        shareEmail = findViewById(R.id.sharedEmail);

        shareEmail.setText(email);

        MyDBHandler myDBHandler = new MyDBHandler(AddNote.this); // ✅ initialize it

        //chaeck if Note is Opened
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            isEditMode = true;
            noteId=intent.getIntExtra("id",-1);
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");

            editTitle.setText(title);
            editContent.setText(content);
            addNote.setText("Update Note");
        }


        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes notes = new Notes();
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(AddNote.this, "Please enter title and content", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success;

                if (isEditMode) {
                    success = myDBHandler.updateNote(noteId,email,title,content,timestamp);
                    Toast.makeText(AddNote.this, success? "Note added!":"Failed to update", Toast.LENGTH_SHORT).show();
                } else {
                    success =  myDBHandler.insertNote(email,title,content,timestamp);
                    Toast.makeText(AddNote.this, success ? "Note added!" : "Failed to add note", Toast.LENGTH_SHORT).show();
                }
                if(success) finish(); // Close this activity
            }
        });
    }
}