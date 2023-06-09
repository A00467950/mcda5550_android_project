package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import retrofit2.Call;

public class GuestEntryFragment extends Fragment implements ItemClickListener {
    View view;

    TextView hotelSelectedTextView;

    Button book;

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

        hotelSelectedTextView = view.findViewById(R.id.hotel_select_text_view);
        String guests = getArguments().getString("number of guests");
        String hotelName = getArguments().getString("hotel name");
        String checkInDate = getArguments().getString("check in date");
        String checkOutDate = getArguments().getString("check out date");

        Log.d("GuestEntryAdapter", "The start date is: " + checkInDate);

        String bookingMessage = String.format("Booking from: %s to %s form %s guests in Hotel: %s",
                checkInDate, checkOutDate, guests, hotelName);

        hotelSelectedTextView.setText(bookingMessage);

        int guestCount = 1;

        if(guests != null && !"".equals(guests)){
            guestCount = Integer.parseInt(guests);
        }

        List<GuestDetail> guestListData = initGuestDetails(guestCount);

        RecyclerView recyclerView = view.findViewById(R.id.guest_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        GuestEntryAdapter guestListAdapter = new GuestEntryAdapter(getActivity(), guestListData);
        recyclerView.setAdapter(guestListAdapter);

        book = view.findViewById(R.id.book_button);

        book.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HotelsRestAPI api = RestAPI.getClient();
                BookingDetails hotel = new BookingDetails("Selected Hotel");
                Call<String> bookingCall = api.book(hotel);

                String bookingId = null;
                try {
                    bookingId = bookingCall.execute().body();
                } catch (Exception exp){
                    exp.printStackTrace();
                }

                Log.d("GuestEntryFragment", "The booking id is: " + bookingId);

                Bundle bundle = new Bundle();
                bundle.putString("bookingId", bookingId);

                BookingConfirmFragment bookingConfirmFragment = new BookingConfirmFragment();
                bookingConfirmFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(bookingConfirmFragment, "BOOKING_CONFIRM_FRAGMENT");
                fragmentTransaction.remove(GuestEntryFragment.this);
                fragmentTransaction.replace(R.id.main_layout, bookingConfirmFragment);
                fragmentTransaction.addToBackStack("BOOKING_CONFIRM_FRAGMENT");
                fragmentTransaction.commit();
            }
        });
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
