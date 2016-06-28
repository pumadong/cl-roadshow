package com.cl.sonar;

import java.io.Serializable;

/**
 * Fields in a "Serializable" class should either be transient or serializable
 * so T extends Serializable
 */
public class ResultVo<T extends Serializable> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private T data;
	
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
