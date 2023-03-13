package com.example.mareu.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;

import java.util.List;

public class MeetingListViewModel extends ViewModel {
    private MeetingRepository repository;
    private MutableLiveData<List<Meeting>> _mutableMeetings = new MutableLiveData<>();

    //TODO EST CE QUE LE SETVALUE DOIT SE FAIRE DANS LE CONSTRUCTEUR
    public MeetingListViewModel(MeetingRepository meetingRepository) {
        this.repository = meetingRepository;
        _mutableMeetings.setValue(repository.getMeetings());
    }
    public LiveData<List<Meeting>> getMeetings() {
        return _mutableMeetings;
    }

    // TODO: hilt voir comment utiliser hilt pour faire de l'injection de dependance pour ne plus creer de viewModelFactory, il injectera lui meme le meetingRepository

}