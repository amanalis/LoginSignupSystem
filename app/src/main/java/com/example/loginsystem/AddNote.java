package com.example.loginsystem;

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
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        SharedPreferences getSharedPreferences = getSharedPreferences("demo",MODE_PRIVATE);
        String email = getSharedPreferences.getString("email","email");

        editTitle = findViewById(R.id.addTitle);
        editContent = findViewById(R.id.addContent);
        addNote = findViewById(R.id.buttonAddNote);
        shareEmail = findViewById(R.id.sharedEmail);

        shareEmail.setText(email);


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

                boolean success = dbHandler.insertNote(email, title, content, timestamp);

                if (success) {
                    Toast.makeText(AddNote.this, "Note added!", Toast.LENGTH_SHORT).show();
                    finish(); // Close this activity
                } else {
                    Toast.makeText(AddNote.this, "Failed to add note", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}