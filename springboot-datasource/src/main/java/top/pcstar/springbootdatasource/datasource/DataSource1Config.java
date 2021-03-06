package top.pcstar.springbootdatasource.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: PanChao
 * @Description: DataSource1Config
 * @Date: Created in 15:15 2018/7/16
 */
@MapperScan(basePackages = "top.pcstar.springbootdatasource.test1",sqlSessionFactoryRef = "test1SqlSessionFactory")
@Configuration
public class DataSource1Config {
    /**
     * 获取DataSourceProperties配置文件
     * @return
     */
    @Primary
    @Bean("test1DataSourceProperties")
    @ConfigurationProperties("spring.datasource.test1")
    public DataSourceProperties getDataSourceProperties(){
        return new DataSourceProperties();
    }
    /**
     * 创建数据源
     *
     * @return
     */
    @Primary
    @Bean("test1DataSource")
    public DataSource getDataSource() {
        return getDataSourceProperties().initializeDataSourceBuilder().build();
    }

    /**
     * test1 sql会话工厂
     *
     * @param test1DataSource
     * @return
     * @throws Exception
     */
    @Primary
    @Bean("test1SqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(@Qualifier("test1DataSource") DataSource test1DataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(test1DataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/test1/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * test1 事务管理
     *
     * @param test1DataSource
     * @return
     */
    @Primary
    @Bean("test1DataSourceTransactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("test1DataSource") DataSource test1DataSource) {
        return new DataSourceTransactionManager(test1DataSource);
    }

    /**
     * test1 sql会话模板
     * @param test1SqlSessionFactory
     * @return
     */
    @Primary
    @Bean("test1SqlSessionTemplate")
    public SqlSessionTemplate getSqlSessionTemplate(@Qualifier("test1SqlSessionFactory") SqlSessionFactory test1SqlSessionFactory) {
        return new SqlSessionTemplate(test1SqlSessionFactory);
    }
}
