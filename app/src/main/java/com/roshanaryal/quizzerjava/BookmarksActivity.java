package com.roshanaryal.quizzerjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=findViewById(R.id.rvBookmarks);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        List<QuestionModal> list=new ArrayList<>();
        list.add(new QuestionModal("This is sample question  1","optionA","optionB","optionC","optionD","optionD",2));
        list.add(new QuestionModal("This is sample question 2","optionA","optionB","optionC","optionD","optionD",2));
        list.add(new QuestionModal("This is sample question 3","optionA","optionB","optionC","optionD","optionD",2));
        list.add(new QuestionModal("This is sample question 4","optionA","optionB","optionC","optionD","optionD",2));
        list.add(new QuestionModal("This is sample question 5","optionA","optionB","optionC","optionD","optionD",2));
        list.add(new QuestionModal("This is sample question 6","optionA","optionB","optionC","optionD","optionD",2));

        BookmarksAdapter adapter=new BookmarksAdapter(list);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
