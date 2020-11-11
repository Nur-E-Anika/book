package com.example.anan.project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class bookView extends AppCompatActivity {

    Button cmntBtn;

    ImageView imageView;

    Boolean itemSel = false;
    TextView ratTaxtView;
    ArrayList<String> ratings = new ArrayList<String>() {
        {
            add("Remove Rating");
            add("10");
            add("9");
            add("8");
            add("7");
            add("6");
            add("5");
            add("4");
            add("3");
            add("2");
            add("1");
            add("0");
        }
    };


    LinearLayout layout;
    RelativeLayout layout_2;
    ScrollView scrollView;
    Firebase reference1;
    ListView listView;
    Firebase ref;
    Firebase refFav;
    private RatingBar ratingBar;
    RatingBar ratingBarBtn;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){

            UserDetails.uid = user.getUid();
        }



        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout)findViewById(R.id.layout2);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        final ImageView imageView=(ImageView)findViewById(R.id.imageView);
        final TextView plotTextView=(TextView)findViewById(R.id.plotTextview);
        final TextView nameTextView=(TextView)findViewById(R.id.nameTextview);

        ref= new Firebase("https://project-22aa1.firebaseio.com/WantToRead/"+UserDetails.uid);
        refFav= new Firebase("https://project-22aa1.firebaseio.com/Favourite/"+UserDetails.uid);

        reference1=new Firebase("https://project-22aa1.firebaseio.com/Book Details/"+UserDetails.bookRef);

        final Context context=this;

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserDetails.name=dataSnapshot.child("WRITER").getValue().toString();
                UserDetails.genre=dataSnapshot.child("GENRE").getValue().toString();
                UserDetails.plot=dataSnapshot.child("PLOT").getValue().toString();
                String urli=dataSnapshot.child("imgView").getValue().toString();
                Picasso.get().load(urli).into(imageView);
                nameTextView.setText(UserDetails.bookRef+"\n"+UserDetails.name+"\nGenre: "+UserDetails.genre+"\n");
                plotTextView.setText(UserDetails.plot);
                scrollView.fullScroll(View.FOCUS_DOWN);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });







        TextView wtrTextView = (TextView)findViewById(R.id.wtrTextView);
        wtrTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Query que = ref.orderByChild(UserDetails.uid).equalTo(UserDetails.bookRef);
                //if(que!= null){

                    ref.child(UserDetails.bookRef).child("genre").setValue(UserDetails.BookName);

                    Toast.makeText(bookView.this, "Book Added", Toast.LENGTH_LONG).show();
              //  }
            }
        });

        TextView favTextView = (TextView)findViewById(R.id.favTextView);
        favTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Query que = ref.orderByChild(UserDetails.uid).equalTo(UserDetails.bookRef);
                //if(que!= null){

                refFav.child(UserDetails.bookRef).child("genre").setValue(UserDetails.BookName);

                Toast.makeText(bookView.this, "Book Added", Toast.LENGTH_LONG).show();
                //  }
            }
        });

        TextView readTextView=(TextView) findViewById(R.id.readTextView);
        readTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(context,book.class);
                startActivity(intent1);


            }
        });




        ratingBarBtn=(RatingBar)findViewById(R.id.ratingBarBtn);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        final TextView avgRatingTextView = (TextView)findViewById(R.id.avgRatingTextView);
        ratTaxtView=(TextView)findViewById(R.id.ratTaxtView);
        final Firebase rf=new Firebase("https://project-22aa1.firebaseio.com/Rating/");


       // Float rating = 4f;
       // ratingBar.setFocusable(true);
        //ratingBar.setTag(rating);
      /*  ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // myRatingDialog.show();
                    view.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setPressed(true);
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setPressed(false);
                }
                return true;
            }
        });*/

      /*  ratingBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        view.setPressed(false);
                       Toast.makeText(bookView.this,"asdfg",Toast.LENGTH_LONG).show();
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        if (view.focusSearch(View.FOCUS_LEFT) != null)
                            view.focusSearch(View.FOCUS_LEFT).requestFocus();
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (view.focusSearch(View.FOCUS_RIGHT) != null)
                            view.focusSearch(View.FOCUS_RIGHT).requestFocus();
                    }
                }
                return false;
            }
        });  ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.setRating((Float) ratingBar.getTag());
            }
        });*/
       /* ratingBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        v.setPressed(false);
                        myRatingDialog.show();
                    } else if(keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
                        if (v.focusSearch(View.FOCUS_LEFT) != null)  v.focusSearch(View.FOCUS_LEFT).requestFocus();
                    } else if(keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (v.focusSearch(View.FOCUS_RIGHT) != null) v.focusSearch(View.FOCUS_RIGHT).requestFocus();
                    }
                }
                return false;
            }
        });
      */


       ratingBar.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent event) {

                   myRatingDialog();


               return false;
           }
       });



      /*  ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rf.child(UserDetails.bookRef).child(UserDetails.uid).setValue(v);
            }
        });
*/
     rf.child(UserDetails.bookRef).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

               double total = 0.0;
               double count = 0.0;
               double average = 0.0;

               for (DataSnapshot ds : dataSnapshot.child("ratings").getChildren()) {
                   double drating = Double.parseDouble(ds.getValue().toString());
                   total = total + drating;
                   count = count + 1;
                   average = total / count;
               }


               rf.child(UserDetails.bookRef).child("AvgRating").setValue(average);

               String Rt=String.valueOf(dataSnapshot.child("AvgRating").getValue());
               avgRatingTextView.setText(Rt+"/10\n"+(int)count);

               if(dataSnapshot.child("ratings").child(UserDetails.uid).exists()){
                   UserDetails.rating=dataSnapshot.child("ratings").child(UserDetails.uid).getValue().toString();
                   ratingBar.setRating(1);
               } else {
                   UserDetails.rating="";
                   ratingBar.setRating(0);
               }
               ratTaxtView.setText(UserDetails.rating+"/10\nYou");

           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
       });


        cmntBtn = (Button)findViewById(R.id.cmntBtn);
        cmntBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(bookView.this, openMessage.class));

            }
        });


            }

            public void myRatingDialog(){

                final Dialog dialog = new Dialog(bookView.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialoglist);



                // set values for custom dialog components - text, image and button


                final Firebase rf1=new Firebase("https://project-22aa1.firebaseio.com/Rating/");

                listView = (ListView)dialog.findViewById(R.id.bookList);
                listView.setVisibility(View.VISIBLE);
                listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ratings){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);

                        TextView textView = (TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                        textView.setTextColor(Color.WHITE);

                        textView.setTextSize(21);



                        return view;
                    }
                });

                dialog.show();

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String rt = ratings.get(position);


                        if (rt != "Remove Rating") {
                            rf1.child(UserDetails.bookRef).child("ratings").child(UserDetails.uid).setValue(rt);


                        } else {

                            rf1.child(UserDetails.bookRef).child("ratings").child(UserDetails.uid).setValue(null);
                        }
                       // Toast.makeText(bookView.this, UserDetails.rating, Toast.LENGTH_LONG).show();


                        dialog.dismiss();

                    }
                });

            }
}
