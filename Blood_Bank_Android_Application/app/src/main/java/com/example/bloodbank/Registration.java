package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    Button signup;
    String selectedBloodGroup="";

    Spinner spinnerBloodGroup;
    EditText name,mail,password,birthdate,bloodgroup,phone,district;

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        DB = new DBHelper(this);

        signup=findViewById(R.id.signup);
        name=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        password=findViewById(R.id.password);
        birthdate=findViewById(R.id.birthdate);
        //bloodgroup=findViewById(R.id.bloodgroup);
        phone=findViewById(R.id.phone);
        district=findViewById(R.id.district);


        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);

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
    public void signupAction(View view){
        String names=name.getText().toString();
        String mails=mail.getText().toString();
        String passwords=password.getText().toString();
        String birthdates=birthdate.getText().toString();
        String bloodgroups=selectedBloodGroup;
        String phones=phone.getText().toString();
        String districts=district.getText().toString();




        if(names.isEmpty()) Toast.makeText(this,"Name Empty",Toast.LENGTH_SHORT).show();
        else if (!isValidEmail(mails)) {
            Toast.makeText(this,"Enter Valid Email",Toast.LENGTH_SHORT).show();
        }
        else{
            if(isValidPassword(passwords)){
                String dateFormat = "dd-MM-yyyy"; // Change the format according to your requirements

                if (isValidDate(birthdates, dateFormat)) {
                    if(bloodgroups.isEmpty()) {Toast.makeText(this,"Blood Group Empty",Toast.LENGTH_SHORT).show();}
                    else if (isValidPhoneNumber(phones)) {
                        if(districts.isEmpty()) Toast.makeText(this,"District Empty",Toast.LENGTH_SHORT).show();
                        else{
                            //check the mail and phone doesn't exists in the database
                            Cursor res = DB.getuserdata();

                            //if mail or phone exists then return not insert
                            if(res.getCount()>=1) {
                                while (res.moveToNext()) {
                                    String maildb = res.getString(2);
                                    String phonedb = res.getString(6);
                                    if (maildb.equals(mails)) {
                                        Toast.makeText(Registration.this, "Mail Already Exists", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (phonedb.equals(phones)) {
                                        Toast.makeText(Registration.this, "Phone Already Exists", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }
                            }


                            //insert date into database User table
                            Boolean checkinsertdata = DB.insertuserdata(names,mails,passwords,birthdates,bloodgroups,phones,districts,"NULL","0","Donar","0");
                            if(checkinsertdata == true){
                                Toast.makeText(Registration.this, "Sign Up Completed!!!", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(this,MainActivity.class);
                                startActivity(intent);
                            }

                            else
                                Toast.makeText(Registration.this, "Sign Up not Completed!!!", Toast.LENGTH_SHORT).show();


                        }
                    } else {
                        Toast.makeText(this,"Enter Valid BD Phone Number",Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this,"Enter Birthdate in Valid format",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
    public boolean isValidEmail(CharSequence email) {
        return (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
    public boolean isValidPassword(String password) {
        // Password length should be at least 8 characters
        if (password.length() < 8) {
            Toast.makeText(this,
                    "Password Should at least 8 character",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // Password should contain at least one digit
        if (!password.matches(".*\\d.*")) {
            Toast.makeText(this,"Password Should at least 1 digit",Toast.LENGTH_SHORT).show();
            return false;
        }

        // Password should contain at least one special character
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            Toast.makeText(this,"Password Should at least 1 special Character",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression to match the required format
        String regex = "^(017|018|019|015|013|014|016)\\d{8}$";

        // Compile the regular expression into a Pattern
        Pattern pattern = Pattern.compile(regex);

        // Match the pattern against the input phone number
        Matcher matcher = pattern.matcher(phoneNumber);

        // Return true if the phone number matches the pattern, otherwise false
        return matcher.matches();
    }
}