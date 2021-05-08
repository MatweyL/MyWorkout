package com.example.myworkoutcreatingtrainsdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkoutcreatingtrainsdemo.R;
import com.example.myworkoutcreatingtrainsdemo.TrainActivity;
import com.example.myworkoutcreatingtrainsdemo.TrainExercisesActivity;
import com.example.myworkoutcreatingtrainsdemo.room.Train;

import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder>{
    private Context ctx;
    private List<Train> trains;
    private boolean training;

    public TrainAdapter(Context ctx, List<Train> trains, boolean training) {
        this.ctx = ctx;
        this.trains = trains;
        this.training = training;
    }

    @NonNull
    @Override
    public TrainAdapter.TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_train, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.TrainViewHolder holder, int position) {
        Train tr = trains.get(position);
        holder.textViewName.setText(tr.getName());
        holder.textViewTargetMuscles.setText(tr.getTargetMuscles());
        String trTime = Double.toString(Double.parseDouble(tr.getTimeOfTraining()) / 60);
        holder.textViewTimeOfTrain.setText(trTime);
    }

    @Override
    public int getItemCount() {
        return trains.size();
    }

    public class TrainViewHolder  extends RecyclerView.ViewHolder {
        TextView textViewName, textViewTargetMuscles, textViewTimeOfTrain;
        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewTargetMuscles = itemView.findViewById(R.id.textViewTargetMuscles);
            textViewTimeOfTrain = itemView.findViewById(R.id.textViewTimeOfTraining);
            itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (training == false) {
                            Train train = trains.get(getAdapterPosition());
                            Intent intent = new Intent(ctx, TrainExercisesActivity.class);
                            intent.putExtra(Train.class.getSimpleName(), train);
                            ctx.startActivity(intent);
                        }
                        else {
                            Train train = trains.get(getAdapterPosition());
                            Intent intent = new Intent(ctx, TrainActivity.class);
                            intent.putExtra(Train.class.getSimpleName(), train);
                            ctx.startActivity(intent);
                        }
                    }
                });


        }
    }
}
