package com.roshanaryal.quizzerjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.viewholder> {

    private List<QuestionModal> mList;

    public BookmarksAdapter(List<QuestionModal> list) {
        mList = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.bookmarks_item,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
       QuestionModal current=mList.get(position);
       holder.setDate(current.getQuestion(),current.getCorrectAnswer(),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{

       private TextView question,answer;
       private ImageButton deleteButton;


        public viewholder(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.question);
            answer=itemView.findViewById(R.id.answer);
            deleteButton=itemView.findViewById(R.id.deleteButton);
        }

        private void setDate(String question, String answer, final int position){
            this.question.setText(question);
            this.answer.setText(answer);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                   notifyDataSetChanged();
                }
            });
        }
    }
}
