package models;

import android.media.Image;

import com.telerik.everlive.sdk.core.model.base.DataItem;

import java.sql.Date;

/**
 * Created by Stefan on 10/11/2014.
 */
public class Event extends DataItem {
    private String city;
    private String content;
    private Date date;
    private Image image;
    private String latitude;
    private String longitude;
    private String organizerName;
    private String organizerPhone;
    private String title;
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

    public Image getImage(){
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

    public void setImage(Image image){
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
