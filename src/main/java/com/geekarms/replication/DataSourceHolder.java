package com.geekarms.replication;


/**
 * Created by kaywer on 2017/1/18.
 */
public class DataSourceHolder {
    public static final String MASTER = "master";

    public static final String SLAVE = "slave";

    public static final ThreadLocal<String> dataSourceLocal = new ThreadLocal<String>();

    public static void setDataSource(String dataSource){
        dataSourceLocal.set(dataSource);
    }

    public static String getDataSource(){
        if (dataSourceLocal.get() == null){
            dataSourceLocal.set(MASTER);
        }
        return dataSourceLocal.get();
    }

    public static void setMaster(){
        setDataSource(MASTER);
    }

    public static void setSlave(){
        setDataSource(SLAVE);
    }

    public static boolean isMaster(){
        return getDataSource().equals(MASTER);
    }

    public static boolean isSlave(){
        return getDataSource().equals(SLAVE);
    }

    public static void clearDataSource(){
        dataSourceLocal.remove();
    }


}
