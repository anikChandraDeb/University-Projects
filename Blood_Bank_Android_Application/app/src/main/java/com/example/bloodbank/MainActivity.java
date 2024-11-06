package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText mail,password;
    SharedPreferences sharedPreferences;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB=new DBHelper(this);
        mail=findViewById(R.id.mail);
        password=findViewById(R.id.password);

    }
    public void signup(View view){
        Intent intent=new Intent(this,Registration.class);
        startActivity(intent);
    }
    public void login(View view){
        //Intent intent=new Intent(this,deshboard.class);

        //startActivity(intent);


        String mails=mail.getText().toString();
        String passwords=password.getText().toString();
        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(MainActivity.this, "No user Exits", Toast.LENGTH_SHORT).show();
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
            if(!approveddb.equals("1")){flag=3;break;}
            else if(maildb.equals(mails) && passworddb.equals(passwords)) {
                flag=1;
                Cursor res1 = DB.getlogindatabymail(mails);
                if(res1.getCount()==0){
                    Boolean checkinsertdata = DB.insertlogindata(namedb,maildb,passworddb,birthdatedb,bloodgroupdb,phonedb,districtdb,lastdonatedatedb,countdonateblooddb,typedb,approveddb);
                    if(checkinsertdata == true){
                        Toast.makeText(MainActivity.this, "Store!", Toast.LENGTH_SHORT).show();

                    }

                    else
                        Toast.makeText(MainActivity.this, "Exists", Toast.LENGTH_SHORT).show();


                }
                break;
            }
            else if(maildb.equals(mails) && !passworddb.equals(passwords)) {flag=2;break;}
        }

        if(flag==1){
            Toast.makeText(MainActivity.this, "Login Successfully!!", Toast.LENGTH_SHORT).show();


            Intent intent=new Intent(this,deshboard.class);

            startActivity(intent);
        }
        else if(flag==2){
            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }
        else if(flag==3){
            Toast.makeText(MainActivity.this, "User Not Approved Yet..", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "User Doesn't Exists", Toast.LENGTH_SHORT).show();
        }

    }
    public void loginAdmin(View view){
        //Intent intent=new Intent(this,admindeshboard.class);

        //startActivity(intent);


        String mails=mail.getText().toString();
        String passwords=password.getText().toString();
        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(MainActivity.this, "No user Exits", Toast.LENGTH_SHORT).show();
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
            if(!approveddb.equals("1")){flag=3;break;}
            else if(maildb.equals(mails) && passworddb.equals(passwords)) {
                flag=1;
                Cursor res1 = DB.getlogindatabymail(mails);
                if(res1.getCount()==0){
                    Boolean checkinsertdata = DB.insertlogindata(namedb,maildb,passworddb,birthdatedb,bloodgroupdb,phonedb,districtdb,lastdonatedatedb,countdonateblooddb,typedb,approveddb);
                    if(checkinsertdata == true){
                        Toast.makeText(MainActivity.this, "Store!", Toast.LENGTH_SHORT).show();

                    }

                    else
                        Toast.makeText(MainActivity.this, "Exists", Toast.LENGTH_SHORT).show();


                }
                break;
            }
            else if(maildb.equals(mails) && !passworddb.equals(passwords)) {flag=2;break;}
        }

        if(flag==1){
            Toast.makeText(MainActivity.this, "Login Successfully!!", Toast.LENGTH_SHORT).show();


            Intent intent=new Intent(this,admindeshboard.class);
            startActivity(intent);
        }
        else if(flag==2){
            Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }
        else if(flag==3){
            Toast.makeText(MainActivity.this, "User Not Approved Yet..", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "User Doesn't Exists", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onBackPressed() {
        // Handle the back button press here
        // You can perform any action you want, such as showing a confirmation dialog
        // or navigating to a different activity
        // To perform the default back button action (finish the current activity), call super.onBackPressed()
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        super.onBackPressed();
    }
    public  void forgetAction(View view){



        String mails=mail.getText().toString();
        String passwords=password.getText().toString();
        Cursor res = DB.getuserdata();
        if(res.getCount()==0){
            Toast.makeText(MainActivity.this, "No user Exits", Toast.LENGTH_SHORT).show();
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
            if(!approveddb.equals("1")){flag=3;break;}
            else if(maildb.equals(mails)) {
                flag=1;
                break;
            }
            else if(maildb.equals(mails) && !passworddb.equals(passwords)) {flag=2;break;}
        }

        if(flag==1){
            String from="";
            String to="anik.deb161@gmail.com";
            String subject="Give Me New Password!!!";
            String body="My email is : "+mails+"\n I forget my password please give me new password.";


            Intent intent = new Intent(Intent.ACTION_SENDTO); // Only use email apps
            intent.setData(Uri.parse("mailto:")); // Set the URI to "mailto:"
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { to }); // Recipients
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);

            startActivity(intent);

        }
        else if(flag==3){
            Toast.makeText(MainActivity.this, "User Not Approved Yet..", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this, "User Doesn't Exists", Toast.LENGTH_SHORT).show();
        }
    }
}