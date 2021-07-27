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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Random;

public class PhotosFor extends AppCompatActivity {
LinearLayout linear;
    Uri imgUri;
    StorageReference st;
    StorageTask uploadtask;
    String url234[]=new String[100];
    int r=0;String RoomName[]=new String[100];
    int d=0;
    boolean g=false;
    Button ci,no;
    ImageView pr;
    TextView tag,tag1;
    private static final int IMAGE_REQUEST=1;
    SessionManagerHotels sh;
    String name;
    Button a;
    View view;
    ProgressDialog pr1;


int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.photosfor);
       sh =new SessionManagerHotels(PhotosFor.this,SessionManagerHotels.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.FULLNAME);
        st= FirebaseStorage.getInstance().getReference("Upload");
        linear=(LinearLayout)findViewById(R.id.linear);
        a=(Button)findViewById(R.id.a);
        tag1=(TextView) findViewById(R.id.tag);
        tag1.setText("Add Photos of "+name+" hotels's hall, outsideview and swimmingpool.");

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!a.getText().toString().equals("Finish"))
                        addView();
                    else {
                        startActivity(new Intent(getApplicationContext(), HotelsOverview.class));
                        finish();
                    }
                }
            });





    }

    private void addView() {
        k++;
        a.setVisibility(View.GONE);
         view=getLayoutInflater().inflate(R.layout.hotelhalls, null,false);
         ci=view.findViewById(R.id.ci);
        final Button up=view.findViewById(R.id.up);
        pr=view.findViewById(R.id.pr);
        tag=view.findViewById(R.id.tag);
        no=view.findViewById(R.id.not);
        linear.addView(view);

        if(k==1)
        {
            tag.setText("Add a photo of your hotel's hall");
            a.setText("Add Your Hotels's outside view photo");
        }
        else if(k==2)
        {
            tag.setText("Add a photo of your hotel's outside view");
            a.setText("Add Your Hotels's swimmingpool's photo");
        }
        else if(k==3)
        {
            no.setVisibility(View.VISIBLE);
            tag.setText("Add a photo of your hotel's swimmingpool");
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    linear.removeView(view);
                    return;
                }
            });
       a.setText("Finish");

        }
        if(k==4)
        {
            tag.setText("Successfully Added The Photos. Tap on finish button to go to the overview section.");
            up.setVisibility(View.GONE);
            pr.setVisibility(View.GONE);
            no.setVisibility(View.GONE);

        }

        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
                up.setVisibility(View.VISIBLE);
                pr.setVisibility(View.VISIBLE);

            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }
    private void uploadImage()
    {
        if(imgUri==null)
        {
            Toast.makeText(getApplicationContext(), "It is demo image. You can not upload this. Select from yur own gallery", Toast.LENGTH_LONG).show();
        }
        else {
            pr1=new ProgressDialog(PhotosFor.this);
            pr1.show();
            pr1.setContentView(R.layout.progress);
            pr1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
ci.setVisibility(View.GONE);
pr.setVisibility(View.GONE);

            StorageReference ref = st.child(System.currentTimeMillis() + "." + getFileExtension(imgUri));

            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {



                            Task<Uri> tu = taskSnapshot.getStorage().getDownloadUrl();
                            HashMap hem = new HashMap();
                            Random rn = new Random();
                            int p10 = rn.nextInt(10000);

                            while (!tu.isSuccessful()) ;
                            Uri dow = tu.getResult();
                            HashMap hmt=new HashMap();
                         if(k==1)
                             hmt.put("hall",dow.toString());
                         else if(k==2)
                             hmt.put("hotelsoutside",dow.toString());
                         else
                             hmt.put("swimmingppol",dow.toString());
                            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                               FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Photos").updateChildren(hmt);

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                        }
                    });
            pr1.dismiss();
            a.setVisibility(View.VISIBLE);
            linear.removeView(view);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            Picasso.with(this).load(imgUri).into(pr);

        }
    }

    private void openImageChooser()
    {


        Intent in=new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(in, IMAGE_REQUEST);

    }

    public String getFileExtension(Uri imgURI)
    {
        ContentResolver con= getContentResolver();
        MimeTypeMap mim=MimeTypeMap.getSingleton();
        return mim.getExtensionFromMimeType(con.getType(imgURI));
    }

    @Override
    public void onBackPressed() {
        //roomCreation.super.onBackPressed();
    }

}