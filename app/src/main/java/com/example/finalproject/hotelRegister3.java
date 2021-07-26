package com.example.finalproject;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

public class hotelRegister3 extends AppCompatActivity {
Button chooseimage, upload;
TextInputLayout name;
String name1;
ImageView img;
    String name2, password, email, phone, username, DO, star, des;
Uri imgUri;
StorageReference st;
int count=1;
int d=0;
StorageTask uploadtask;
private static final int IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_register3);
        img=(ImageView) findViewById(R.id.dpp);
        name2 = getIntent().getStringExtra("Name");
        upload=(Button) findViewById(R.id.upload);
        st= FirebaseStorage.getInstance().getReference("Upload");
        chooseimage=(Button) findViewById(R.id.chooseimage);
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
                upload.setVisibility(View.VISIBLE);
                img.setVisibility(View.VISIBLE);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadtask!=null&&uploadtask.isInProgress())
                {
                    Toast.makeText(getApplicationContext(),"Uploading In Progress",Toast.LENGTH_LONG).show();
                }else
                uploadImage();
            }
        });

        name=(TextInputLayout) findViewById(R.id.name);
      name.getEditText().setText(name2);
        name.getEditText().setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            Picasso.with(this).load(imgUri).into(img);

        }
    }

    private void openImageChooser()
    {


        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, IMAGE_REQUEST);

    }
    private void uploadImage()
    {
        if(imgUri==null)
        {
            Toast.makeText(getApplicationContext(), "It is demo image. You can not upload this. Select from yur own gallery", Toast.LENGTH_LONG).show();
        }
        else {
            StorageReference ref = st.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            phone = getIntent().getStringExtra("Phone");
                            Toast.makeText(getApplicationContext(), phone, Toast.LENGTH_LONG).show();

                            password = getIntent().getStringExtra("Password");
                            email = getIntent().getStringExtra("Email");
                            des = getIntent().getStringExtra("Des");
                            star = getIntent().getStringExtra("Star");
                            username = getIntent().getStringExtra("UserName");
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Hotels");
                            Task<Uri> tu = taskSnapshot.getStorage().getDownloadUrl();
                            HashMap hem = new HashMap();
                            Random rn = new Random();
                            int p10 = rn.nextInt(10000);

                            while (!tu.isSuccessful()) ;
                            Uri dow = tu.getResult();
                            SessionManagerHotels sh = new SessionManagerHotels(hotelRegister3.this, SessionManagerHotels.USERSESSION);

                            sh.loginSession(name2, email, star, phone, password, des, username, dow.toString());

                            HotelShow hr = new HotelShow(name2, dow.toString());
                            db.child("Hotel Names").child(name2).setValue(hr);
                            count++;
                            Hotel h = new Hotel(name2, password, email, phone, des, star, username, dow.toString());
                            db.child(name2).setValue(h);
                            countingStar(star, name2, d);
                            Intent in = new Intent(getApplicationContext(), hotelregister5.class);
                            in.putExtra("Name", name2);

                            startActivity(in);
                            finish();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
        }
    }

    private void countingStar(String star, String name2,int d) {
Random rn =new Random();
d=rn.nextInt(1000000);
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("Star").child(star+" Star");
       HashMap hm1=new HashMap();
       hm1.put("name"+d, name2);
       db.updateChildren(hm1);
    }


    public String getFileExtension(Uri imgURI)
    {
        ContentResolver con= getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(con.getType(imgURI));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),CutomerOrOwner.class));
        finish();
    }
}