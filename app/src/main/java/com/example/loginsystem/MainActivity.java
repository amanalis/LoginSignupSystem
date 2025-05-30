package com.example.loginsystem;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginsystem.adapter.RecyclerViewAdapter;
import com.example.loginsystem.data.MyDBHandler;
import com.example.loginsystem.model.Notes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyDBHandler myDBHandler;
    ListView listView;
    Button addBtn;
    ArrayAdapter<String> arrayAdapter;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Notes> notesArrayList;
    List<Notes> allNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        myDBHandler = new MyDBHandler(MainActivity.this);

        SharedPreferences getSharedPreferences = getSharedPreferences("demo",MODE_PRIVATE);
        String useremail = getSharedPreferences.getString("email","email");

        notesArrayList = new ArrayList<>();
        allNotes = myDBHandler.getNotesByEmail(useremail);
        for(Notes notes : allNotes){
            notesArrayList.add(notes);
        }

        //For recycler view
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, notesArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        Button btnAdd = findViewById(R.id.addBtn);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNote.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }

    private void loadNotes(){
        SharedPreferences getSharedPreferences = getSharedPreferences("demo",MODE_PRIVATE);
        String email = getSharedPreferences.getString("email","email");

        notesArrayList.clear();
        allNotes = myDBHandler.getNotesByEmail(email);

        for(Notes notes : allNotes){
            notesArrayList.add(notes);
        }
        if (arrayAdapter == null) {
            recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, notesArrayList);
            recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false) // prevents back press from closing the dialog
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sharedPreferences = getSharedPreferences("demo",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("rememberMe",false);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}