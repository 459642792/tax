package com.blueteam.base.help.generator;

/**
 * 主键key生成器
 * Created by  NastyNas on 2017/12/26.
 */
public interface KeyGenerator {

    /**
     * 生产Long型主键
     *
     * @param
     * @return
     */
    Long generateKey();
}