package com.example.mareu.ui.list;

/**
 * L'interface OnMeetingClickListener définit les méthodes de rappel à utiliser lorsqu'une
 * interaction avec une réunion se produit dans l'interface utilisateur, par exemple lorsqu'un
 * utilisateur clique sur le bouton de suppression d'une réunion.
 */
public interface OnMeetingCLickListener {
    /**
     * Cette méthode est appelée lorsque l'utilisateur clique sur le bouton de suppression d'une réunion.
     *
     * @param meetingId l'ID de la réunion à supprimer
     */
    void onDeleteMeetingClicked(long meetingId);
}
