package com.example.mareu.ui.list;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;

import java.util.ArrayList;
import java.util.List;

public class MeetingListViewModel extends ViewModel {
    @NonNull
    private MeetingRepository meetingRepository;


    public MeetingListViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public LiveData<List<MeetingListViewStateItem>> getMeetingListViewStateItemLiveData() {
        return Transformations.map(meetingRepository.getMeetingsLiveData(), meetings -> {
            List<MeetingListViewStateItem> meetingsViewStateItems = new ArrayList<>();
            for (Meeting meeting : meetings) {
                meetingsViewStateItems.add(
                        new MeetingListViewStateItem(
                                meeting.getId(),
                                meeting.getName(),
                                meeting.getParticipants()
                        )
                );
            }
            return meetingsViewStateItems;
        });
    }

    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }

    // TODO: hilt voir comment utiliser hilt pour faire de l'injection de dependance pour ne plus creer de viewModelFactory, il injectera lui meme le meetingRepository

}