package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.StringTokenizer;

public class UpdateProfile extends AppCompatActivity {
    String selectedBloodGroup="",updateusermail,classname;
    DBHelper DB;
    Spinner spinnerBloodGroup;

    DatePicker datepicker;
    EditText name,password,birthdate,district,lastdonatedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        DB=new DBHelper(this);

        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        birthdate=findViewById(R.id.birthdate);
        district=findViewById(R.id.district);
        lastdonatedate=findViewById(R.id.lastdonatedate);

        Intent intent=getIntent();
        String msg=intent.getStringExtra("MAIL");
        StringTokenizer st = new StringTokenizer(msg,"#");


        int inx=0;
        while (st.hasMoreTokens()) {
            if(inx==0) {updateusermail=st.nextToken();inx++;}
            else if(inx==1){classname=st.nextToken();inx++;}
        }

        Toast.makeText(UpdateProfile.this, classname, Toast.LENGTH_SHORT).show();

    }
    public void updateAction(View view){
        String names=name.getText().toString();
        String passwords=password.getText().toString();
        String birthdates=birthdate.getText().toString();
        String districts=district.getText().toString();
        String lastdonatedates=lastdonatedate.getText().toString();
        //Toast.makeText(UpdateProfile.this, "Birthdates :"+birthdates, Toast.LENGTH_SHORT).show();

        if(!passwords.isEmpty() && !isValidPassword(passwords)) return;
        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(UpdateProfile.this, "No user Exits", Toast.LENGTH_SHORT).show();
            return;
        }
        //if mail or phone exists then return not insert
        int flag=0;
        while(res.moveToNext()){
            String namedb=res.getString(1);
            String maildb=res.getString(2);
            String passworddb=res.getString(3);
            String birthdatedb=res.getString(4);
            String bloodgroupdb=res.getString(5);
            String phonedb=res.getString(6);
            String districtdb=res.getString(7);
            String lastdonatedatedb=res.getString(8);
            String countdonateblooddb=res.getString(9);
            String typedb=res.getString(10);
            String approveddb=res.getString(11);
            if(maildb.equals(updateusermail)) {
                if(!names.isEmpty()) namedb=names;
                if(!passwords.isEmpty()) passworddb=passwords;
                if(!birthdates.isEmpty()) birthdatedb=birthdates;
                if(!districts.isEmpty()) districtdb=districts;
                if(!lastdonatedates.isEmpty() && !lastdonatedatedb.equals(lastdonatedates)){
                    lastdonatedatedb=lastdonatedates;
                    int cnt=Integer.valueOf(countdonateblooddb);
                    cnt++;
                    countdonateblooddb=String.valueOf(cnt);
                }
                DB.dropTable("Login");
                Boolean checkinsertdata = DB.insertlogindata(namedb,maildb,passworddb,birthdatedb,bloodgroupdb,phonedb,districtdb,lastdonatedatedb,countdonateblooddb,typedb,approveddb);
                if(checkinsertdata == true){
                    Toast.makeText(UpdateProfile.this, "Store in local", Toast.LENGTH_SHORT).show();

                }

                else
                    Toast.makeText(UpdateProfile.this, "Exists", Toast.LENGTH_SHORT).show();


                boolean check=DB.updateuserdata(namedb,maildb,passworddb,birthdatedb,bloodgroupdb,phonedb,districtdb,lastdonatedatedb,countdonateblooddb,typedb,approveddb);
                if(check == true){
                    Toast.makeText(UpdateProfile.this, "Store in user", Toast.LENGTH_SHORT).show();
                    if(classname.equals("user")){
                        Intent intent=new Intent(this,Profile.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(this,adminprofile.class);
                        startActivity(intent);
                    }
                }

                else
                    Toast.makeText(UpdateProfile.this, "Exists", Toast.LENGTH_SHORT).show();



                break;
            }

        }

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
}