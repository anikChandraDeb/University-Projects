package com.example.bloodbank;

import android.content.Context;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MyAdapterBloodImpl extends MyAdapterBlood {
    public MyAdapterBloodImpl(Context context, ArrayList name,ArrayList mail,ArrayList patientdisease, ArrayList bloodgroup,ArrayList hospital, ArrayList address, ArrayList donatedate,ArrayList phone,ArrayList approve) {
        super(context, name,mail ,patientdisease, bloodgroup,hospital, address,donatedate, phone,approve);
    }
}
