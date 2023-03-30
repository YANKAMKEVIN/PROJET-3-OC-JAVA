package com.example.mareu.ui.list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.mareu.SingleLiveEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.repository.MeetingRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe MeetingListViewModel est responsable de la gestion de l'état et des actions
 * associées à la liste des réunions. Elle interagit avec le MeetingRepository pour obtenir
 * les données des réunions et les transforme en MeetingListViewStateItem pour être utilisées
 * dans la vue.
 */
public class MeetingListViewModel extends ViewModel {
    @NonNull
    private final MeetingRepository meetingRepository;
    private final SingleLiveEvent<String> showEmptyListErrorEvent = new SingleLiveEvent<>();
    private String currentLocationFilter = null;
    private LocalDate currentDateFilter = null;

    /**
     * Construit un nouvel objet MeetingListViewModel en utilisant le MeetingRepository spécifié.
     *
     * @param meetingRepository le dépôt de données pour les réunions
     */
    public MeetingListViewModel(@NonNull MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    /**
     * Retourne l'événement pour afficher une erreur de liste vide.
     *
     * @return l'événement pour afficher une erreur de liste vide
     */
    public SingleLiveEvent<String> getShowEmptyListErrorEvent() {
        return showEmptyListErrorEvent;
    }

    /**
     * Retourne la liste des réunions sous forme de MeetingListViewStateItem LiveData.
     *
     * @return la liste des réunions sous forme de MeetingListViewStateItem LiveData
     */
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


    /**
     * Définit le filtre de localisation à utiliser lors de la récupération des réunions.
     *
     * @param location la localisation à filtrer, ou null pour ne pas filtrer par localisation
     */
    public void setLocationFilter(@Nullable String location) {
        currentLocationFilter = location;
    }

    /**
     * Définit le filtre de date à utiliser lors de la récupération des réunions.
     *
     * @param date la date à filtrer, ou null pour ne pas filtrer par date
     */
    public void setDateFilter(@Nullable LocalDate date) {
        currentDateFilter = date;
    }

    /**
     * Réinitialise les filtres de localisation et de date.
     */
    public void resetFilters() {
        currentLocationFilter = null;
        currentDateFilter = null;
    }

    /**
     * Supprime une réunion en fonction de l'ID de la réunion spécifié.
     *
     * @param meetingId l'ID de la réunion à supprimer
     */
    public void onDeleteMeetingClicked(long meetingId) {
        meetingRepository.deleteMeeting(meetingId);
    }
}