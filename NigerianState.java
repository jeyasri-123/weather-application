package com.nivek.kevoweather;

import java.util.ArrayList;
import java.util.List;

public class NigerianState {

    private String latitude ;
    private String longitude ;
    private String name ;
    private String desc ;
    private String lowTemp ;
    private String highTemp ;
    private String icon ;

    public static NigerianState[] nigerianStates ={
            new NigerianState("Lagos" , "6.454066","3.394673"),
            new NigerianState("Kano" , "12.002381","8.51316") ,
            new NigerianState("Abuja","9.083333","7.533333"),
            new NigerianState("Kaduna","10.526413","7.438795"),
            new NigerianState("Benin City","6.338153","5.625749"),
            new NigerianState("Ikare","7.525913","5.753417") ,
            new NigerianState("Port Harcourt","4.777423","7.013404"),
            new NigerianState("Ogbomoso","8.133725","4.240144"),
            new NigerianState("Aba","5.106576","7.366667"),
            new NigerianState("Maiduguri","11.846924","13.157122"),
            new NigerianState("Zaria","11.078945","7.710359"),
            new NigerianState("Warri","5.51737","5.750064"),
            new NigerianState("Jos","9.92849","8.892118"),
            new NigerianState("Ilorin","8.496642","4.542143"),
            new NigerianState("Oyo","7.852575","3.931249"),
            new NigerianState("Sokoto","13.048025","5.214285"),
            new NigerianState("Enugu","6.441321","7.498834"),
            new NigerianState("Abeokuta","7.15571","3.345086"),
            new NigerianState("Uyo","5.033333","7.92657"),
            new NigerianState("Awka","6.21269","7.071986"),
            new NigerianState("Ile-Ife","7.482405","4.560324"),
            new NigerianState("Calabar","4.958931","8.32695"),
            new NigerianState("Ado-Ekiti","7.623289","5.22087"),
            new NigerianState("Katsina" ,"12.990823","7.601769"),
            new NigerianState("Akure","7.25256","5.193118")
    } ;

    private NigerianState(String name , String latitude , String longitude){
        this.name = name ;
        this.latitude = latitude ;
        this.longitude = longitude ;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(String lowTemp) {
        this.lowTemp = lowTemp;
    }

    public String getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(String highTemp) {
        this.highTemp = highTemp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
