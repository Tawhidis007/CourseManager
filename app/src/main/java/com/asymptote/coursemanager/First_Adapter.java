package com.asymptote.coursemanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class First_Adapter extends RecyclerView.Adapter<First_Adapter.ViewHolder> {


    Context context;
    static ArrayList<Courses> a;
    static ArrayList<Courses> b;

    bridge my_listener;


    public First_Adapter(Context context, ArrayList<Courses> a) {
        this.context = context;
        this.a = a;
        b = a;


    }

    interface bridge {
        void listener(int position);
    }

    void setlistener(bridge o) {
        my_listener = o;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.mycard, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        TextView tv = cardView.findViewById(R.id.card_text);
        TextView tv2 = cardView.findViewById(R.id.beauty_desc);

        tv.setText(a.get(position).course_name);
        tv2.setText(a.get(position).getDesc());

    }


    public void remover(int position) {

        AppDatabase.getAppDatabase(context).motherTableDAO().delete(AppDatabase.getAppDatabase(context).motherTableDAO()
                .getAll().get(position));
        a.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return a.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    remover(getAdapterPosition());
                    return true;
                }
            });
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (my_listener != null) {
                        my_listener.listener(getAdapterPosition());
                    }
                }
            });
        }

    }

    public void updateList(ArrayList<Courses> newList) {
        a = new ArrayList<>();
        a.addAll(newList);
        notifyDataSetChanged();
    }

}
