package com.blueteam.base.cache.westcache.sample.RedisCache;

import com.blueteam.base.cache.westcache.sample.SampleCity;

import java.util.List;

/**
 * Created by  NastyNas on 2017/12/8.
 */
public interface RedisService {

    List<SampleCity> listSampleCity();

    SampleCity getSampleCity(String city, String name);

    List<SampleCity> getManyCity();

}
