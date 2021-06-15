package app.myapp.findlocation;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String la,lo;

    TextView name,address,city,state,country,postalcode,knownname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        name = findViewById(R.id.text_name_lo);
        address = findViewById(R.id.text_adress);
        city = findViewById(R.id.text_city);
        state = findViewById(R.id.text_state);
        country = findViewById(R.id.text_country);
        postalcode = findViewById(R.id.text_postalcode);
        knownname = findViewById(R.id.text_knownname);

        Intent intent = getIntent();
        la = intent.getStringExtra("lat");
        lo = intent.getStringExtra("log");
        String nn = intent.getStringExtra("name");
        name.setText(nn);


        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(la), Double.parseDouble(lo), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }




        try {

            if (addresses != null) {
                String addresss = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String cityy = addresses.get(0).getLocality();
                String statee = addresses.get(0).getAdminArea();
                String countryy = addresses.get(0).getCountryName();
                String postalCodee = addresses.get(0).getPostalCode();
                String knownNamee = addresses.get(0).getFeatureName();

                if (!TextUtils.isEmpty(addresss)) {

                    address.setText(addresss);
                }

                if (!TextUtils.isEmpty(cityy)) {

                    city.setText(cityy);
                }


                if (!TextUtils.isEmpty(statee)) {

                    state.setText(statee);
                }


                if (!TextUtils.isEmpty(countryy)) {

                    country.setText(countryy);
                }


                if (!TextUtils.isEmpty(postalCodee)) {

                    postalcode.setText(postalCodee);
                }


                if (!TextUtils.isEmpty(knownNamee)) {

                    knownname.setText(knownNamee);
                }
            }

        }catch (Exception e){

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng sydney = new LatLng(Double.parseDouble(la),Double.parseDouble(lo));

        mMap.addMarker(new MarkerOptions().position(sydney).title(address.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 7));
    }

    public void Default(View view) {
        LatLng sydney = new LatLng(Double.parseDouble(la),Double.parseDouble(lo));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void setalait(View view) {

        LatLng sydney = new LatLng(Double.parseDouble(la),Double.parseDouble(lo));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,7));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }






}