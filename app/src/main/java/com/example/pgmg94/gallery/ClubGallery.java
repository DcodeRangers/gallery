package com.example.pgmg94.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
public class ClubGallery extends AppCompatActivity {
    DatabaseReference rootref;
    int count=0;
    HorizontalScrollView horizontalScrollView;
    LinearLayout linearLayout;
    RelativeLayout rl;
    ImageView imageViews;
    List<String> imagesUrl = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_gallery);
        Intent intent=getIntent();
        final String ClubName=intent.getStringExtra("ClubName");
        rl =(RelativeLayout)findViewById(R.id.relative);
        rootref= FirebaseDatabase.getInstance().getReference();
        final ScrollView horizontalScrollView = new ScrollView(this);
        final LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams prams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(prams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        rootref.child("Clubs").child("Artistia").child("Event").child("Event1").child("gallery")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        String value= dataSnapshot.getValue(String.class);
                        Toast.makeText(ClubGallery.this, value, Toast.LENGTH_LONG).show();
                        imageViews = new ImageView(ClubGallery.this);
                        imageViews.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT));
                        imageViews.setPadding(10,0,10,0);

                        Picasso.get().load(value).into(imageViews);

                        linearLayout.addView(imageViews);
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        horizontalScrollView.addView(linearLayout);
        rl.addView(horizontalScrollView);
    }
}
