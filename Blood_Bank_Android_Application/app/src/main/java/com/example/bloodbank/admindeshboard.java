package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class admindeshboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    LinearLayout linearLayout;
    NavigationView navigationView;

    RecyclerView recyclerView;
    DBHelper DB;

    MyAdapterBlood adapter;

    ArrayList<String> name,Mail,patientdisease,bloodGroupList,hospital,address,donatedate,phone,approve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindeshboard);




        DB=new DBHelper(this);





        drawerLayout=findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);

        navigationView=findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);

        TextView mailprint=headerView.findViewById(R.id.mailprint);




        Cursor res = DB.getlogindata();
        String mailp="",namep="";
        while(res.moveToNext()){
            namep=res.getString(1);
            mailp=res.getString(2);
        }
        String print=namep+"\n"+mailp;
        mailprint.setText(print);

        Intent intent1=new Intent(this,MainActivity.class);
        Intent intent2=new Intent(this,addadmin.class);
        Intent intent3=new Intent(this,adminprofile.class);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                admindeshboard.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId()==R.id.share){
                    Toast.makeText(admindeshboard.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.approvepost){
                    Toast.makeText(admindeshboard.this,"Home",Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.approveuser){
                    Toast.makeText(admindeshboard.this,"User Approve",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(admindeshboard.this,adminuser.class);
                    intent.putExtra("MSG","approve");
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.blockuser){
                    Toast.makeText(admindeshboard.this,"Block User",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(admindeshboard.this,adminuser.class);
                    intent.putExtra("MSG","block");
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.addadmin){
                    Toast.makeText(admindeshboard.this,"Add Hospitals",Toast.LENGTH_SHORT).show();
                    startActivity(intent2);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(admindeshboard.this,"Add Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(admindeshboard.this,"See Profile",Toast.LENGTH_SHORT).show();
                    startActivity(intent3);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(admindeshboard.this,"Logout",Toast.LENGTH_SHORT).show();
                    DB.dropTable("Login");

                    startActivity(intent1);
                }
                return false;
            }


        });

        name=new ArrayList<>();
        Mail=new ArrayList<>();
        patientdisease=new ArrayList<>();
        bloodGroupList=new ArrayList<>();
        hospital=new ArrayList<>();
        address=new ArrayList<>();
        donatedate=new ArrayList<>();
        phone=new ArrayList<>();
        approve=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new MyAdapterBlood(this,name,Mail,patientdisease,bloodGroupList,hospital,address,donatedate,phone,approve);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();

    }



    public void displaydata(){
        //Toast.makeText(this,bg+" "+dis,Toast.LENGTH_SHORT).show();
        Cursor res = DB.getbloodpostdata();
        if(res.getCount()==0){
            Toast.makeText(admindeshboard.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){

            if(res.getString(7).equals("0")){

                String date1 = res.getString(6);
                LocalDate today = LocalDate.now();

                // Define a formatter to specify the date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                // Format the LocalDate using the formatter
                String formattedDate = today.format(formatter);




                String date2 = formattedDate;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                int flag=0;
                try {
                    Date d1 = sdf.parse(date1);
                    Date d2 = sdf.parse(date2);

                    // Calculate difference in milliseconds
                    long differenceInMilliseconds = d1.getTime() - d2.getTime();
                    if(differenceInMilliseconds>0) flag=1;


                } catch (ParseException e) {
                    e.printStackTrace();
                }



                if(flag==1){

                    String mailsearch=res.getString(1);
                    Cursor res1 = DB.getuserdatabymail(mailsearch);
                    String names="",phones="";
                    while(res1.moveToNext()){
                        names=res1.getString(1);
                        phones=res1.getString(6);
                    }


                    name.add(names);
                    Mail.add(res.getString(1));
                    patientdisease.add(res.getString(2));
                    bloodGroupList.add(res.getString(3));
                    hospital.add(res.getString(4));
                    address.add(res.getString(5));
                    donatedate.add(res.getString(6));
                    phone.add(phones);
                    if(res.getString(7).equals("0")) approve.add("Not Approve");
                    else approve.add("Approve");


                }



            }
        }
    }
    public void onBackPressed() {
        // Handle the back button press here
        // You can perform any action you want, such as showing a confirmation dialog
        // or navigating to a different activity
        // To perform the default back button action (finish the current activity), call super.onBackPressed()
        DB.dropTable("Login");
        Intent intent = new Intent(admindeshboard.this,MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

}
