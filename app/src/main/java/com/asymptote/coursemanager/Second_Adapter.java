package com.asymptote.coursemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Second_Adapter extends RecyclerView.Adapter<Second_Adapter.ViewHolder2> {

    ArrayList<String> categories;
    Context context;
    bridge2 my_listener2;

    interface bridge2 {
        void listener2(int position);

    }

    void setListener2(bridge2 o) {
        my_listener2 = o;
    }

    public Second_Adapter(Context context, ArrayList<String> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new ViewHolder2(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        CardView cardView = holder.cardView;
        TextView tv = cardView.findViewById(R.id.cat_tv);
        tv.setText(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder2(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (my_listener2 != null) {
                        my_listener2.listener2(getAdapterPosition());
                    }
                }
            });
        }
    }
}
