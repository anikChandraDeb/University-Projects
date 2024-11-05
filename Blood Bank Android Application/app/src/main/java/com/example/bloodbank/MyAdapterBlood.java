package com.example.bloodbank;

import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterBlood extends RecyclerView.Adapter<MyAdapterBlood.MyViewHolder> {
    private Context context;

    DBHelper DB;
    private ArrayList<String> name, mail, patientdisease, bloodgroup, hospital, address, donatedate, phone,approve;

    public MyAdapterBlood(Context context, ArrayList<String> name, ArrayList<String> mail, ArrayList<String> patientdisease, ArrayList<String> bloodgroup, ArrayList<String> hospital, ArrayList<String> address, ArrayList<String> donatedate, ArrayList<String> phone,ArrayList<String> approve) {
        this.context = context;
        this.mail = mail;
        this.name = name;
        this.patientdisease = patientdisease;
        this.bloodgroup = bloodgroup;
        this.hospital = hospital;
        this.address = address;
        this.donatedate = donatedate;
        this.phone = phone;
        this.approve=approve;
        this.DB=new DBHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.bloodpostentry, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(name.get(position));
        holder.mail.setText(mail.get(position));
        holder.patientdisease.setText(patientdisease.get(position));
        holder.bloodgroup.setText(bloodgroup.get(position));
        holder.hospital.setText(hospital.get(position));
        holder.address.setText(address.get(position));
        holder.donatedate.setText(donatedate.get(position));
        holder.phone.setText(phone.get(position));
        holder.approve.setText(approve.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phone.get(position);
                String mailId = mail.get(position);
                String status=approve.get(position);
                String classname="Deshboard";
                status=status.toUpperCase();
                if(status.equals("NOT APPROVE")){

                    boolean check=DB.updatebloodpostdata(mailId,"1");
                    Toast.makeText(v.getContext(), "Approve Post", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), admindeshboard.class);
                    v.getContext().startActivity(intent);
                }
                else{
                    String send = phoneNumber + "#" + mailId+"#"+classname;
                    Intent intent = new Intent(v.getContext(), MailPhone.class);
                    intent.putExtra("MSG", send);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mail, patientdisease, bloodgroup, hospital, address, donatedate, phone,approve;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textname);
            mail = itemView.findViewById(R.id.textmail);
            patientdisease = itemView.findViewById(R.id.textpatientdisease);
            bloodgroup = itemView.findViewById(R.id.textbloodgroup);
            hospital = itemView.findViewById(R.id.texthospital);
            address = itemView.findViewById(R.id.textaddress);
            donatedate = itemView.findViewById(R.id.textdonatedate);
            phone = itemView.findViewById(R.id.textphone);
            approve = itemView.findViewById(R.id.textapprove);
        }
    }
}
