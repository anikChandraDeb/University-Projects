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

import java.util.ArrayList;

public class deshboard extends AppCompatActivity {

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
        setContentView(R.layout.activity_deshboard);




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
        Intent intent2=new Intent(this,SearchBlood.class);
        Intent intent3=new Intent(this,Profile.class);
        Intent intent4=new Intent(this,BloodPost.class);
        Intent intent5=new Intent(this,deshboard.class);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                deshboard.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId()==R.id.share){
                    Toast.makeText(deshboard.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.home){
                    Toast.makeText(deshboard.this,"Home",Toast.LENGTH_SHORT).show();

                    startActivity(intent5);
                }
                else if(item.getItemId()==R.id.bloodpost){
                    Toast.makeText(deshboard.this,"Blood Post",Toast.LENGTH_SHORT).show();
                    startActivity(intent4);
                }
                else if(item.getItemId()==R.id.search){
                    Toast.makeText(deshboard.this,"Search Blood",Toast.LENGTH_SHORT).show();

                    startActivity(intent2);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(deshboard.this,"See Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(deshboard.this,"See Profile",Toast.LENGTH_SHORT).show();
                    startActivity(intent3);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(deshboard.this,"Logout",Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        DB.dropTable("Login");
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        // You can also call super.onBackPressed() to perform the default back action
        super.onBackPressed(); // This calls the default back action (finishes the current activity)
    }

    public void displaydata(){
        //Toast.makeText(this,bg+" "+dis,Toast.LENGTH_SHORT).show();
        Cursor res = DB.getbloodpostdata();
        if(res.getCount()==0){
            Toast.makeText(deshboard.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){

            if(res.getString(7).equals("1")){


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
