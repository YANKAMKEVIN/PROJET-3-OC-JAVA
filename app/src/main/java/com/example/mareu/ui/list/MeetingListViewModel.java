package com.example.mareu.ui.list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.SingleLiveEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MeetingListViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;
    private final SingleLiveEvent<String> showEmptyListErrorEvent = new SingleLiveEvent<>();
    private String currentLocationFilter = null;
    private LocalDate currentDateFilter = null;
    public SingleLiveEvent<String> getShowEmptyListErrorEvent() {
        return showEmptyListErrorEvent;
    }
    public MeetingListViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public LiveData<List<MeetingListViewStateItem>> getMeetingListViewStateItemLiveData() {

        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingListViewStateItem> meetingsViewStateItems = new ArrayList<>();
            for (Meeting meeting : meetings) {
                boolean matchesLocation = (currentLocationFilter == null) || meeting.getLocation().equalsIgnoreCase(currentLocationFilter);
                boolean matchesDate = (currentDateFilter == null) || meeting.getDate().equals(currentDateFilter);

                if (matchesLocation && matchesDate) {
                    meetingsViewStateItems.add(
                            new MeetingListViewStateItem(
                                    meeting.getId(),
                                    meeting.getName(),
                                    meeting.getLocation(),
                                    meeting.getStartTime(),
                                    meeting.getParticipants(),
                                    meeting.getDate()
                            )
                    );
                }
            }

            if (meetingsViewStateItems.isEmpty()) {
                String errorMessage;
                if (currentLocationFilter != null && currentDateFilter != null) {
                    errorMessage = "Aucune réunion trouvée pour ce lieu et cette date";
                } else if (currentLocationFilter != null) {
                    errorMessage = "Aucune réunion trouvée pour ce lieu";
                } else if (currentDateFilter != null) {
                    errorMessage = "Aucune réunion trouvée pour cette date";
                } else {
                    errorMessage = null;
                }
                if (errorMessage != null) {
                    showEmptyListErrorEvent.setValue(errorMessage);
                }
            }

            return meetingsViewStateItems;
        });
    }

    public void setLocationFilter(@Nullable String location) {
        currentLocationFilter = location;
    }

    public void setDateFilter(@Nullable LocalDate date) {
        currentDateFilter = date;
    }

    public void resetFilters() {
        currentLocationFilter = null;
        currentDateFilter = null;
    }
    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }
}