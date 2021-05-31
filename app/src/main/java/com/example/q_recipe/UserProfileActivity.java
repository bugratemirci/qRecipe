package com.example.q_recipe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q_recipe.Business.LoggedInUser;
import com.example.q_recipe.ENV.GlobalVariables;
import com.example.q_recipe.Models.User;
import com.example.q_recipe.WebServices.GetOperations;
import com.example.q_recipe.WebServices.PostOperations;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

public class UserProfileActivity extends AppCompatActivity {
    private TextView textViewProfileName, textViewProfileEmail, textViewProfileAbout, textViewProfilePhone, textboxUserLocation;
    private LoggedInUser loggedInUser;
    private Button buttonProfileEdit;
    private CircularImageView imageViewProfileImage;
    private static int RESULT_LOAD_IMAGE = 1;
    private PostOperations postOperations = new PostOperations();
    private Bitmap bitmap;
    private User user = null;
    private GetOperations getOperations = new GetOperations();
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private ImageView imageViewSpinnerRefresh;
    protected Context context;
    private AnimationDrawable animationDrawable;
    SwipeListener swipeListener;
    private LinearLayout linearLayoutUserProfile;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        loggedInUser = (LoggedInUser) intent.getSerializableExtra("user");
        getCurrentLocation();
        user = getOperations.getUser(loggedInUser.getId());

        textViewProfileName = findViewById(R.id.textViewProfileName);
        textViewProfileEmail = findViewById(R.id.textViewProfileEmail);
        textViewProfileAbout = findViewById(R.id.textViewProfileAbout);
        textViewProfilePhone = findViewById(R.id.textViewProfilePhone);
        buttonProfileEdit = findViewById(R.id.buttonProfileEdit);
        imageViewProfileImage = findViewById(R.id.imageViewProfileImage);
        textboxUserLocation = findViewById(R.id.textboxUserLocation);
        linearLayoutUserProfile = findViewById(R.id.linearLayoutUserProfile);
        imageViewSpinnerRefresh = findViewById(R.id.imageViewSpinnerRefresh);
        imageViewSpinnerRefresh.setVisibility(View.INVISIBLE);

        imageViewSpinnerRefresh.setBackgroundResource(R.drawable.reload_spinner);
        animationDrawable = (AnimationDrawable) imageViewSpinnerRefresh.getBackground();

        String originalInput = loggedInUser.getProfile_image();
        originalInput = originalInput.replace("\n", "");
        byte[] result = Base64.getDecoder().decode(originalInput);
        Bitmap bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);

        imageViewProfileImage.setImageBitmap(bitmap);


        textViewProfileName.setText(user.getName());
        textViewProfileEmail.setText(user.getEmail());
        textViewProfileAbout.setText(user.getAbout());
        textViewProfilePhone.setText(user.getPhone());
        imageViewProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });
        buttonProfileEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditProfile = new Intent(UserProfileActivity.this, ProfileEditActivity.class);
                intentEditProfile.putExtra("user", user);
                intentEditProfile.putExtra("user_access_token", loggedInUser.getAccess_token());
                startActivity(intentEditProfile);
            }
        });

        swipeListener = new SwipeListener(linearLayoutUserProfile);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse("file:///" + picturePath));
                postOperations.updateProfilePicture(UserProfileActivity.this,
                        Bitmap.createScaledBitmap(bitmap, 500, 500, false),
                        loggedInUser.getAccess_token(),
                        loggedInUser.getId(),
                        loggedInUser);
                imageViewProfileImage.setImageDrawable(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.putExtra("user", loggedInUser);
        startActivity(intent);
        overridePendingTransition(R.anim.ltor, R.anim.fade_out);
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0){
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longtitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addressList = null;
                    try {
                        addressList  = geocoder.getFromLocation(latitude, longtitude, 1);
                        Address address = addressList.get(0);
                        String adressLine = address.getSubAdminArea();
                        textboxUserLocation.setText(adressLine.replace(" ", "/"));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, Looper.getMainLooper());
    }


    private class SwipeListener implements View.OnTouchListener {

        GestureDetector gestureDetector;
        SwipeListener(View view){
            int threshold = 100;
            int velocity_threshold = 100;

            GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }


                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    float xDiff = e2.getX() - e1.getX();
                    float yDiff = e2.getY() - e1.getY();

                    try{
                        if(Math.abs(xDiff) > Math.abs(yDiff)){
                            if(Math.abs(xDiff) > threshold && Math.abs(velocityX) > velocity_threshold){
                                if(xDiff > 0){
                                    Intent intent = new Intent(UserProfileActivity.this, HomepageActivity.class);
                                    intent.putExtra("user", loggedInUser);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.ltor, R.anim.fade_out);
                                }
                                else
                                {
                                    Log.d("Swiped", "Left");
                                }
                                return true;
                            }
                        }
                        else {
                            if(Math.abs(yDiff) > threshold && Math.abs(velocityY) > velocity_threshold){
                                if(yDiff > 0){
                                    imageViewSpinnerRefresh.setVisibility(View.VISIBLE);
                                    animationDrawable.start();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            imageViewSpinnerRefresh.setVisibility(View.INVISIBLE);
                                            animationDrawable.stop();
                                            finish();
                                            startActivity(getIntent());
                                        }
                                    }, 1500);

                                }
                                else
                                {
                                    Log.d("Swiped", "Up");
                                }
                                return true;
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    return false;
                }
            };
            gestureDetector = new GestureDetector(listener);
            view.setOnTouchListener(this);
        }
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            return gestureDetector.onTouchEvent(event);
        }
    }
}