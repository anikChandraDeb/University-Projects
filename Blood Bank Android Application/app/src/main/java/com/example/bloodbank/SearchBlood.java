package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class SearchBlood extends AppCompatActivity {
    EditText district;
    DrawerLayout drawerLayout;
    MaterialToolbar materialToolbar;
    FrameLayout frameLayout;
    NavigationView navigationView;

    DBHelper DB;

    String selectedBloodGroup="";

    Spinner spinnerBloodGroup;

    Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_blood);
        DB=new DBHelper(this);





        drawerLayout=findViewById(R.id.drawerLayout);
        materialToolbar=findViewById(R.id.materialToolbar);

        navigationView=findViewById(R.id.navigationView);

        View headerView = navigationView.getHeaderView(0);

        TextView mailprint=headerView.findViewById(R.id.mailprint);
        Intent intent = getIntent();
        String mail = "";
        mailprint.setText(mail);


        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);
        district=findViewById(R.id.district);
        search=findViewById(R.id.search);
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




        Intent intent1=new Intent(this,MainActivity.class);
        Intent intent2=new Intent(this,SearchBlood.class);
        Intent intent3=new Intent(this,Profile.class);
        Intent intent4=new Intent(this,BloodPost.class);
        Intent intent5=new Intent(this,deshboard.class);


        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                SearchBlood.this,drawerLayout,materialToolbar,R.string.drawer_close,R.string.drawer_open);
        drawerLayout.addDrawerListener(toggle);

        materialToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                if(item.getItemId()==R.id.share){
                    Toast.makeText(SearchBlood.this,"Share App",Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item){


                if(item.getItemId()==R.id.home){
                    Toast.makeText(SearchBlood.this,"Home",Toast.LENGTH_SHORT).show();

                    startActivity(intent5);
                }
                else if(item.getItemId()==R.id.bloodpost){
                    Toast.makeText(SearchBlood.this,"Blood Post",Toast.LENGTH_SHORT).show();
                    startActivity(intent4);
                }
                else if(item.getItemId()==R.id.search){
                    Toast.makeText(SearchBlood.this,"Search Blood",Toast.LENGTH_SHORT).show();

                    startActivity(intent2);
                }
                else if(item.getItemId()==R.id.hospitals){
                    Toast.makeText(SearchBlood.this,"See Hospitals",Toast.LENGTH_SHORT).show();
                }
                else if(item.getItemId()==R.id.profile){
                    Toast.makeText(SearchBlood.this,"See Profile",Toast.LENGTH_SHORT).show();
                    startActivity(intent3);
                }
                else if(item.getItemId()==R.id.logout){
                    Toast.makeText(SearchBlood.this,"Logout",Toast.LENGTH_SHORT).show();
                    DB.dropTable("Login");

                    startActivity(intent1);
                }
                return false;
            }


        });



    }

    public void search(View view){
        String bloodgroups=selectedBloodGroup;
        String districts=district.getText().toString();
        String send=bloodgroups+"#"+districts+"#";
        Intent intent=new Intent(SearchBlood.this,FilterBlood.class);
        intent.putExtra("MSG",send);
        startActivity(intent);
    }
}
