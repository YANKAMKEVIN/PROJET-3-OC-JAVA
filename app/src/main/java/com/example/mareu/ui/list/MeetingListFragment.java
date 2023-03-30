package com.example.mareu.ui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.utils.ViewModelFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.time.LocalDate;
import java.util.List;

/**
 * La classe MeetingListFragment est responsable de l'affichage de la liste des réunions.
 * Elle utilise le MeetingListViewModel pour interagir avec les données et mettre à jour
 * l'affichage en fonction des changements de données.
 */
public class MeetingListFragment extends Fragment {
    RecyclerView mRecyclerView;
    MeetingListViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enregistrez le fragment pour écouter les événements LocationFilterEvent
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Récupérez le ViewModel
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingListViewModel.class);

        // Configurez le RecyclerView
        mRecyclerView = view.findViewById(R.id.list_meetings_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        // Créez et définissez l'adaptateur avec une lambda pour OnMeetingClickListener
        MeetingListAdapter adapter = new MeetingListAdapter(mViewModel::onDeleteMeetingClicked);
        mRecyclerView.setAdapter(adapter);

        // Observez les changements de données
        // Initialisez l'observateur
        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), meetingListObserver);

        // Dans votre activité ou fragment
        mViewModel.getShowEmptyListErrorEvent().observe(getViewLifecycleOwner(), errorMessage -> {
            // Affichez le message d'erreur ici
            // Par exemple, vous pouvez utiliser un Toast ou un Snackbar
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
        });
        //showSeekBarForOneSecond();
    }

    private final Observer<List<MeetingListViewStateItem>> meetingListObserver = meetingListViewStateItems -> {
        MeetingListAdapter adapter = (MeetingListAdapter) mRecyclerView.getAdapter();
        adapter.submitList(meetingListViewStateItems);
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Filtre la liste des réunions en fonction du lieu.
     *
     * @param location Le lieu à utiliser pour filtrer la liste des réunions.
     */
    public void filterByLocation(String location) {
        // Show the seekbar for one second
        mViewModel.setLocationFilter(location);
        showSeekBarForOneSecond();
        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), meetingListObserver);

    }

    /**
     * Filtre la liste des réunions en fonction de la date.
     *
     * @param date La date à utiliser pour filtrer la liste des réunions.
     */
    public void filterByDate(LocalDate date) {
        mViewModel.setDateFilter(date);
        showSeekBarForOneSecond();
        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), meetingListObserver);

    }

    /**
     * Réinitialise les filtres et affiche toutes les réunions.
     */
    public void resetFilter() {
        mViewModel.resetFilters();
        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), meetingListObserver);
    }


    /**
     * Affiche une barre de progression pendant une seconde.
     */
    public void showSeekBarForOneSecond() {
        // Créer la seekbar
        ProgressBar spinner = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleLarge);
        spinner.setIndeterminate(true);

        // Ajouter la seekbar à la vue
        ViewGroup rootLayout = (ViewGroup) getView();
        rootLayout.addView(spinner);

        // Masquer la seekbar après 1 seconde
        spinner.postDelayed(() -> {
            rootLayout.removeView(spinner);
        }, 1000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationFilterEvent(LocationFilterEvent event) {
        filterByLocation(event.getLocation());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDateFilterEvent(DateFilterEvent event) {
        filterByDate(event.getDate());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onResetFilterEvent(ResetFilterEvent event) {
        resetFilter();
    }

}