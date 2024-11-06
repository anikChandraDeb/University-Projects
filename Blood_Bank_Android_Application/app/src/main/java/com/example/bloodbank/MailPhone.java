package com.example.bloodbank;

import static com.google.android.material.internal.ContextUtils.getActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MailPhone extends AppCompatActivity {
    EditText mailcontent;
    Button mail,call;
    DBHelper DB;
    String mails,phones,classname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_phone);

        DB = new DBHelper(this);





        mailcontent=findViewById(R.id.mailcontent);
        mail=findViewById(R.id.mail);
        call=findViewById(R.id.call);

        Intent intent=getIntent();
        String msg=intent.getStringExtra("MSG");
        StringTokenizer st = new StringTokenizer(msg,"#");
        int inx=0;
        while (st.hasMoreTokens()) {

            if(inx==1) {mails=st.nextToken();inx++;}
            else if(inx==0){phones=st.nextToken();inx++;}
            else{classname=st.nextToken();inx++;}
        }
        Toast.makeText(this,mails+" "+phones+" "+classname,Toast.LENGTH_SHORT).show();

    }
    public void callAction(View view){

        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phones));
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void mailAction(View view){
        Cursor res=DB.getlogindata();
        String from="";
        String body=mailcontent.getText().toString();
        String subject="";
        if(classname.equals("Deshboard")) subject="I Want to give blood";
        else subject="Need Blood";
        while(res.moveToNext()){
            from=res.getString(2);
        }


        Intent intent = new Intent(Intent.ACTION_SENDTO); // Only use email apps
        intent.setData(Uri.parse("mailto:")); // Set the URI to "mailto:"
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { mails }); // Recipients
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        startActivity(intent);
    }
}