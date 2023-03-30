package com.example.mareu.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.api.FakeApiService;
import com.example.mareu.repository.MeetingRepository;
import com.example.mareu.ui.add.AddMeetingViewModel;
import com.example.mareu.ui.list.MeetingListViewModel;

/**
 * ViewModelFactory est une classe permettant de créer et de gérer des instances de ViewModel
 * pour l'application Mareu.
 * Elle implémente l'interface ViewModelProvider.Factory pour fonctionner avec l'architecture
 * ViewModel d'Android.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final MeetingRepository meetingRepository;
    private static volatile ViewModelFactory factory;

    /**
     * Constructeur pour la classe ViewModelFactory.
     *
     * @param meetingRepository une instance de MeetingRepository
     */
    public ViewModelFactory(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    /**
     * Retourne une instance singleton de ViewModelFactory.
     *
     * @return une instance singleton de ViewModelFactory
     */
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

    /**
     * Crée une instance de ViewModel de la classe donnée en paramètre.
     *
     * @param modelClass la classe du ViewModel à créer
     * @return une instance de ViewModel de la classe donnée en paramètre
     * @throws IllegalArgumentException si la classe ViewModel donnée n'est pas prise en charge
     */
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MeetingListViewModel.class)) {
            return (T) new MeetingListViewModel(
                    meetingRepository
            );
        } else if (modelClass.isAssignableFrom(AddMeetingViewModel.class)) {
            return (T) new AddMeetingViewModel(
                    meetingRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
