package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BloodPost extends AppCompatActivity {
    String selectedBloodGroup="",mail="";

    Spinner spinnerBloodGroup;
    DBHelper DB;

    EditText patientdisease,hospital,address,donatedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_post);
        DB=new DBHelper(this);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        patientdisease=findViewById(R.id.patientdisease);
        hospital=findViewById(R.id.hospital);
        address=findViewById(R.id.address);
        donatedate=findViewById(R.id.donatedate);



        Cursor res = DB.getlogindata();
        String mailp="",namep="";
        while(res.moveToNext()){
            namep=res.getString(1);
            mailp=res.getString(2);
        }
        mail=mailp;


        // Define blood group options
        String[] bloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};

        // Create ArrayAdapter using the blood group options and default layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, bloodGroups);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinnerBloodGroup.setAdapter(adapter);

        spinnerBloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                selectedBloodGroup = bloodGroups[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing if nothing is selected
            }
        });
    }
    public void postAction(View view){
        String patientdiseases=patientdisease.getText().toString();
        String hospitals=hospital.getText().toString();
        String addresss=address.getText().toString();
        String donatedates=donatedate.getText().toString();
        if(!patientdiseases.isEmpty() && !hospitals.isEmpty() && !addresss.isEmpty() && !donatedates.isEmpty()){
            String dateFormat = "dd-MM-yyyy";
            if(isValidDate(donatedates,dateFormat)){
                //Toast.makeText(BloodPost.this,mail+" "+patientdiseases+" "+hospitals+" "+addresss+" "+donatedates+" "+selectedBloodGroup,Toast.LENGTH_SHORT).show();
                boolean check=DB.insertbloodpostdata(mail,patientdiseases,selectedBloodGroup,hospitals,addresss,donatedates,"0");
                if(check){
                    Toast.makeText(BloodPost.this,"Post Done",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,deshboard.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(BloodPost.this,"Post not Done",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(BloodPost.this,"Enter Valid Date",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(BloodPost.this,"Enter all required info",Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isValidDate(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false); // Disable lenient mode to enforce strict date parsing

        try {
            Date date = sdf.parse(dateStr);
            return date != null;
        } catch (ParseException e) {
            return false;
        }
    }
}