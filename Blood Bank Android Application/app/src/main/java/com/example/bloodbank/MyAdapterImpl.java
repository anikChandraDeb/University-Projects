package com.example.bloodbank;

import android.content.Context;

import java.util.ArrayList;

public class MyAdapterImpl extends MyAdapter {
    public MyAdapterImpl(Context context, ArrayList name,ArrayList mail, ArrayList bloodgroup, ArrayList district, ArrayList phone,ArrayList approve,String classname) {
        super(context, name,mail , bloodgroup, district, phone,approve,classname);
    }
}
