package com.example.mareu.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.ViewModelFactory;
import com.example.mareu.model.Meeting;

import java.util.Objects;

public class MeetingListFragment extends Fragment {

    private MeetingListViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private onMeetingCLickListener listener;
    public static MeetingListFragment newInstance() {
        return new MeetingListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingListViewModel.class);
        mRecyclerView = view.findViewById(R.id.recycler_cv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        listener = new onMeetingCLickListener() {
            @Override
            public void onDeleteMeetingClicked(Meeting meeting) {
                mViewModel.deleteMeeting(meeting);
                MeetingListAdapter monAdapter = new MeetingListAdapter( listener);
                mViewModel.getMeetings().observe(getViewLifecycleOwner(), meetings1 -> {
                    Log.d("KEVIN", String.valueOf(meetings1.size()));
                    monAdapter.submitList(meetings1);
                });
                mRecyclerView.setAdapter(monAdapter);
            }
        };

        MeetingListAdapter monAdapter = new MeetingListAdapter( listener);
        mViewModel.getMeetings().observe(getViewLifecycleOwner(), meetings1 -> {
            Log.d("KEVIN", String.valueOf(meetings1.size()));
            monAdapter.submitList(meetings1);
        });
        mRecyclerView.setAdapter(monAdapter);

        return view;
    }
}