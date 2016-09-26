package com.cl.roadshow.zookeeper.chapter05;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

// ZooKeeper API 获取子节点列表，使用同步(sync)接口。
public class ZooKeeper_GetChildren_API_Sync_Usage implements Watcher {

    private static CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static ZooKeeper zk = null;
    
    public static void main(String[] args) throws Exception{

    	String path = "/zk-book";
        zk = new ZooKeeper("domain1.book.zookeeper:2181", 
				5000, //
				new ZooKeeper_GetChildren_API_Sync_Usage());
        connectedSemaphore.await();
        zk.create(path, "".getBytes(), 
        		  Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create(path+"/c1", "".getBytes(), 
        		  Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        List<String> childrenList = zk.getChildren(path, true);
        System.out.println(childrenList);
        
        zk.create(path+"/c2", "".getBytes(), 
        		  Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        
        Thread.sleep( Integer.MAX_VALUE );
    }
    public void process(WatchedEvent event) {
      if (KeeperState.SyncConnected == event.getState()) {
        if (EventType.None == event.getType() && null == event.getPath()) {
            connectedSemaphore.countDown();
        } else if (event.getType() == EventType.NodeChildrenChanged) {
            try {
                System.out.println("ReGet Child:"+zk.getChildren(event.getPath(),true));
            } catch (Exception e) {}
        }
      }
    }
}
