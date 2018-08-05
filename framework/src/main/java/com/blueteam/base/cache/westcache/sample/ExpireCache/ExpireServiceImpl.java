package com.blueteam.base.cache.westcache.sample.ExpireCache;

import com.blueteam.base.cache.westcache.base.ExpireCache;
import com.blueteam.base.cache.westcache.sample.SampleCity;
import org.springframework.stereotype.Service;

/**
 * Created by  NastyNas on 2017/12/8.
 */
@Service
public class ExpireServiceImpl implements ExpireService {
    /**
     * 首次调用getSampleCity()方法会执行方法体,
     * 之后的60s内再次调用方法，直接从缓存获取数据，不再调用方法体
     *
     * @return
     */
    @Override
    @ExpireCache(expireAfterWrite = "60s")
    public SampleCity getSampleCity(String name, String city) {
        SampleCity nanjing = new SampleCity(name, city);
        return nanjing;
    }
}
