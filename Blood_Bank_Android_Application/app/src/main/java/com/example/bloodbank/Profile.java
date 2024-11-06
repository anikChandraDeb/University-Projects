package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    String mailaddress;
    DBHelper DB;

    TextView name,mail,bloodgroup,birthdate,phone,district,donateblood,last,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        name=findViewById(R.id.name);
        mail=findViewById(R.id.mail);
        bloodgroup=findViewById(R.id.bloodgroup);
        birthdate=findViewById(R.id.birthdate);
        phone=findViewById(R.id.phone);
        district=findViewById(R.id.district);
        donateblood=findViewById(R.id.donateblood);
        last=findViewById(R.id.last);
        status=findViewById(R.id.status);


        DB=new DBHelper(this);





        drawerLayout=findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);

        navigationView=findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);

        TextView mailprint=headerView.findViewById(R.id.mailprint);

        Cursor res1 = DB.getlogindata();
        String mailp="",namep="";
        while(res1.moveToNext()){
            namep=res1.getString(1);
            mailp=res1.getString(2);
        }
        String print=namep+"\n"+mailp;
        mailprint.setText(print);




        Cursor res = DB.getlogindata();
        while(res.moveToNext()){
            name.setText(res.getString(1));
            mail.setText(res.getString(2));
            mailaddress=res.getString(2);
            birthdate.setText(res.getString(4));
            bloodgroup.setText(res.getString(5));
            phone.setText(res.getString(6));
            district.setText(res.getString(7));
            donateblood.setText(res.getString(9));
            String lastdonatedate=res.getString(8);
            last.setText(lastdonatedate);
            Toast.makeText(Profile.this,lastdonatedate,Toast.LENGTH_SHORT).show();
            if(lastdonatedate.equals("NULL")) status.setText("Ready");
            else{

                String date1 = lastdonatedate;
                LocalDate today = LocalDate.now();

                // Define a formatter to specify the date format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                // Format the LocalDate using the formatter
                String formattedDate = today.format(formatter);




                String date2 = formattedDate;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                try {
                    Date d1 = sdf.parse(date1);
                    Date d2 = sdf.parse(date2);

                    // Calculate difference in milliseconds
                    long differenceInMilliseconds = d2.getTime() - d1.getTime();

                    // Convert milliseconds to days
                    long differenceInDays = differenceInMilliseconds / (24 * 60 * 60 * 1000);

                    // Output the difference in days
                    if(differenceInDays>=90) status.setText("Ready");
                    else status.setText("Not Ready");
                } catch (ParseException e) {
                    e.printStackTrace();
                }



            }
        }

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
                    Toast.makeText(Profile.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.home){
                    Toast.makeText(Profile.this,"Home",Toast.LENGTH_SHORT).show();

                    startActivity(intent5);
                }
                else if(item.getItemId()==R.id.bloodpost){
                    Toast.makeText(Profile.this,"Blood Post",Toast.LENGTH_SHORT).show();
                    startActivity(intent4);
                }
                else if(item.getItemId()==R.id.search){
                    Toast.makeText(Profile.this,"Search Blood",Toast.LENGTH_SHORT).show();

                    startActivity(intent2);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(Profile.this,"See Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(Profile.this,"See Profile",Toast.LENGTH_SHORT).show();
                    startActivity(intent3);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(Profile.this,"Logout",Toast.LENGTH_SHORT).show();
                    DB.dropTable("Login");

                    startActivity(intent1);
                }
                return false;
            }


        });



    }
    public void update(View view){
        Intent intent=new Intent(this,UpdateProfile.class);
        intent.putExtra("MAIL",mailaddress+"#user");
        startActivity(intent);
    }


}
