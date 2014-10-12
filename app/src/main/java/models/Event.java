package models;

import android.graphics.Bitmap;
import android.media.Image;

import com.telerik.everlive.sdk.core.model.base.DataItem;
import com.telerik.everlive.sdk.core.serialization.ServerIgnore;
import com.telerik.everlive.sdk.core.serialization.ServerProperty;
import com.telerik.everlive.sdk.core.serialization.ServerType;

import java.util.Date;

/**
 * Created by Stefan on 10/11/2014.
 */
@ServerType("Event")
public class Event extends DataItem {
    @ServerProperty("City")
    private String city;
    @ServerProperty("Content")
    private String content;
    @ServerProperty("Date")
    private Date date;
    @ServerIgnore
    private Bitmap image;
    @ServerProperty("Latitude")
    private String latitude;
    @ServerProperty("Longitude")
    private String longitude;
    @ServerProperty("OrganizerName")
    private String organizerName;
    @ServerProperty("OrganizerPhone")
    private String organizerPhone;
    @ServerProperty("Title")
    private String title;
    @ServerProperty("SportType")
    private String sportType;

    public String getCity(){
        return this.city;
    }

    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public Date getDate(){
        return this.date;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public String getLatitude(){
        return this.latitude;
    }

    public String getLongitude(){
        return this.longitude;
    }

    public String getOrganizerName(){
        return this.organizerName;
    }

    public String getOrganizerPhone(){
        return this.organizerPhone;
    }

    public String getSportType(){
        return this.sportType;
    }

    public void setCity(String city){
        this.city = city;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setImage(Bitmap image){
       this.image = image;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public void setOrganizerName(String name){
        this.organizerName = name;
    }

    public void setOrganizerPhone(String phone){
        this.organizerPhone = phone;
    }

    public void setSportType(String sportType){
        this.sportType = sportType;
    }
}
