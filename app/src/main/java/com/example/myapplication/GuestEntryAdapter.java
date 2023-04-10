package com.example.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GuestEntryAdapter extends RecyclerView.Adapter<GuestEntryAdapter.ViewHolder> {
    private List<GuestDetail> guestDetails;
    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;


    GuestEntryAdapter(Context context, List<GuestDetail> guestDetails){
        this.layoutInflater = LayoutInflater.from(context);
        this.guestDetails = guestDetails;
    }

    public GuestEntryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = layoutInflater.inflate(R.layout.guest_entry_layout, parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(GuestEntryAdapter.ViewHolder holder, int position){
        final GuestDetail guestDetail = guestDetails.get(position);

        String guestName = guestDetail.getGuestName();
        String gender = guestDetail.getGender();

        holder.guestName.setText(guestName);
        holder.guestName.setHint("Guest " + (position + 1));

        holder.guestGender.setText(gender);
        holder.guestGender.setHint("Gender (M/F)");

        holder.guestName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence guestName, int i, int i1, int i2) {
                guestDetail.setGuestName(guestName.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        holder.guestGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence guestGender, int i, int i1, int i2) {
                guestDetail.setGender(guestGender.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public int getItemCount(){
        return guestDetails != null ? guestDetails.size() : 0;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        EditText guestName;
        EditText guestGender;

        public ViewHolder(View itemView){
            super(itemView);
            guestName = itemView.findViewById(R.id.guest_name_text_view);
            guestGender = itemView.findViewById(R.id.gender_text_view);

            itemView.setOnClickListener(this);
        }

        public void onClick(View view){
            if (clickListener != null)
                clickListener.onClick(view, getAdapterPosition());
        }
    }
}
