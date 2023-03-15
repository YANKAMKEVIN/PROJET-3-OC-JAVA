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


public class MeetingListAdapter extends ListAdapter<MeetingListViewStateItem, MeetingListAdapter.ViewHolder> {
    private final OnMeetingCLickListener listener;

    public MeetingListAdapter(OnMeetingCLickListener listener) {
        super(new MeetingDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView firstText;
        private final TextView secondText;
        private final ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstText = itemView.findViewById(R.id.meeting_name_hour_location);
            secondText = itemView.findViewById(R.id.meeting_participants);
            imageButton = itemView.findViewById(R.id.item_list_delete_button);
        }

        public void bind(MeetingListViewStateItem item, OnMeetingCLickListener listener) {
            firstText.setText(item.getName());
            secondText.setText(item.getParticipants().toString());
            imageButton.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }
    }

    private static class MeetingDiffCallback extends DiffUtil.ItemCallback<MeetingListViewStateItem> {

        @Override
        public boolean areItemsTheSame(@NonNull MeetingListViewStateItem oldItem, @NonNull MeetingListViewStateItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MeetingListViewStateItem oldItem, @NonNull MeetingListViewStateItem newItem) {
            return oldItem.equals(newItem);
        }
    }
}
