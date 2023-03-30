package com.example.mareu.ui.list;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.utils.LocationHelper;

/**
 * Un adaptateur pour afficher les éléments de la liste des réunions dans un RecyclerView.
 */
public class MeetingListAdapter extends ListAdapter<MeetingListViewStateItem, MeetingListAdapter.ViewHolder> {
    private final OnMeetingCLickListener listener;

    /**
     * Construit un nouvel adaptateur MeetingListAdapter.
     *
     * @param listener Le listener pour les événements de clic sur les réunions.
     */
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

    /**
     * Un ViewHolder pour afficher les éléments d'une réunion dans un RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView meetingInfos;
        private final TextView participants;
        private final ImageButton imageButton;
        private ImageView imageCircle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingInfos = itemView.findViewById(R.id.meeting_name_hour_location);
            participants = itemView.findViewById(R.id.meeting_participants);
            imageButton = itemView.findViewById(R.id.item_list_delete_button);
            imageCircle = itemView.findViewById(R.id.room_color);
        }


        /**
         * Lie les données de l'élément MeetingListViewStateItem au ViewHolder.
         *
         * @param item     L'élément MeetingListViewStateItem contenant les données de la réunion.
         * @param listener Le listener pour les événements de clic sur les réunions.
         */
        public void bind(MeetingListViewStateItem item, OnMeetingCLickListener listener) {
            meetingInfos.setText(String.join(" - ", item.getName(), item.getStartTime().toString(), item.getLocation()));
            String participantsString = item.getParticipants().toString();
            participantsString = participantsString.substring(1, participantsString.length() - 1);
            participants.setText(participantsString);
            imageButton.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
            Drawable drawable = ContextCompat.getDrawable(itemView.getContext(), R.drawable.room);
            drawable.setColorFilter(ContextCompat.getColor(itemView.getContext(), LocationHelper.getColorIdFromLocation(item.getLocation())), PorterDuff.Mode.SRC_IN);
            imageCircle.setBackground(drawable);
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
