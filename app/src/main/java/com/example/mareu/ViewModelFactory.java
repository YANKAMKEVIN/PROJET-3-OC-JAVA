package com.example.mareu;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.api.ApiService;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.MeetingListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory factory;

    public static ViewModelFactory getInstance() {
        if (factory == null) {
            synchronized (ViewModelFactory.class) {
                if (factory == null) {

                    /*factory = new ViewModelFactory(
                        //    new MeetingRepository(new ApiService())
                    );*/
                }
            }
        }

        return factory;
    }

    @NonNull
    private final MeetingRepository meetingRepository;

    public ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
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
