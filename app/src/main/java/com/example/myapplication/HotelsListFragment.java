package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HotelsListFragment extends Fragment implements ItemClickListener {

    View view;
    TextView headingTextView;
    ProgressBar progressBar;
    List<HotelListData> userListResponseData;

    Button nextButton;

    Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //heading text view
        headingTextView = view.findViewById(R.id.heading_text_view);
        progressBar = view.findViewById(R.id.progress_bar);

        String guestName = getArguments().getString("guest name");
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");
        String numberOfGuests = getArguments().getString("number of guests");

        //Set up the header
        headingTextView.setText("Welcome " + guestName + "!, displaying hotel for " + numberOfGuests + " guests staying from " + checkInDate +
                " to " + checkOutDate);

        // Set up the RecyclerView
        List<HotelListData> hotelListData = getAvailableHotels();

        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), hotelListData);
        recyclerView.setAdapter(hotelListAdapter);

        hotelListAdapter.setClickListener(this);

        // getHotelsListsData();

        nextButton = view.findViewById(R.id.search_next_button);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("NextButtonClick", "Next button Clicked after search");

                bundle.putString("number of guests", numberOfGuests);
                //bundle.putString("hotel name", selectedHotel);
                bundle.putString("check in date", checkInDate);
                bundle.putString("check out date", checkOutDate);

                // set Fragment class Arguments
                GuestEntryFragment guestEntryFragment = new GuestEntryFragment();
                guestEntryFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(guestEntryFragment, "GUEST_ENTRY_FRAGMENT");
                fragmentTransaction.remove(HotelsListFragment.this);
                fragmentTransaction.replace(R.id.main_layout, guestEntryFragment);
                fragmentTransaction.addToBackStack("GUEST_ENTRY_FRAGMENT");
                fragmentTransaction.commit();
            }
        });
    }

    public ArrayList<HotelListData> initHotelListData() {
        ArrayList<HotelListData> list = new ArrayList<>();

        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true"));
        list.add(new HotelListData("Hotel Pearl", "500$", "false"));
        list.add(new HotelListData("Hotel Amano", "800$", "true"));
        list.add(new HotelListData("San Jones", "250$", "false"));
        list.add(new HotelListData("Halifax Regional Hotel", "2000$", "true"));
        list.add(new HotelListData("Hotel Pearl", "500$", "false"));
        list.add(new HotelListData("Hotel Amano", "800$", "true"));
        list.add(new HotelListData("San Jones", "250$", "false"));

        return list;
    }

    public List<HotelListData> getAvailableHotels() {
        HotelsRestAPI api = RestAPI.getClient();

        Call<List<HotelListData>> hotelsCall = api.getHotels();

        List<HotelListData> hotels = Collections.emptyList();

        try {
            hotels = hotelsCall.execute().body();
        } catch (Exception exp){
            exp.printStackTrace();
        }

        return  hotels;
    }

    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);

        //Bind the click listener
        hotelListAdapter.setClickListener(this);
    }

    @Override
    public void onClick(View view, int position) {
        String selectedHotel = initHotelListData().get(position).getHotel_name();
        this.bundle.putString("hotel name", selectedHotel);
        Log.d("HotelSelected", String.format("Hotel: %s selected from search list", selectedHotel));
    }
}

