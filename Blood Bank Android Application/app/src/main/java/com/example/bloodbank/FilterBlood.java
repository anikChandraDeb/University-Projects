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

public class FilterBlood extends AppCompatActivity {

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
        setContentView(R.layout.activity_filter_blood);
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




        Intent intent1=new Intent(this,MainActivity.class);
        Intent intent2=new Intent(this,SearchBlood.class);
        Intent intent3=new Intent(this,Profile.class);
        Intent intent4=new Intent(this,BloodPost.class);
        Intent intent5=new Intent(this,deshboard.class);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId()==R.id.share){
                    Toast.makeText(FilterBlood.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.home){
                    Toast.makeText(FilterBlood.this,"Home",Toast.LENGTH_SHORT).show();

                    startActivity(intent5);
                }
                else if(item.getItemId()==R.id.bloodpost){
                    Toast.makeText(FilterBlood.this,"Blood Post",Toast.LENGTH_SHORT).show();
                    startActivity(intent4);
                }
                else if(item.getItemId()==R.id.search){
                    Toast.makeText(FilterBlood.this,"Search Blood",Toast.LENGTH_SHORT).show();

                    startActivity(intent2);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(FilterBlood.this,"See Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(FilterBlood.this,"See Profile",Toast.LENGTH_SHORT).show();
                    startActivity(intent3);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(FilterBlood.this,"Logout",Toast.LENGTH_SHORT).show();
                    DB.dropTable("Login");

                    startActivity(intent1);
                }
                return false;
            }


        });

        Intent intent=getIntent();
        String msg=intent.getStringExtra("MSG");
        StringTokenizer st = new StringTokenizer(msg,"#");

        String bloodgroup="",district="",classname="filter";
        int inx=0;
        while (st.hasMoreTokens()) {

            if(inx==0) {bloodgroup=st.nextToken();inx++;}
            else if(inx==1){district=st.nextToken();inx++;}

        }

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
        displaydata(bloodgroup,district);

    }
    public void displaydata(String bg,String dis){
        Toast.makeText(this,bg+" "+dis,Toast.LENGTH_SHORT).show();
        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(FilterBlood.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
            return;
        }

        while(res.moveToNext()){

            if(res.getString(11).equals("1")){
                if(bg.equals(res.getString(5))){
                    if(!dis.isEmpty()){
                        String a=dis.toUpperCase();
                        String b=res.getString(7).toUpperCase();
                        a = a.replaceAll("\\s", "");
                        b = b.replaceAll("\\s", "");
                        //Toast.makeText(FilterBlood.this, a.length()+" "+b.length(), Toast.LENGTH_SHORT).show();
                        if(a.equals(b)){
                            //Toast.makeText(FilterBlood.this, "OK", Toast.LENGTH_SHORT).show();
                            name.add(res.getString(1));
                            Mail.add(res.getString(2));
                            bloodGroupList.add(res.getString(5));
                            District.add(res.getString(7));
                            phone.add(res.getString(6));
                            if(res.getString(11).equals("0")) approve.add("Not Approve");
                            else approve.add("Approve");
                        }
                    }
                    else{
                        Toast.makeText(FilterBlood.this, "Anik", Toast.LENGTH_SHORT).show();
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
        }
    }

}
