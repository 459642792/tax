package com.blueteam.base.cache.westcache.sample.DirectTable;

import com.blueteam.base.cache.westcache.base.DirectTableCache;
import com.blueteam.base.cache.westcache.sample.SampleCity;

import java.util.List;

/**
 * Created by  NastyNas on 2017/12/8.
 */
public interface DirectSpecialService {
    //数据来源:WESTCACHE_FLUSHER表DIRECT_VALUE字段(json类型)
    @DirectTableCache(key = "listSampleCityJson")
    List<SampleCity> listSampleCityJson();

    //数据来源
    @DirectTableCache(key = "listSampleCityLoader")
    List<SampleCity> listSampleCityLoader();


}
