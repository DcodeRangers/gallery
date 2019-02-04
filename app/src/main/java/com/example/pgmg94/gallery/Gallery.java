package com.example.pgmg94.gallery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Gallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }
    public void club(View view)
    {
        Intent intent =new Intent(Gallery.this,ClubGallery.class);
        if(view.getId()==R.id.cv1)
            intent.putExtra("ClubName","Artistia");
        else if(view.getId()==R.id.cv2)
            intent.putExtra("ClubName","Virasat");

        else if(view.getId()==R.id.cv3)
            intent.putExtra("ClubName","Literary");

        else if(view.getId()==R.id.cv4)
            intent.putExtra("ClubName","Social");

        else if(view.getId()==R.id.cv5)
            intent.putExtra("ClubName","Rangmanch");

        else if(view.getId()==R.id.cv6)
            intent.putExtra("ClubName","Pixel");

        startActivity(intent);





    }
}
