package com.blueteam.wineshop.base.boot;

import com.blueteam.base.util.DiamondUtil;
import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

import static com.blueteam.base.constant.DiamondConstant.DB_DATA;

/**
 * Created by  NastyNas on 17/8/18.
 * <p/>
 * 数据库和mybatis配置
 * <p/>
 */
@Configuration
@EnableTransactionManagement
@IgnoreMultiComponentScan(ignore = true)
public class RepositoryConfig {

    /**
     * 数据源（DBCP）
     *
     * @return
     */
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DiamondUtil.getPre(DB_DATA, "db.driverClassName"));
        ds.setUrl(DiamondUtil.getPre(DB_DATA, "db.url"));
        ds.setUsername(DiamondUtil.getPre(DB_DATA, "db.username"));
        ds.setPassword(DiamondUtil.getPre(DB_DATA, "db.password"));
//        ds.setUrl("jdbc:mysql://47.92.105.103:3306/fjjh?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&tinyInt1isBit=false");
//        ds.setUsername("root");
//        ds.setPassword("lshd123");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.blueteam.entity.bo,;com.blueteam.entity.dto,;com.blueteam.entity.po,;com.blueteam.entity.vo");
        //mybatis 启用缓存
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);//全局映射器启用缓存
        configuration.setLazyLoadingEnabled(false);//查询时，关闭关联对象即时加载以提高性能
        configuration.setMultipleResultSetsEnabled(true);//对于未知的SQL查询，允许返回不同的结果集以达到通用的效果
        configuration.setAggressiveLazyLoading(true);//设置关联对象加载的形态，此处为按需加载字段(加载字段由SQL指 定)，不会加载关联表的所有字段，以提高性能
        sqlSessionFactoryBean.setConfiguration(configuration);

//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        String locationPattern = "config/mybatis/mappers*//*.xml";
//        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:" + locationPattern));
//        sqlSessionFactoryBean.setTypeAliasesPackage("org.com.myspring.dao.pojo");
        return sqlSessionFactoryBean;
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean().getObject());
        return sqlSessionTemplate;
    }

    /**
     * spring 基于jdbc的事务管理
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }


}
