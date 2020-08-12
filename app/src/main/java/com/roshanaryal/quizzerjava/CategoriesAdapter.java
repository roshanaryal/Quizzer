package com.roshanaryal.quizzerjava;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Viewholder> {

    private List<CategoriesModal> mCategoriesModalList;
    private Context mContext;

    public CategoriesAdapter(List<CategoriesModal> categoriesModalList, Context context) {
        mCategoriesModalList = categoriesModalList;
        mContext = context;
    }

    @NonNull
    @Override
    public CategoriesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.Viewholder holder, int position) {

        CategoriesModal current=mCategoriesModalList.get(position);

        holder.setData(current.getUrl(),current.getName(),current.getSets());
    }

    @Override
    public int getItemCount() {
        return mCategoriesModalList.size();

    }


    class Viewholder extends RecyclerView.ViewHolder {

        CircleImageView mImageView;
        TextView mTextView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imageCategories);
            mTextView = itemView.findViewById(R.id.textViewTitle);


        }

        private void setData(String Url, final String title, final int sets) {
            Glide.with(mContext).load(Url).into(mImageView);
            mTextView.setText(title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent setsIntent=new Intent(itemView.getContext(),SetsActivity.class);
                    setsIntent.putExtra("title",title);
                    setsIntent.putExtra("sets",sets);
                    itemView.getContext().startActivity(setsIntent);
                }
            });
        }
    }

}
