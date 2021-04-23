package com.fit2081.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText msgText;
    Button sendBtn;
    ArrayList<String> messages;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view_id);
        msgText = findViewById(R.id.msg_id);
        sendBtn = findViewById(R.id.send_btn_id);

        messages = new ArrayList<>();


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ChatAdapter(messages);
        recyclerView.setAdapter(mAdapter);

//        Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("week8messages/fit2081");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                messages.add(dataSnapshot.getValue(String.class));
                mAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public void sendOnClick(View v) {
        myRef.push().setValue(msgText.getText().toString());
//        messages.add(msgText.getText().toString());
        Log.d("Week8ChatApp", "Add new message");

//        mAdapter.notifyDataSetChanged();
    }
}
