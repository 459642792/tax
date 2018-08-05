//package com.blueteam.base.cache.westcache.sample;
//
//import com.blueteam.base.cache.westcache.sample.DirectTable.DirectSpecialService;
//import com.blueteam.base.cache.westcache.sample.ExpireCache.ExpireService;
//import com.blueteam.base.cache.westcache.sample.RedisCache.RedisService;
//import com.blueteam.entity.dto.ApiResult;
//import com.blueteam.entity.dto.BaseResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.util.List;
//
///**
// * westcache 使用试例
// * Created by  NastyNas on 2017/12/8.
// */
//@Controller("sample")
//public class SampleController {
//
//    @Autowired
//    DirectSpecialService directSpecialService;
//
//    @Autowired
//    ExpireService expireService;
//
//    @Autowired
//    RedisService redisService;
//
//    /**
//     * DirectTableCache试例一
//     * WESTCACHE_FLUSHER表DIRECT_VALUE字段数据
//     *
//     * @return
//     */
//    @RequestMapping(value = "/directJson", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult directJson() {
//        List<SampleCity> cityList = directSpecialService.listSampleCityJson();
//        return ApiResult.success(cityList);
//    }
//
//    /**
//     * DirectTableCache试例二
//     * Callable的回调类的call方法返回结果
//     *
//     * @return
//     */
//    @RequestMapping(value = "/directLoader", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult directLoader() {
//        List<SampleCity> cityList = directSpecialService.listSampleCityLoader();
//        return ApiResult.success(cityList);
//    }
//
//
//    /**
//     * ExpireCache试例
//     *
//     * @return
//     */
//    @RequestMapping(value = "/expire", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult expire(String city, String name) {
//        SampleCity sampleCity = expireService.getSampleCity(city, name);
//        return ApiResult.success(sampleCity);
//    }
//
//    /**
//     * ExpireCache试例
//     *
//     * @return
//     */
//    @RequestMapping(value = "/redisSimple", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult redisSimple(String city, String name) {
//        SampleCity sampleCity = expireService.getSampleCity(city, name);
//        return ApiResult.success(sampleCity);
//    }
//
//    /**
//     * redis 试例一
//     *
//     * @return
//     */
//    @RequestMapping(value = "/redisSimple", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult redisSimple() {
//        List<SampleCity> cityList = redisService.listSampleCity();
//        return ApiResult.success(cityList);
//    }
//
//    /**
//     * redis 试例二
//     *
//     * @return
//     */
//    @RequestMapping(value = "/redisSimple", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult redisQuartz(String city, String name) {
//        SampleCity cityList = redisService.getSampleCity(city, name);
//        return ApiResult.success(cityList);
//    }
//
//    /**
//     * redis 试例三
//     *
//     * @return
//     */
//    @RequestMapping(value = "/redisSimple", method = RequestMethod.GET)
//    @ResponseBody
//    public BaseResult redisSnapshot() {
//        List<SampleCity> cityList = redisService.getManyCity();
//        return ApiResult.success(cityList);
//    }
//
//
//}
