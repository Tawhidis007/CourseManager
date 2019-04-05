package com.asymptote.coursemanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Usis_Adapter extends RecyclerView.Adapter<Usis_Adapter.ViewHolder> {

    Context context;
    ArrayList<UsisTable> usis_list;
    ArrayList<UsisTable> selection;


    public Usis_Adapter(Context context, ArrayList<UsisTable> usis_list) {
        this.context = context;
        this.usis_list = usis_list;
        if (selection == null) {
            selection = new ArrayList<>();
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.usis_card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        Button usis_course_add_btn = cardView.findViewById(R.id.usis_course_add_btn);
        TextView usis_card_tv = cardView.findViewById(R.id.usis_card_tv);
        TextView usis_course_desc = cardView.findViewById(R.id.usis_course_desc);
        TextView usis_faculty = cardView.findViewById(R.id.usis_faculty);
        TextView class_days = cardView.findViewById(R.id.class_days);
        TextView class_time = cardView.findViewById(R.id.class_time);
        TextView lab_day = cardView.findViewById(R.id.lab_day);
        TextView lab_time = cardView.findViewById(R.id.lab_time);

        usis_card_tv.setText(usis_list.get(position).getCourse_code());
        usis_course_desc.setText(usis_list.get(position).getCourse_name());
        usis_faculty.setText(usis_list.get(position).getFaculty());
        class_days.setText(usis_list.get(position).getClass_days());
        class_time.setText(usis_list.get(position).getClass_time());
        lab_day.setText(usis_list.get(position).getLab_day());
        lab_time.setText(usis_list.get(position).getLab_time());

        usis_course_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selection != null) {
                    if (!ase(selection, usis_list.get(position).getCourse_code())) {
                        if (!class_clash_check(selection, usis_list.get(position).getClass_days(),
                                usis_list.get(position).getClass_time())) {
                            if (!lab_clash_check(selection, usis_list.get(position)
                                    .getLab_day(), usis_list.get(position).getLab_time())) {
                                selection.add(usis_list.get(position));
                                MainActivity.o.add(usis_list.get(position).getCourse_code());
                            } else {
                                Toast.makeText(context, "Sorry! You have a lab-clash!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Sorry! You have a class already at that time!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Sorry! You already have this course taken!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    selection.add(usis_list.get(position));
                    MainActivity.o.add(usis_list.get(position).getCourse_code());
                }
                Log.d("taken", usis_list.get(position).getCourse_code());
            }
        });
    }

    @Override
    public int getItemCount() {
        return usis_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    private boolean ase(ArrayList<UsisTable> check, String kon_course) {
        for (UsisTable u : check) {
            if (kon_course.equals(u.getCourse_code())) {
                return true;
            }
        }
        return false;
    }

    private boolean class_clash_check(ArrayList<UsisTable> check, String classDay, String classTime) {
        for (UsisTable u : check) {
            if (classDay.equals(u.getClass_days()) && classTime.equals(u.getClass_time())) {
                return true;
            }
        }
        return false;
    }

    private boolean lab_clash_check(ArrayList<UsisTable> check, String labDay, String labTime) {
        for (UsisTable u : check) {
            if (labDay.equals(u.getLab_day()) && labTime.equals(u.getLab_time())) {
                return true;
            }
        }
        return false;
    }
}
