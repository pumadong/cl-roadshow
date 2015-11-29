package com.cl.roadshow.algorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 二分查找
 *
 */
public class BinarySearchDemo {
    
    private static final Logger log = LoggerFactory.getLogger("console");
    
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,6,7,8,9,10};
        int pos = binSearch(arr,10,3);
        log.info("非递归方法，查找3所在的索引位置：" + pos);
        
        int pos2 = binSearch(arr,0,9,3);
        log.info(" 递归方法，查找3所在的索引位置：" + pos2);
    }
    
    private static int binSearch(int Array[],int low,int high,int key)
    {
        if (low<=high)
        {
            int mid = (low+high)/2;
            if(key == Array[mid]) {
                return mid;
            }
            else if(key<Array[mid])
                return binSearch(Array,low,mid-1,key);
            else {
                return binSearch(Array,mid+1,high,key);
            }
        }
        else {
            return -1;
        }
    }
    
    private static int binSearch(int Array[],int SizeOfArray,int key)
    {
        int low=0,high=SizeOfArray-1;
        int mid;
        while (low<=high)
        {
            mid = (low+high)/2;
            if(key==Array[mid])
                return mid;
            if(key<Array[mid])
                high=mid-1;
            if(key>Array[mid])
                low=mid+1;
        }
        return -1;
    }
}
