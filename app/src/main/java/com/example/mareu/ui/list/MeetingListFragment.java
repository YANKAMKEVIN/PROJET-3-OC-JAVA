package com.example.mareu.ui.list;

import android.os.Bundle;
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

public class MeetingListFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_meeting_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MeetingListViewModel  mViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MeetingListViewModel.class);

        RecyclerView  mRecyclerView = view.findViewById(R.id.recycler_cv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        MeetingListAdapter adapter = new MeetingListAdapter(new OnMeetingCLickListener() {
            @Override
            public void onDeleteMeetingClicked(long meetingId) {
                mViewModel.onDeleteMeetingClicked(meetingId);
            }
        });
        mRecyclerView.setAdapter(adapter);

        mViewModel.getMeetingListViewStateItemLiveData().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}