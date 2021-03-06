package com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.TrainActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.TrainExercisesActivity;
import com.example.myworkout.Matvey.myworkoutcreatingtrainsdemo.room.Train;
import com.example.myworkout.R;
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
        View view = LayoutInflater.from(ctx).inflate(R.layout.recycler_view_train2, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainAdapter.TrainViewHolder holder, int position) {
        Train tr = trains.get(position);
        holder.textViewName.setText(tr.getName());
        holder.textViewTargetMuscles.setText(tr.getTargetMuscles());
        Integer trTimeMinutes = Integer.parseInt(tr.getTimeOfTraining()) / 60;
        Integer trTimeSeconds = Integer.parseInt(tr.getTimeOfTraining()) % 60;
        holder.textViewTimeOfTrain.setText(Integer.toString(trTimeMinutes));
        holder.textViewTimeOfTrainSeconds.setText(Integer.toString(trTimeSeconds));
    }

    @Override
    public int getItemCount() {
        return trains.size();
    }

    public class TrainViewHolder  extends RecyclerView.ViewHolder {
        TextView textViewName, textViewTargetMuscles, textViewTimeOfTrain, textViewTimeOfTrainSeconds;
        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName1);
            textViewTargetMuscles = itemView.findViewById(R.id.textViewTargetMuscles1);
            textViewTimeOfTrain = itemView.findViewById(R.id.textViewTimeOfTraining1);
            textViewTimeOfTrainSeconds = itemView.findViewById(R.id.textViewTimeOfTrainingSeconds1);
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