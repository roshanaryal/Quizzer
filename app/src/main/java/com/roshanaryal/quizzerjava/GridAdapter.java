package com.roshanaryal.quizzerjava;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class GridAdapter extends BaseAdapter {

    private int setNo=0;
    private String category="";

    public GridAdapter(int setNo,String c) {
        this.category=c;
        this.setNo = setNo;
    }

    @Override
    public int getCount() {
        return setNo;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        View view;

        if (convertView==null)
        {
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sets_item,parent,false);

        }
        else {
            view=convertView;
        }

        ( (TextView)view.findViewById(R.id.textView)).setText(String.valueOf(position+1));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(),QuestionActivity.class);
                intent.putExtra("setNo",setNo);
                intent.putExtra("category",category);
                parent.getContext().startActivity(intent);
            }
        });

        return view;
    }
}
