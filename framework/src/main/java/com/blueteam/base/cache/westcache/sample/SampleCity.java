package com.blueteam.base.cache.westcache.sample;

/**
 * Created by  NastyNas on 2017/12/8.
 * com.blueteam.base.cache.westcache.sample.DirectTable.SampleLoader
 */
public class SampleCity {
    //城市拼音
    String city;
    //城市全称
    String name;

    public SampleCity() {
    }

    public SampleCity(String city, String name) {
        this.city = city;
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SampleCity{" +
                "city='" + city + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
