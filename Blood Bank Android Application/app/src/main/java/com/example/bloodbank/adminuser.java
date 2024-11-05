package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import android.os.Bundle;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class adminuser extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    DBHelper DB;

    RecyclerView recyclerView;
    ArrayList<String> name,Mail,bloodGroupList,District,phone,approve;

    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuser);
        drawerLayout=findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);
        DB=new DBHelper(this);
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







        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId()==R.id.share){
                    Toast.makeText(adminuser.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.approvepost){
                    Toast.makeText(adminuser.this,"Home",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(adminuser.this,admindeshboard.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.approveuser){
                    Toast.makeText(adminuser.this,"User Approve",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(adminuser.this,adminuser.class);
                    intent.putExtra("MSG","approve");
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.blockuser){
                    Toast.makeText(adminuser.this,"Block User",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(adminuser.this,adminuser.class);
                    intent.putExtra("MSG","block");
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.addadmin){
                    Toast.makeText(adminuser.this,"Add Hospitals",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(adminuser.this,addadmin.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(adminuser.this,"Add Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(adminuser.this,"See Profile",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(adminuser.this,Profile.class);
                    startActivity(intent);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(adminuser.this,"Logout",Toast.LENGTH_SHORT).show();
                    DB.dropTable("Login");
                    Intent intent=new Intent(adminuser.this,MainActivity.class);
                    startActivity(intent);
                }
                return false;
            }


        });

        Intent intent2=getIntent();
        String classname=intent2.getStringExtra("MSG");

        materialToolbar.setTitle(capitalize(classname)+" User");
        name=new ArrayList<>();
        Mail=new ArrayList<>();
        bloodGroupList=new ArrayList<>();
        District=new ArrayList<>();
        phone=new ArrayList<>();
        approve=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        adapter=new MyAdapter(this,name,Mail,bloodGroupList,District,phone,approve,classname);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(classname.equals("approve")) displaydata("0");
        else if(classname.equals("block")) displaydata("1");

    }
    public void displaydata(String approves){

        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(adminuser.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){

            if(res.getString(11).equals(approves) && res.getString(10).equals("Donar")){
                Toast.makeText(adminuser.this, "Anik", Toast.LENGTH_SHORT).show();
                name.add(res.getString(1));
                Mail.add(res.getString(2));
                bloodGroupList.add(res.getString(5));
                District.add(res.getString(7));
                phone.add(res.getString(6));
                if(res.getString(11).equals("0")) approve.add("Not Approve");
                else approve.add("Approve");
            }
        }
    }
    public static final String capitalize(String str)
    {
        if (str == null || str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
