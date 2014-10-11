package models;

import com.telerik.everlive.sdk.core.EverliveApp;

/**
 * Created by Stefan on 10/12/2014.
 */
public class Everlive {
    private static EverliveApp everlive = null;



    public static EverliveApp getEverliveObj(){
        if (everlive == null){
            everlive = new EverliveApp("Se1uyHp5A8LQJMr6");
        }

        return everlive;
    }
}
