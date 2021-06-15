package app.myapp.findlocation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app.myapp.findlocation.Database;
import app.myapp.findlocation.ManagmentOperations;
import app.myapp.findlocation.MapsActivity;
import app.myapp.findlocation.Model.ListItem;
import app.myapp.findlocation.R;

public class MyAdapterLocation extends RecyclerView.Adapter<MyAdapterLocation.MyHolder> {

    ArrayList<ListItem>arrayList;
    Context context;

    public MyAdapterLocation(ArrayList<ListItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyAdapterLocation.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view=LayoutInflater.from(context).inflate(R.layout.split_location_users, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterLocation.MyHolder holder, int position) {

        ListItem listItem=arrayList.get(position);

        holder.name.setText(listItem.name);
        holder.phone.setText(ManagmentOperations.FormatPhoneNumber(listItem.phone));

        String lat=listItem.lati;
        String log=listItem.log;
        String name=listItem.name;



        if (lat==null&&log==null){
            holder.itemView.setVisibility(View.GONE);

        }

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=new Intent(context, MapsActivity.class);
                    intent.putExtra("lat",lat);
                    intent.putExtra("log",log);
                    intent.putExtra("name",name);
                    context.startActivity(intent);
                }
            });



        }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        Button button;
        TextView name,phone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);


            button=itemView.findViewById(R.id.but_loc_user);
            name=itemView.findViewById(R.id.user_name);
            phone=itemView.findViewById(R.id.phone_user);
        }
    }
}
