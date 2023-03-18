package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HotelSearchFragment extends Fragment {
    ConstraintLayout mainLayout;
    View view;
    TextView titleTextView;
    TextView searchConfirmTextView;
    DatePicker checkInDatePicker;
    DatePicker checkOutDatePicker;
    EditText guestsCountEditText;
    EditText nameEditText;
    Button confirmSearchBtn;
    EditText confirmSearchTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.hotel_search_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainLayout = view.findViewById(R.id.main_layout);
        searchConfirmTextView = view.findViewById(R.id.search_confirm_text_view);
        confirmSearchBtn = view.findViewById(R.id.confirm_my_search_button);
        checkInDatePicker = view.findViewById(R.id.checkin_date_picker_view);
        checkOutDatePicker = view.findViewById(R.id.checkout_date_picker_view);
        guestsCountEditText = view.findViewById(R.id.guests_count_edit_text);
        nameEditText = view.findViewById(R.id.name_edit_text);

        confirmSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String checkInDate = getDateFromCalendar(checkInDatePicker);
                String checkOutDate = getDateFromCalendar(checkOutDatePicker);
                //Get input of guests count
                String numberOfGuests = guestsCountEditText.getText().toString();
                String guestName = nameEditText.getText().toString();

                // Saving into shared preferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("myPerf", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("nameKey", guestName);
                editor.putString("guestsCount", numberOfGuests);
                editor.commit();

                searchConfirmTextView.setText("Dear Customer, Your check in date is " + checkInDate + ", " +
                        "your checkout date is " + checkOutDate + ".The number of guests are " + numberOfGuests);
            }
        });

    }

    private String getDateFromCalendar(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        return formattedDate;
    }
}
