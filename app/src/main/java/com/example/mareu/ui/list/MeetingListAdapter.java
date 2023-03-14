package com.example.mareu.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;


public class MeetingListAdapter extends ListAdapter<Meeting, MeetingListAdapter.ViewHolder> {
    private onMeetingCLickListener lickListener;

    public MeetingListAdapter(onMeetingCLickListener lickListener) {
        super(new MeetingDiffCallback());
        this.lickListener = lickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Meeting meeting = getItem(position);
        holder.firstText.setText(meeting.getName());
        holder.secondText.setText(meeting.getParticipants().toString());
        holder.imageButton.setOnClickListener(l -> lickListener.onDeleteMeetingClicked(meeting));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstText;
        public TextView secondText;
        public ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            firstText = itemView.findViewById(R.id.meeting_name_hour_location);
            secondText = itemView.findViewById(R.id.meeting_participants);
            imageButton = itemView.findViewById(R.id.item_list_delete_button);
        }
    }

    private static class MeetingDiffCallback extends DiffUtil.ItemCallback<Meeting> {

        @Override
        public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
            return oldItem.equals(newItem);
        }
    }
}
