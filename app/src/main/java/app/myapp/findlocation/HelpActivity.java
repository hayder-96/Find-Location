package app.myapp.findlocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HelpActivity extends AppCompatActivity {


    ImageView imageView;
    TextView textView;
    Button button;
    int [] image;
    String [] text;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        imageView=findViewById(R.id.image_help);
        textView=findViewById(R.id.textView_help);
        button=findViewById(R.id.but_help);




       AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        image=new int[]{R.drawable.l1,R.drawable.l2,R.drawable.l3,R.drawable.l4};
        text=new String[]{getResources().getString(R.string.first),getResources().getString(R.string.second),getResources().getString(R.string.third),getResources().getString(R.string.fourth)};


        imageView.setImageResource(R.drawable.l1);
        textView.setText(getResources().getString(R.string.first));


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageView.setImageResource(image[i]);
                textView.setText(text[i]);

                i++;

                if (i>3){
                    i=0;
                }





            }
        });





    }
}