package com.example.bloodbank;

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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name,mail,bloodgroup,district,phone,approve;

    private  String classname;
    DBHelper DB;
    public MyAdapter(Context context, ArrayList<String> name, ArrayList<String> mail, ArrayList<String> bloodgroup, ArrayList<String> district, ArrayList<String> phone, ArrayList<String> approve, String classname) {
        this.context = context;
        this.mail = mail;
        this.name = name;
        this.bloodgroup = bloodgroup;
        this.district = district;
        this.phone = phone;
        this.approve = approve;
        this.classname = classname; // Add classname as a field in your adapter
        this.DB=new DBHelper(context);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        holder.mail.setText(String.valueOf(mail.get(position)));
        holder.bloodgroup.setText(String.valueOf(bloodgroup.get(position)));
        holder.district.setText(String.valueOf(district.get(position)));
        holder.phone.setText(String.valueOf(phone.get(position)));
        holder.approve.setText(String.valueOf(approve.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(classname.equals("approve")){
                    String mailId=String.valueOf(mail.get(position));
                    boolean check=DB.updateuserdata(mailId,"1");
                    Toast.makeText(v.getContext(),"Approve",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(),adminuser.class);
                    intent.putExtra("MSG","approve");
                    v.getContext().startActivity(intent);
                }
                else if(classname.equals("block")){
                    String mailId=String.valueOf(mail.get(position));
                    boolean check=DB.updateuserdata(mailId,"0");
                    Toast.makeText(v.getContext(),"Block",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(),adminuser.class);
                    intent.putExtra("MSG","block");
                    v.getContext().startActivity(intent);
                }
                else{
                    String phoneNumber = String.valueOf(phone.get(position));
                    String mailId=String.valueOf(mail.get(position));
                    String classname="SearchBlood";
                    String send=phoneNumber+"#"+mailId+"#"+classname;
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
    public class MyViewHolder extends  RecyclerView.ViewHolder{
        public TextView name,mail,bloodgroup,district,phone,approve;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textname);
            mail=itemView.findViewById(R.id.textmail);
            bloodgroup=itemView.findViewById(R.id.textbloodgroup);
            district=itemView.findViewById(R.id.textdistrict);
            phone=itemView.findViewById(R.id.textphone);
            approve=itemView.findViewById(R.id.textapprove);
        }
    }
}
