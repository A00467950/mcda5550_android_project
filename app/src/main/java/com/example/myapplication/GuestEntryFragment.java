package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GuestEntryFragment extends Fragment implements ItemClickListener {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.guest_entry_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        int guestCount = Integer.parseInt(getArguments().getString("number of guests"));

        List<GuestDetail> guestListData = initGuestDetails(guestCount);

        RecyclerView recyclerView = view.findViewById(R.id.guest_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestEntryAdapter guestListAdapter = new GuestEntryAdapter(getActivity(), guestListData);
        recyclerView.setAdapter(guestListAdapter);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("GuestDetailsEntry", "Item clicked");
    }

    public List<GuestDetail> initGuestDetails(int guestCount) {
        List<GuestDetail> guests = IntStream.range(0, guestCount)
                .mapToObj(i -> new GuestDetail()).collect(Collectors.toList());

        return guests;
    }
}
