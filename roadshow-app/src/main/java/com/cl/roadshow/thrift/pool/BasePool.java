package com.cl.roadshow.thrift.pool;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * 连接池泛型基础类
 * 
 */
public abstract class BasePool<T> {

    private final GenericObjectPool<T> internalPool;

    public BasePool(final GenericObjectPool.Config poolConfig, PoolableObjectFactory<T> poolFactory) {
        this.internalPool = new GenericObjectPool<T>(poolFactory, poolConfig);
    }

    public T getResource() throws Exception {
        try {
            return (T) internalPool.borrowObject();
        } catch (Exception e) {
            throw e;
        }
    }
        
    public void returnResourceObject(final T resource) throws Exception {
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void returnBrokenResource(final T resource) throws Exception {
        returnBrokenResourceObject(resource);
    }
    
    public void returnResource(final T resource) throws Exception {
        returnResourceObject(resource);
    }

    protected void returnBrokenResourceObject(final T resource) throws Exception {
        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw e;
        }
    }

    public void destroy() throws Exception {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public int currentActives(){
        return internalPool.getNumActive();
    }

}