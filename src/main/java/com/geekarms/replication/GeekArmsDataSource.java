package com.geekarms.replication;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kaywer on 2017/1/18.
 */
public class GeekArmsDataSource extends AbstractRoutingDataSource{
    private AtomicInteger counter = new AtomicInteger(-1);

    private List<Object> slaveKeys = new ArrayList<>();

    private Integer slaveCount;

    @Override
    protected Object determineCurrentLookupKey() {
        if (DataSourceHolder.isMaster()){
            return DataSourceHolder.getDataSource();
        }

        return getSlaveKey();
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        // 由于父类的resolvedDataSources属性是私有的子类获取不到，需要使用反射获取
        Field field = ReflectionUtils.findField(AbstractRoutingDataSource.class, "resolvedDataSources");
        field.setAccessible(true); // 设置可访问

        try {
            Map<Object, DataSource> resolvedDataSources = (Map<Object, DataSource>) field.get(this);
            // 读库的数据量等于数据源总数减去写库的数量
            this.slaveCount = resolvedDataSources.size() - 1;
            for (Map.Entry<Object, DataSource> entry : resolvedDataSources.entrySet()) {
                if (DataSourceHolder.MASTER.equals(entry.getKey())) {
                    continue;
                }
                slaveKeys.add(entry.getKey());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getSlaveKey(){
        Integer index = counter.incrementAndGet() % slaveCount;
        if (counter.get() > 9999) { // 以免超出Integer范围
            counter.set(-1); // 还原
        }
        return slaveKeys.get(index);
    }
}
