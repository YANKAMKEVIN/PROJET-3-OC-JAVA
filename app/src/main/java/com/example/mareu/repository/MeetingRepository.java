package com.example.mareu.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.mareu.api.ApiService;
import com.example.mareu.model.Meeting;

import java.util.List;
/**
 * Un dépôt qui gère les opérations liées aux réunions, telles que la récupération, l'ajout et la suppression
 * de réunions. Cette classe utilise un ApiService pour effectuer les opérations sur les données.
 */
public class MeetingRepository {
    private final ApiService apiService;

    /**
     * Construit un nouveau MeetingRepository avec le service API fourni.
     *
     * @param apiService Le service API à utiliser pour les opérations de données.
     */
    public MeetingRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Récupère les données des réunions sous forme d'objet LiveData.
     *
     * @return Un objet LiveData contenant la liste des réunions.
     */
    public LiveData<List<Meeting>> getMeetingsLiveData() {
        return apiService.getMeetingsLiveData();
    }

    /**
     * Ajoute une nouvelle réunion à la liste des réunions.
     *
     * @param meeting La réunion à ajouter.
     */
    public void addMeeting(@NonNull Meeting meeting) {apiService.addMeeting(meeting);}

    /**
     * Supprime une réunion de la liste des réunions en fonction de son ID.
     *
     * @param meetingId L'ID de la réunion à supprimer.
     */
    public void deleteMeeting(long meetingId) {
        apiService.deleteMeeting(meetingId);
    }
    /**
     * Génère une liste de fausses réunions pour les besoins de test et de démonstration.
     */
    private void generateFakeMeetings() {
        apiService.generateFakeMeetings();
    }
}
