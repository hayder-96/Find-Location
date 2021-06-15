package app.myapp.findlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

import app.myapp.findlocation.Adapter.MyAdapterCocat;
import app.myapp.findlocation.Model.ListItem;

public class MainActivity extends AppCompatActivity {



    Toolbar toolbar;
    RecyclerView recyclerView;
    MyAdapterCocat myAdapterCocat;
    Database database;
    Context context;
    Intent intent;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        toolbar=findViewById(R.id.toolbar);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.rec_main);

         database=new Database(this);

      intent=getIntent();

         id=intent.getIntExtra("id", -1);
        ArrayList<ListItem> arrayList = database.getData();
        if (id==99) {

            if (arrayList != null) {
                Double la=intent.getDoubleExtra("latitude",-1);
                Double lo=intent.getDoubleExtra("longtude",-1);


                myAdapterCocat = new MyAdapterCocat(arrayList, this,String.valueOf(la),String.valueOf(lo));
                recyclerView.setAdapter(myAdapterCocat);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                myAdapterCocat.notifyDataSetChanged();
            }
        }else {


            if (arrayList != null) {



                ItemTouchHelper.SimpleCallback itemTouchHelper= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                        myAdapterCocat.deletePlanet(viewHolder.getAdapterPosition());
                        myAdapterCocat.notifyDataSetChanged();



                    }
                };

                new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);





                myAdapterCocat = new MyAdapterCocat(arrayList, this);

                recyclerView.setAdapter(myAdapterCocat);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                myAdapterCocat.notifyDataSetChanged();
            }








        }








        }



        SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);


        MenuItem menuItem=menu.findItem(R.id.add);

        if (id==99){

            menuItem.setVisible(false);

        }

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                myAdapterCocat.getFilter().filter(newText);

                return false;
            }
        });



        return super.onCreateOptionsMenu(menu);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.add:




                Intent intent=new Intent(getBaseContext(),Main_Concat.class);
                intent.putExtra("id","one");
                startActivity(intent);
                finish();

                break;


        }


        return super.onOptionsItemSelected(item);
    }












}