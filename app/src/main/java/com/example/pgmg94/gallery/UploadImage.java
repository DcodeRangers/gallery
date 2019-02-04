package com.example.pgmg94.gallery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOError;
import java.io.IOException;
import java.util.UUID;

public class UploadImage extends AppCompatActivity {

    private ImageView image;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST= 71;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    DatabaseReference rootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        image=(ImageView) findViewById(R.id.image);
        firebaseStorage=FirebaseStorage.getInstance();
        rootRef=FirebaseDatabase.getInstance().getReference();
        storageReference=firebaseStorage.getReference();
    }
    public void upload(View view)
    {
        uploadImage();

    }



    private void uploadImage() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading......");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());

            UploadTask uploadTask = ref.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String x=downloadUri.toString();
                        Toast.makeText(UploadImage.this,x,Toast.LENGTH_LONG).show();

                        rootRef.child("Clubs").child("Artistia").child("Event").child("Event1").child("gallery").push().setValue(x)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            Toast.makeText(UploadImage.this,"Done",Toast.LENGTH_LONG);
                                        }
                                        else
                                            Toast.makeText(UploadImage.this,"NOt Done",Toast.LENGTH_LONG);
                                    }
                                });
                        progressDialog.dismiss();
                    } else {

                    }
                }
            });



        }
    }






    public void choose(View view)
    {
        chooseImage();
    }

    private void chooseImage() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==PICK_IMAGE_REQUEST &&  data!= null &&data.getData()!=null)
        {
            filePath= data.getData();
            try{
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                image.setImageBitmap(bitmap);


            }catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
