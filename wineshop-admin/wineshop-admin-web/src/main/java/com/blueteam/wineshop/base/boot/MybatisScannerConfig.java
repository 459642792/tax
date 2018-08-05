package com.blueteam.wineshop.base.boot;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by  NastyNas on 17/8/12.
 * <p/>
 * question:
 * 为什么MapperScannerConfigurer扫描器要单独配置？
 * <p/>
 * answer:
 * mapperScannerConfigurer 实现了 BeanDefinitionRegistryPostProcessor 因此MapperScannerConfigurer的构建会发生在所有bean的初始化之前
 * MapperScannerConfigurer将会被优先构建，导致MybatisScannerConfig所在的@Configuration类以及类中其他@Bean 也会被优先构建。
 * <p/>
 * 因此当前类中无法再通过@Autowired注入其他组件（其他组件还没有被构建），如果强行注入，会出现NPE
 */
@Configuration
@IgnoreMultiComponentScan(ignore = true)
public class MybatisScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
        mapperScannerConfigurer.setBasePackage("com.blueteam.wineshop.mapper");
        return mapperScannerConfigurer;
    }
}
