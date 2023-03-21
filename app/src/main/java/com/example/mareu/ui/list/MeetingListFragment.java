package com.example.mareu.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MeetingListFragment extends Fragment {
    RecyclerView  mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enregistrez le fragment pour écouter les événements LocationFilterEvent
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // N'oubliez pas de vous désinscrire lorsque le fragment est détruit
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationFilterEvent(LocationFilterEvent event) {
        filterByLocation(event.getLocation());
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get the ViewModel
        MeetingListViewModel  mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingListViewModel.class);

        // Setup the RecyclerView
        mRecyclerView = view.findViewById(R.id.recycler_cv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        // Create and set the adapter with lambda for OnMeetingClickListener
        MeetingListAdapter adapter = new MeetingListAdapter(mViewModel::onDeleteMeetingClicked);
        mRecyclerView.setAdapter(adapter);

        // Observe data changes
        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
    }

    public void filterByLocation(String location) {
        Log.d("KEVIN",""+location);
        MeetingListViewModel viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingListViewModel.class);
        viewModel.getMeetingListViewStateItemLiveDataByLocation(location).observe(getViewLifecycleOwner(), meetingListViewStateItems -> {
            MeetingListAdapter adapter = (MeetingListAdapter) mRecyclerView.getAdapter();
            adapter.submitList(meetingListViewStateItems);
        });
    }

}