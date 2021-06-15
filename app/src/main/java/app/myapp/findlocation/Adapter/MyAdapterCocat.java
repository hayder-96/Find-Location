package app.myapp.findlocation.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.myapp.findlocation.Database;
import app.myapp.findlocation.ManagmentOperations;
import app.myapp.findlocation.Model.ListItem;
import app.myapp.findlocation.R;

public class MyAdapterCocat extends RecyclerView.Adapter<MyAdapterCocat.MyHolder> implements Filterable {

    ArrayList<ListItem> arrayList;
    Context context;
    String lat;
    String log;
     ArrayList<ListItem> list;

    public MyAdapterCocat(ArrayList<ListItem> arrayList, Context context, String lat, String log) {
        this.arrayList = arrayList;
        this.context = context;
        this.lat = lat;
        this.log = log;
        this.list = new ArrayList<>();
        this.list.addAll(arrayList);

    }

    public MyAdapterCocat(ArrayList<ListItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        this.list = new ArrayList<>();
        this.list.addAll(arrayList);

    }

    @NonNull
    @Override
    public MyAdapterCocat.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.split_concat, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterCocat.MyHolder holder, int position) {

        ListItem listItem = arrayList.get(position);

        holder.name.setText(listItem.name);
        holder.phone.setText(ManagmentOperations.FormatPhoneNumber(listItem.phone));
        ListItem listItemm = new Database(context).getOneItem(ManagmentOperations.FormatPhoneNumber(listItem.phone));

        if (listItemm!=null) {


        }
        if (listItem.ImageURL != 0) {
            holder.imageView.setImageResource(listItem.ImageURL);


        }
        if (listItem.ImageURL == R.drawable.gpsoff) {

            if (listItemm!=null) {
                holder.imageDone.setVisibility(View.VISIBLE);
                holder.imageAdd.setVisibility(View.GONE);
                return;
            }else {
                holder.imageDone.setVisibility(View.GONE);
                holder.imageAdd.setVisibility(View.VISIBLE);

            }
            holder.imageAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        try {


                            holder.imageDone.setVisibility(View.VISIBLE);
                            holder.imageAdd.setVisibility(View.GONE);



                            Database database = new Database(context);
                            database.insertData(new ListItem(listItem.name, ManagmentOperations.FormatPhoneNumber(listItem.phone), R.drawable.gpson, null, null));

                        } catch (Exception e) {
                            Toast.makeText(context, e+"", Toast.LENGTH_SHORT).show();
                        }
                    }



            });
        } else if (listItem.ImageURL == R.drawable.gpson && lat == null && log == null) {

            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Alert(listItem.name,listItemm.phone,"?&I_need_location_you?&",  context.getString(R.string.get_location_sms)+listItem.name);
//                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//                    smsIntent.setData(Uri.parse("smsto:"));
//                    smsIntent.setType("vnd.android-dir/mms-sms");
//                    smsIntent.putExtra("address", listItemm.phone);
//                    smsIntent.putExtra("sms_body", "?&I_need_location_you?&");
//                    context.startActivity(smsIntent);

                }
            });


        } else if (listItem.ImageURL == R.drawable.gpson && lat != null && log != null) {

            holder.button.setVisibility(View.VISIBLE);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Alert(listItem.name,listItemm.phone,"*$%" + lat + "%" + log + "%$*", context.getString(R.string.send_sms_to_concat)+listItem.name);


                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public void deletePlanet(int pos) {

        ListItem p = arrayList.get(pos);
        arrayList.remove(pos);
        int id = p.getId();
        new Database(context).delete(id);

    }



    @Override
    public Filter getFilter() {
        return filter;
    }


    final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<ListItem> listflter = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {

                listflter.addAll(list);
            } else {

                String filterList = constraint.toString().toLowerCase().trim();
                for (ListItem item : list) {

                    if (item.name.toLowerCase().contains(filterList)) {
                        listflter.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = listflter;
            return results;


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            arrayList.clear();
            arrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }


    };
























    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView,imageAdd,imageDone;
        TextView name, phone;
        Button button;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView_concat);
            name = itemView.findViewById(R.id.text_name);
            phone = itemView.findViewById(R.id.text_phone);
            button=itemView.findViewById(R.id.but_send_sms);
            imageAdd=itemView.findViewById(R.id.add_concat);
             imageDone=itemView.findViewById(R.id.image_done);



        }


    }




    private void Alert(String name,String phone,String message,String content){

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
         builder.setTitle(context.getResources().getString(R.string.add_me));
        builder.setMessage(content);
        builder.setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SmsManager.getDefault().sendTextMessage(phone,null,message,null,null);


            }
        });
        builder.setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }




}
