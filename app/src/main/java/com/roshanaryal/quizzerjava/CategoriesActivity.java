package com.roshanaryal.quizzerjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriesActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private CategoriesAdapter mAdapter;
  private List<CategoriesModal> mCategoriesModalList= new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        if (haveNetworkConnection()) {

            loadingDialog = new Dialog(this);
            loadingDialog.setContentView(R.layout.loading_dialog);
            Objects.requireNonNull(loadingDialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
            loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            loadingDialog.show();

            myRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        mCategoriesModalList.add(dataSnapshot1.getValue(CategoriesModal.class));
                        mAdapter.notifyDataSetChanged();
                    }
                    loadingDialog.dismiss();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CategoriesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                    finish();
                }
            });
        }
        else {

            mCategoriesModalList.add(new CategoriesModal("Youtube",2,""));
            mCategoriesModalList.add(new CategoriesModal("Facebook",1,""));
            mCategoriesModalList.add(new CategoriesModal("Science",3,""));
            mCategoriesModalList.add(new CategoriesModal("History",2,""));

        }

        mAdapter=new CategoriesAdapter(mCategoriesModalList,this);
        mRecyclerView.setAdapter(mAdapter);





    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        try {

            assert cm != null;
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
