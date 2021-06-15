package app.myapp.findlocation;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

public class ManagmentOperations {


     public static String FormatPhoneNumber(String Oldnmber){
      try{
          String numberOnly= Oldnmber.replaceAll("[^0-9]", "");

           return(numberOnly);
      }
      catch (Exception ex){
          return(" ");
      }
    }

}
