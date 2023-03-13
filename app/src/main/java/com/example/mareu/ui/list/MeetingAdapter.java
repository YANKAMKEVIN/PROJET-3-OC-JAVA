package com.example.mareu.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;

import java.util.List;


public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder> {
    private List<Meeting> meetings;

    public MeetingAdapter(List<Meeting> meetings) {
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public ViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.firstText.setText(meeting.getName());
        holder.secondText.setText(meeting.getParticipants().toString());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //TODO utilise le viewBinding ici à la place du BindView
        public TextView firstText;
        public TextView secondText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstText = itemView.findViewById(R.id.meeting_name_hour_location);
            secondText = itemView.findViewById(R.id.meeting_participants);
        }
    }
}
