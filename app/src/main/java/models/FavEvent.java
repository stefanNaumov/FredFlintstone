package models;

/**
 * Created by Rusekov on 14/10/2014.
 */
public class FavEvent {
    private int id;
    private String everliteIndex;

    public FavEvent(){}

    public FavEvent(String everliteIndex) {
        super();
        this.everliteIndex = everliteIndex;
    }


    //Todo: if needed getters & setters
    public String getEverliteIndex(){
        return this.everliteIndex;
    }

    public void setEverliteIndex(String index){
        this.everliteIndex = index;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int val){
        this.id = val;
    }


    @Override
    public String toString() {
        return "FavEvent [id=" + id + ", everliteIndex=" + everliteIndex + "]";
    }
}