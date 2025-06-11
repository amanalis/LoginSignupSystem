package com.example.loginsystem;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.loginsystem.adapter.RecyclerViewAdapter;
import com.example.loginsystem.data.MyDBHandler;
import com.example.loginsystem.model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragement extends Fragment {
    FloatingActionButton fab;

    MyDBHandler myDBHandler;
    ListView listView;
    Button addBtn;
    ArrayAdapter<String> arrayAdapter;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<Notes> notesArrayList;
    List<Notes> allNotes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragement newInstance(String param1, String param2) {
        HomeFragement fragment = new HomeFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_fragement, container, false);

        recyclerView = view.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        myDBHandler = new MyDBHandler(getContext());

        SharedPreferences getSharedPreferences = getContext().getSharedPreferences("demo", getContext().MODE_PRIVATE);
        String useremail = getSharedPreferences.getString("email", "email");

        notesArrayList = new ArrayList<>();
        allNotes = myDBHandler.getNotesByEmail(useremail);
        for (Notes notes : allNotes) {
            notesArrayList.add(notes);
        }

        //For recycler view
        recyclerViewAdapter = new RecyclerViewAdapter(getContext(), notesArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddNote.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void loadNotes(){
        SharedPreferences getSharedPreferences = getContext().getSharedPreferences("demo",MODE_PRIVATE);
        String email = getSharedPreferences.getString("email","email");

        notesArrayList.clear();
        allNotes = myDBHandler.getNotesByEmail(email);

        for(Notes notes : allNotes){
            notesArrayList.add(notes);
        }
        if (arrayAdapter == null) {
            recyclerViewAdapter = new RecyclerViewAdapter(getContext(), notesArrayList);
            recyclerView.setAdapter(recyclerViewAdapter);
        } else {
            recyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotes();
    }
}