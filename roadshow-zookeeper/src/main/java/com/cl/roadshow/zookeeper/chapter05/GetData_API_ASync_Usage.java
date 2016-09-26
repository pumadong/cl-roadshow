package com.cl.roadshow.zookeeper.chapter05;

import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

// ZooKeeper API 获取节点数据内容，使用异步(async)接口。
public class GetData_API_ASync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk;

    public static void main(String[] args) throws Exception {

    	String path = "/zk-book";
    	zk = new ZooKeeper("domain1.book.zookeeper:2181", 
				5000, //
				new GetData_API_ASync_Usage());
        connectedSemaphore.await();
        
        zk.create( path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL );
        
        zk.getData( path, true, new IDataCallback(), null );
        
        zk.setData( path, "123".getBytes(), -1 );
        
        Thread.sleep( Integer.MAX_VALUE );
    }
    public void process(WatchedEvent event) {
        if (KeeperState.SyncConnected == event.getState()) {
  	      if (EventType.None == event.getType() && null == event.getPath()) {
  	          connectedSemaphore.countDown();
  	      } else if (event.getType() == EventType.NodeDataChanged) {
  	          try {
  	        	zk.getData( event.getPath(), true, new IDataCallback(), null );
  	          } catch (Exception e) {}
  	      }
  	    }
       }
}
class IDataCallback implements AsyncCallback.DataCallback{
	public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println(rc + ", " + path + ", " + new String(data));
        System.out.println(stat.getCzxid()+","+
                  		   stat.getMzxid()+","+
		                   stat.getVersion());
    }
}