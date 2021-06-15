package app.myapp.findlocation.Model;


public class ListItem {

    public int id;
    public String name;
    public String phone;
    public int ImageURL;
    public String lati;
    public String log;

    public ListItem(String name, String phone, int ImageURL,String lat,String log)
    { this. name=name;
        this. phone=phone;
        this. ImageURL=ImageURL;
        this.lati=lat;
        this.log=log;
    }

    public ListItem(int id, String name, String phone, int imageURL, String lati, String log) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        ImageURL = imageURL;
        this.lati = lati;
        this.log = log;
    }

    public ListItem(String name, String phone, int imageURL) {
        this.name = name;
        this.phone = phone;
        ImageURL = imageURL;
    }

    public ListItem(int id,String lati, String log) {
        this.lati = lati;
        this.log = log;
        this.id=id;

    }

    public int getId() {
        return id;
    }
}
