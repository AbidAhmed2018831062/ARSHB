package com.example.finalproject;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UserImage extends AppCompatActivity {
    ImageView back,image;
    Button chooseimage,upload;
    StorageTask uploadtask;
    private static final int IMAGE_REQUEST=1;
    Uri imgUri;
    StorageReference st;
    ProgressDialog pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_image);
        st= FirebaseStorage.getInstance().getReference("Upload");
        back = (ImageView) findViewById(R.id.back);
        image=(ImageView)findViewById(R.id.image);
        chooseimage=(Button)findViewById(R.id.chooseimage);
        upload=(Button)findViewById(R.id.upload);
        Picasso.with(UserImage.this).load(getIntent().getStringExtra("url")).fit().centerCrop().into(image);
        chooseimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
openImageChooser();
upload.setVisibility(View.VISIBLE);
            }});
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    public String getFileExtension(Uri imgURI)
    {
        ContentResolver con= getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(con.getType(imgURI));
    }
    private void uploadImage()
    {
        if(imgUri==null)
        {
            Toast.makeText(getApplicationContext(), "It is demo image. You can not upload this. Select from yur own gallery", Toast.LENGTH_LONG).show();
        }
        else {  pr=new ProgressDialog(this);
            pr.show();
            pr.setContentView(R.layout.progress);
            pr.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            StorageReference ref = st.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> tu = taskSnapshot.getStorage().getDownloadUrl();
                            HashMap hem = new HashMap();


                            while (!tu.isSuccessful()) ;
                            Uri dow = tu.getResult();
                            final SessionManager sh=new SessionManager(UserImage.this,SessionManager.USERSESSION);
                            final HashMap hmj=new HashMap();


                            HashMap<String,String>  hm=sh.returnData();
                            final String phone2=hm.get(SessionManager.PHONE);
                            hmj.put("url",dow.toString());
                            FirebaseDatabase.getInstance().getReference("Users").child(phone2).updateChildren(hmj);
                            FirebaseDatabase.getInstance().getReference("Hotels").child("Hotel Names").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        final HotelShow hs=ds.getValue(HotelShow.class);
                                        FirebaseDatabase.getInstance().getReference("Hotels").child(hs.getName()).child("Review").
                                                orderByChild("phone").equalTo(phone2).
                                                addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                             if(dataSnapshot1.exists())
                                             {
                                                 FirebaseDatabase.getInstance().getReference("Hotels").child(hs.getName()).child("Review").child(phone2).updateChildren(hmj);
                                             }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    pr.dismiss();
                                    startActivity(new Intent(getApplicationContext(),DashBoard.class));
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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
    } @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            Picasso.with(this).load(imgUri).into(image);

        }
    }

    private void openImageChooser()
    {


        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, IMAGE_REQUEST);

    }

}