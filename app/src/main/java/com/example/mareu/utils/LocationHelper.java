package com.example.mareu.utils;

import com.example.mareu.R;

/**
 * La classe LocationHelper fournit des méthodes utilitaires pour gérer les salles de réunion.
 */
public class LocationHelper {

    /**
     * Retourne l'ID de la couleur correspondant à la salle de réunion spécifiée.
     *
     * @param location le nom de la salle de réunion
     * @return l'ID de la couleur associée à la salle de réunion
     */
    public static int getColorIdFromLocation(String location) {
        switch (location) {
            case "Salle 1":
                return R.color.room_one;
            case "Salle 2":
                return R.color.room_two;
            case "Salle 3":
                return R.color.room_three;
            case "Salle 4":
                return R.color.room_four;
            case "Salle 5":
                return R.color.room_five;
            case "Salle 6":
                return R.color.room_six;
            case "Salle 7":
                return R.color.room_seven;
            case "Salle 8":
                return R.color.room_eight;
            case "Salle 9":
                return R.color.room_nine;
            default:
                return R.color.room_ten;
        }
    }
}
