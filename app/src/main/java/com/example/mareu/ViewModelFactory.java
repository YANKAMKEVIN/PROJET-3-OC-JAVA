package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.api.FakeApiService;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.list.MeetingListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MeetingRepository meetingRepository;
    private static volatile ViewModelFactory factory;

    public ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    //TODO-- Regarder l'utilisation de synchronise
    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {
                    factory = new ViewModelFactory(new MeetingRepository(new FakeApiService()));
                }
            }
        }
        return factory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingListViewModel.class)) {
            return (T) new MeetingListViewModel(
                    meetingRepository
            );
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
