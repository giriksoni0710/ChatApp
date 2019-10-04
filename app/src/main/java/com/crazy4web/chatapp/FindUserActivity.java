package com.crazy4web.chatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

public class FindUserActivity extends AppCompatActivity {

    private RecyclerView muserList;
    private RecyclerView.LayoutManager muserListManager;
    private RecyclerView.Adapter muserListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        initializeRecyclerView();

    }

    private void initializeRecyclerView() {

        muserList = findViewById(R.id.userList);

        muserList.setNestedScrollingEnabled(false);
        muserList.setHasFixedSize(false);

        muserListManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL,false);

        muserList.setLayoutManager(muserListManager);
    }
}
