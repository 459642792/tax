package com.blueteam.base.cache.westcache.sample.RedisCache;

import com.blueteam.base.cache.westcache.base.RedisCache;
import com.blueteam.base.cache.westcache.sample.SampleCity;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by  NastyNas on 2017/12/8.
 */
@Component
public class RedisServiceImpl implements RedisService {


    /**
     * 使用redis缓存信息，缓存生效时间为1天
     *
     * @return
     */
    @Override
    @RedisCache(expireAfterWrite = "1d")
    public List<SampleCity> listSampleCity() {
        List<SampleCity> cityList = Lists.newArrayList();
        SampleCity hangzhou = new SampleCity("hangzhou", "杭州");
        SampleCity guangdong = new SampleCity("guangdong", "广东");
        cityList.add(hangzhou);
        cityList.add(guangdong);
        return cityList;
    }

    /**
     * 使用redis缓存信息，每30分钟自动刷新缓存
     *
     * @return
     */
    @Override
    @RedisCache(flusher = "quartz", scheduled = "Every 30 minutes")
    public SampleCity getSampleCity(String city, String name) {
        return new SampleCity("hefei", "合肥");
    }

    /**
     * 使用redis缓存信息，每天凌晨3点刷新缓存
     * 假定getManyCity()方法返回数据量太大，可能存在缓存获取超时的情况，
     * 使用文件快照来处理缓存获取超时情况，一旦超时，便从服务器文件系统中读取快照
     *
     * @return
     */
    @Override
    @RedisCache(flusher = "quartz", scheduled = "At 03:00", snapshot = "file")
    public List<SampleCity> getManyCity() {
        List<SampleCity> cityList = Lists.newArrayList();
        SampleCity niuyue = new SampleCity("hangzhou", "纽约");
        SampleCity luoshanji = new SampleCity("guangdong", "洛杉矶");
        cityList.add(niuyue);
        cityList.add(luoshanji);
        return cityList;
    }
}
