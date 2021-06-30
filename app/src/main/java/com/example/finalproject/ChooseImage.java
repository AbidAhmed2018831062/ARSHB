package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

import static com.squareup.picasso.Picasso.*;

public class ChooseImage extends AppCompatActivity {
    Button chooseimage, upload;
    TextInputLayout name;
    String name1;
    ImageView img;
    String name2, password, email, phone, username, DO, star, des;
    Uri imgUri;
    StorageReference st;
    int count=1;
    int d=0;
     String phone3;
    StorageTask uploadtask;
    private static final int IMAGE_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_image);
        img=(ImageView) findViewById(R.id.dpp);
        upload=(Button) findViewById(R.id.upload);
        st= FirebaseStorage.getInstance().getReference("Upload");
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        final String fullname=hm.get(SessionManager.FULLNAME);
        phone3=hm.get(SessionManager.PHONE);
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
        StorageReference ref=st.child(System.currentTimeMillis()+"."+getFileExtension(imgUri));

        ref.putFile(imgUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Users");
                        Task<Uri> tu=taskSnapshot.getStorage().getDownloadUrl();
                        //count++;
                        while(!tu.isSuccessful());
                        Uri dow=tu.getResult();
                        HashMap hem=new HashMap();

                        hem.put("url",dow.toString());
                        db.child(phone3).updateChildren(hem);
                        Intent in=new Intent(getApplicationContext(),UserPrfofilew.class);
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