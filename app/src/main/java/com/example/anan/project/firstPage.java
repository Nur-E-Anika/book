package com.example.anan.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class firstPage extends AppCompatActivity {


    EditText searchText;
    ImageButton searchBtn;

    String name,link;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

        final Context context=this;

        TextView booktextView = (TextView) findViewById(R.id.booktextView);
        TextView wtrTextView = (TextView) findViewById(R.id.wtrTextView);
        TextView favTextView = (TextView) findViewById(R.id.favTextView);
        searchBtn=(ImageButton) findViewById(R.id.searchBtn);
        searchText=(EditText)findViewById(R.id.searchText);
        Firebase.setAndroidContext(this);


        wtrTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4=new Intent(context,wantToRead.class);
                startActivity(intent4);
            }
        });

        favTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,favourite.class));
            }
        });

    /*    reviewtextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(context,ReviewList.class);
                startActivity(intent1);


            }
        });*/

        booktextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2=new Intent(context,BookList.class);
                startActivity(intent2);


            }
        });





        final Firebase ref=new Firebase("https://project-22aa1.firebaseio.com/Book");

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=searchText.getText().toString().toUpperCase();
                if(name.equals("")){
                    searchText.setError("Enter A Book Name...");
                } else {
                    UserDetails.al=new ArrayList<>();
                    UserDetails.bl=new ArrayList<>();
                    Query query=ref.orderByKey().equalTo(name);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                                        String s=ds.getValue(String.class);
                                        String g=ds.getKey();
                                        UserDetails.al.add(g);
                                        UserDetails.bl.add(s);
                                }

                                    startActivity(new Intent(firstPage.this,admin.class));

                                } else{

                                    AlertDialog.Builder dialog=new AlertDialog.Builder(firstPage.this);
                                    dialog.setMessage("No Book Found...");
                                    AlertDialog alertDialog=dialog.create();
                                    alertDialog.show();
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                                Toast.makeText(firstPage.this,firebaseError.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }
        });


       /* btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                link=editText.getText().toString();

                if(link.equals("")){
                    editText.setError("can't be blank");
                }


                else{
                    final ProgressDialog pd = new ProgressDialog(firstPage.this);
                    pd.setMessage("sending...");
                    pd.show();

                    String url = "https://project-22aa1.firebaseio.com/links.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://project-22aa1.firebaseio.com/links");

                            if(s.equals("null")) {
                                reference.child((Objects.requireNonNull(FirebaseAuth.getInstance()
                                        .getCurrentUser()).getDisplayName())).push().setValue(link);
                                Toast.makeText(firstPage.this, "message sent", Toast.LENGTH_LONG).show();

                            }
                            else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    reference.child((Objects.requireNonNull(FirebaseAuth.getInstance()
                                            .getCurrentUser()).getDisplayName())).push().setValue(link);

                                    Toast.makeText(firstPage.this, "message sent", Toast.LENGTH_LONG).show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();


                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(firstPage.this);
                    rQueue.add(request);
                }


                editText.setText("");
            }
        });*/
    }
}
