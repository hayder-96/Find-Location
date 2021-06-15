package app.myapp.findlocation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyLocation extends FragmentActivity implements OnMapReadyCallback {

    Button button, but_s,getLoc,sendLoc;


    TextView textView,textView2,text_adress;
    ProgressBar progressBar;
    LinearLayout layout1,layout2;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder geocoder;
    List<Address> addresses = null;
    String addresss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_location);


        textView=findViewById(R.id.text_loca);
        textView2=findViewById(R.id.text_loca2);
        progressBar=findViewById(R.id.progressBar11);
        getLoc=findViewById(R.id.but_getloc);
        sendLoc=findViewById(R.id.but_send_loc);
        button = findViewById(R.id.button6);
        but_s = findViewById(R.id.but_send_myloc);
        layout1=findViewById(R.id.layout1);
        layout2=findViewById(R.id.layout2);
        text_adress=findViewById(R.id.text_loc_adress);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent=getIntent();

        int type=intent.getIntExtra("type", -1);

        if (type==22){
            layout1.setVisibility(View.VISIBLE);
            layout2.setVisibility(View.GONE);
        }else {
            layout2.setVisibility(View.VISIBLE);
            layout1.setVisibility(View.GONE);
            text_adress.setVisibility(View.VISIBLE);
            Alert();
            turnGps();

        }



        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                re();
            }
        });


        sendLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putExtra("id", 99);
                intent.putExtra("latitude",Double.parseDouble(textView.getText().toString()));
                intent.putExtra("longtude", Double.parseDouble(textView2.getText().toString()));

                startActivity(intent);

            }
        });

        but_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Location location = myLocation();
                if (location != null) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.putExtra("id", 99);
                    intent.putExtra("latitude", location.getLatitude());
                    intent.putExtra("longtude", location.getLongitude());

                    startActivity(intent);


                }

            }
        });

    }

    public void addMyLoc(View view) {


    }


    public Location myLocation() {


        try {

            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {

                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_FINE);

                criteria.setCostAllowed(false);

                String provider = locationManager.getBestProvider(criteria, false);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                }
                Location lastKnownLocationGPS = locationManager.getLastKnownLocation(provider); //(LocationManager.GPS_PROVIDER);
                if (lastKnownLocationGPS != null) {
                    return lastKnownLocationGPS;
                } else {
                    Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    return loc;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(getApplicationContext());

                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }


                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {


                        Location location = task.getResult();


                        if (location != null) {


                            Geocoder geocoder;
                            List<Address> addresses = null;
                            String addresss = null;
                            geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                                if (addresses != null) {
                                    addresss = addresses.get(0).getAddressLine(0);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                            LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());

                            if (!TextUtils.isEmpty(addresss)) {
                                googleMap.addMarker(new MarkerOptions().position(sydney).title(addresss));

                            }
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
                            // googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.try_gps), Toast.LENGTH_SHORT).show();
                        }
                    }

                });
            }
        });
    }








    public void re() {

        progressBar.setVisibility(View.VISIBLE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);






            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    if (location != null) {


                        textView.setText(location.getLatitude() + "");
                        textView2.setText(location.getLongitude() + "");

                        sendLoc.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if (addresses!=null) {
                             addresss = addresses.get(0).getAddressLine(0);
                        }
                        if (!TextUtils.isEmpty(addresss)){
                            text_adress.setText(addresss);

                        }else {

                        }

                        try {
                            locationManager.removeUpdates(locationListener);
                            locationManager = null;

                        }catch (Exception e){

                        }
                    }

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {


                }

                @Override
                public void onProviderEnabled(String provider) {


                }

                @Override
                public void onProviderDisabled(String provider) {

                    progressBar.setVisibility(View.GONE);

                }
            };
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);





    }




    private void Alert(){


        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setMessage(getString(R.string.turn_stalait));

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        builder.show();

    }


    private void turnGps() {

        boolean gps_enabled = false;
        boolean network_enabled = false;

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.turn_Gps))
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })

                    .show();

        }


    }
    }





