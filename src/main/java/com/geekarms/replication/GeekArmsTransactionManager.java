package com.geekarms.replication;

import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;


/**
 * Created by kaywer on 2017/1/18.
 */
public class GeekArmsTransactionManager extends HibernateTransactionManager {
    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition) {
        if (definition.isReadOnly()){
            DataSourceHolder.setSlave();
        }else {
            DataSourceHolder.setMaster();
        }

        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCleanupAfterCompletion(Object transaction) {
        super.doCleanupAfterCompletion(transaction);
        DataSourceHolder.clearDataSource();
    }
}
