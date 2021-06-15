package app.myapp.findlocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import app.myapp.findlocation.Adapter.MyAdapterLocation;
import app.myapp.findlocation.Model.ListItem;

public class List_Location extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__location);


        recyclerView=findViewById(R.id.rec_location_users);

        textView=findViewById(R.id.text_rec);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Database database=new Database(this);
        ArrayList<ListItem> arrayList=database.getData();

        if (arrayList!=null) {
            textView.setVisibility(View.GONE);
            MyAdapterLocation myAdapterLocation = new MyAdapterLocation(arrayList, this);


            recyclerView.setAdapter(myAdapterLocation);


            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);


            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setHasFixedSize(true);

        }else {

            textView.setVisibility(View.VISIBLE);
        }
    }
}