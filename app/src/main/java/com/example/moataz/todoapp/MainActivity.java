package com.example.moataz.todoapp;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    AlertDialog alertDialog;
    EditText addNote;
    TextView saveNote, cancleNote;
    FloatingActionButton imageView;
    MyHelper myHelper;
    ArrayList<Item> arrayList;
    String currentDate;
    String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.add);
        recyclerView = findViewById(R.id.recycler);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });

    }

    private void showAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.new_note, null);
        builder.setView(v);
        addNote = v.findViewById(R.id.add_note);
        saveNote = v.findViewById(R.id.save_new_note);
        cancleNote = v.findViewById(R.id.cancle_new_note);
        alertDialog = builder.create();
        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note = addNote.getText().toString();
                Calendar calendar = Calendar.getInstance();
                currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                Item item = new Item(note, currentDate);
                myHelper = new MyHelper(MainActivity.this);
                myHelper.add(item);
                onResume();

                alertDialog.dismiss();

            }
        });
        cancleNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        alertDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyHelper myHelper1 = new MyHelper(MainActivity.this);
        arrayList = myHelper1.getData();
        itemAdapter = new ItemAdapter(MainActivity.this, arrayList);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

    }
}