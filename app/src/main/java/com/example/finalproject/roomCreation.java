package com.example.finalproject;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class roomCreation extends AppCompatActivity {
 LinearLayout layoutList;
 Button rooms,submit;
 ArrayList<Rooms> list=new ArrayList<>();
 DatabaseReference db,db1;
 FirebaseDatabase fb;
 String abid;
 ImageView RoomImage;
    Uri imgUri;
    StorageReference st;
    StorageTask uploadtask;
    String url234[]=new String[100];
    int r=0;String RoomName[]=new String[100];
    int d=0;
     boolean g=false;
    private static final int IMAGE_REQUEST=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_room_creation);
        rooms=(Button) findViewById(R.id.rooms);
        layoutList=(LinearLayout) findViewById(R.id.linear);
        st= FirebaseStorage.getInstance().getReference("Upload");

        submit=(Button) findViewById(R.id.submit);
        abid=getIntent().getStringExtra("name");
        rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(layoutList.getChildCount()==0) {
                    if (g == true) {
                        Intent in = new Intent(roomCreation.this, HotelRooms.class);

                        in.putExtra("Name", getIntent().getStringExtra("Name").toString());

                        startActivity(in);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "All fileds need to be filled", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Submit the earlier rooms first", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void addView()
    {
        final View roomView=getLayoutInflater().inflate(R.layout.roomcreation1, null,false);
        final TextInputLayout se=(TextInputLayout) roomView.findViewById(R.id.service);
        final TextInputLayout pr=(TextInputLayout)roomView.findViewById(R.id.price);
        final TextInputLayout rn1=(TextInputLayout)roomView.findViewById(R.id.rn);
        Button img=(Button) roomView.findViewById(R.id.cancel);
        final Button ci=(Button) roomView.findViewById(R.id.ci);
        final Button ui=(Button) roomView.findViewById(R.id.ui);
        final Button sub=(Button) roomView.findViewById(R.id.sub);
        RoomImage=(ImageView) roomView.findViewById(R.id.RoomImage);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        roomCreation.this);

// Setting Dialog Title
                alertDialog2.setTitle("Delete Room");

// Setting Dialog Message
                alertDialog2.setMessage("Are You Sure Want To Delete The room??");
                alertDialog2.setIcon(R.drawable.apptitle);
                alertDialog2.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                layoutList.removeView(roomView);


                            }
                        });
// Setting Negative "NO" Btn
                alertDialog2.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

// Showing Alert Dialog
                alertDialog2.show();
        }});
        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
                ui.setVisibility(View.VISIBLE);
                RoomImage.setVisibility(View.VISIBLE);

            }
        });
        ui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g = false;
                if (imgUri == null) {
                    Toast.makeText(getApplicationContext(), "It is demo image. You can not upload this. Select from yur own gallery", Toast.LENGTH_LONG).show();
                } else {
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
                                    url234[r] = dow.toString();
                                    r++;
                                    ci.setVisibility(View.GONE);
                                    ui.setVisibility(View.GONE);
                                    RoomImage.setVisibility(View.GONE);

                                    se.setVisibility(View.VISIBLE);
                                    rn1.setVisibility(View.VISIBLE);
                                    pr.setVisibility(View.VISIBLE);
                                    sub.setVisibility(View.VISIBLE);
                                    for (int j = 0; j < d; j++) {
                                        if (RoomName[j].equals(rn1.getEditText().getText().toString())) {
                                            rn1.getEditText().setError("Room Name Already Exists");
                                            rn1.getEditText().requestFocus();
                                        }
                                    }
                                    RoomName[d] = rn1.getEditText().getText().toString();
                                    d++;
                                    sub.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (!se.getEditText().getText().toString().equals("") && !pr.getEditText().getText().toString().equals("") && !rn1.getEditText().getText().toString().equals("")) {
                                                SessionManagerHotels sh = new SessionManagerHotels(roomCreation.this, SessionManagerHotels.USERSESSION);

                                                HashMap<String, String> hm = sh.returnData();
                                                String name123 = hm.get(SessionManagerHotels.FULLNAME);
                                                Rooms roo = new Rooms(rn1.getEditText().getText().toString(), se.getEditText().getText().toString(), pr.getEditText().getText().toString(), url234[r - 1]);
                                                FirebaseDatabase.getInstance().getReference("Hotels").child(name123).child("rooms").child(rn1.getEditText().getText().toString()).setValue(roo);
                                                g = true;
                                                layoutList.removeView(roomView);
                                                Toast.makeText(getApplicationContext(), "Rooms submitted successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "All fields need to be filled", Toast.LENGTH_LONG).show();
                                            }
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
            }
        });


     layoutList.addView(roomView);
    }
    public boolean check()
    {
        list.clear();
        int d=0;
        boolean r=true;
        db=fb.getInstance().getReference("Hotels").child(getIntent().getStringExtra("Name").toString()).child("rooms");
        String RoomName[]=new String[100];
        for(int i=0;i<layoutList.getChildCount();i++)
        {
           View view=layoutList.getChildAt(i);
            TextInputLayout se=(TextInputLayout) view.findViewById(R.id.service);

            Rooms roo=new Rooms();

           if(!se.getEditText().getText().toString().equals(""))
           {
           roo.setServices(se.getEditText().getText().toString());

           }
           else {
               Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
               r = false;
              break;
           }

            TextInputLayout price=(TextInputLayout)view.findViewById(R.id.price);
            if(!price.getEditText().getText().toString().equals(""))
            {
                roo.setPrice(price.getEditText().getText().toString());
            }
            else {
                Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
                r = false;
                break;
            }
            TextInputLayout rn=(TextInputLayout)view.findViewById(R.id.rn);
            if(!rn.getEditText().getText().toString().equals(""))
            {
                roo.setRoomname(rn.getEditText().getText().toString());
                for(int j=0;j<d;j++)
                {
                    if(RoomName[j].equals(rn.getEditText().getText().toString())) {
                        rn.getEditText().setError("Room Name Already Exists");
                        rn.getEditText().requestFocus();
                    }
                }
                RoomName[i]=rn.getEditText().getText().toString();
                d++;
            }
            else {
                Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
                r = false;
                break;
            }

            db1=db.child(rn.getEditText().getText().toString());
           db1.setValue(roo);
            list.add(roo);

        }
        if(list.size()==0)
        {
            Toast.makeText(getApplicationContext(),"Add Rooms First", Toast.LENGTH_LONG).show();
            r=false;
        }
        else if (!r)
        {
            Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
        }
        return r;
    }  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == IMAGE_REQUEST) && (resultCode == RESULT_OK) && (data.getData() != null)){
            imgUri=data.getData();
            Picasso.with(this).load(imgUri).into(RoomImage);

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

}