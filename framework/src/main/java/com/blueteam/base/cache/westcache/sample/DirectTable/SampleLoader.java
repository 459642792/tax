package com.blueteam.base.cache.westcache.sample.DirectTable;

import com.blueteam.base.cache.westcache.sample.SampleCity;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by  NastyNas on 2017/12/8.
 * <p>
 * 试例数据加载器
 */
public class SampleLoader implements Callable {
    @Override
    public Object call() throws Exception {
        List<SampleCity> cityList = Lists.newArrayList();
        SampleCity beijing = new SampleCity("beijing", "北京");
        SampleCity shanghai = new SampleCity("beijing", "上海");
        cityList.add(beijing);
        cityList.add(shanghai);
        return cityList;
    }
}
