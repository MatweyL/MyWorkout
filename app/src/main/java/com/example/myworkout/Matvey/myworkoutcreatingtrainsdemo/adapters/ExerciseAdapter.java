package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.UpdateExercise;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Exercise;
import com.example.myworkout.R;

import java.util.List;


public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private Context ctx;
    private List<Exercise> exercises;
    private boolean clickable;
    public ExerciseAdapter(Context ctx, List<Exercise> exercises, boolean clickable) {
        this.ctx = ctx;
        this.exercises = exercises;
        this.clickable = clickable;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise ex = exercises.get(position);
        holder.textViewName.setText(ex.getName());
        holder.textViewNumberReps.setText(ex.getRepsNumber());
        holder.textViewNumberSets.setText(ex.getSetsNumber());
        holder.textViewTimeExercise.setText(ex.getTimeExercise());
        holder.textViewTimeRest.setText(ex.getTimeRest());
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder{
        TextView textViewName, textViewNumberReps, textViewNumberSets, textViewTimeExercise, textViewTimeRest;
        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.exerciseName);
            textViewNumberReps = itemView.findViewById(R.id.numberOfReps);
            textViewNumberSets = itemView.findViewById(R.id.numberOfSets);
            textViewTimeExercise = itemView.findViewById(R.id.timeOfExercise);
            textViewTimeRest = itemView.findViewById(R.id.timeOfRest);
            if (clickable) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Exercise exercise = exercises.get(getAdapterPosition());
                        Intent intent = new Intent(ctx, UpdateExercise.class);
                        intent.putExtra(Exercise.class.getSimpleName(), exercise);
                        ctx.startActivity(intent);
                    }
                });
            }
        }
    }
}
