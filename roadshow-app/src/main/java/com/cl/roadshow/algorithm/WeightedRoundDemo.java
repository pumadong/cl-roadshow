package com.cl.roadshow.algorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * WRR算法：权重轮询调度算法(WeightedRound-RobinScheduling)
 * 
 * http://en.wikipedia.org/wiki/Weighted_round_robin
 *
 */
public class WeightedRoundDemo {

	// 服务器集合
	private List<Server> serverList;

	public static void main(String[] args) {

		WeightedRoundDemo obj = new WeightedRoundDemo();
		obj.init();

		Map<String, Integer> countResult = new HashMap<String, Integer>();

		for (int i = 0; i < 100; i++) {
			Server s = obj.GetBestServer();
			String log = "ip:" + s.ip + ";weight:" + s.weight;
			if (countResult.containsKey(log)) {
				countResult.put(log, countResult.get(log) + 1);
			} else {
				countResult.put(log, 1);
			}
			System.out.println(log);

			// 动态添加服务器
			// if ((i == 20) || (i == 22)) {
			// obj.add(i);
			// }

			// 动态停止服务器
			// if (i == 30) {
			// obj.getServer(2).setDown(true);
			// obj.getServer(3).setDown(true);
			// }
		}

		for (Entry<String, Integer> map : countResult.entrySet()) {
			System.out.println("服务器 " + map.getKey() + " 请求次数： " + map.getValue());
		}
	}

	public Server GetBestServer() {
		Server currentServer = null;
		Server bestServer = null;
		int total = 0;
		for (int i = 0, len = serverList.size(); i < len; i++) {
			// 当前服务器对象
			currentServer = serverList.get(i);

			// 当前服务器已宕机，排除
			if (currentServer.down) {
				continue;
			}

			currentServer.currentWeight += currentServer.effectiveWeight;
			total += currentServer.effectiveWeight;

			if (currentServer.effectiveWeight < currentServer.weight) {
				currentServer.effectiveWeight++;
			}

			if ((bestServer == null) || (currentServer.currentWeight > bestServer.currentWeight)) {
				bestServer = currentServer;
			}

		}

		if (bestServer == null) {
			return null;
		}

		bestServer.currentWeight -= total;
		bestServer.checkedDate = new Date();
		return bestServer;
	}

	class Server {
		public String ip;
		public int weight;
		public int effectiveWeight;
		public int currentWeight;
		public boolean down = false;
		public Date checkedDate;

		public Server(String ip, int weight) {
			super();
			this.ip = ip;
			this.weight = weight;
			this.effectiveWeight = this.weight;
			this.currentWeight = 0;
			if (this.weight < 0) {
				this.down = true;
			} else {
				this.down = false;
			}
		}

		public String getIp() {
			return ip;
		}

		public void setIp(String ip) {
			this.ip = ip;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public int getEffectiveWeight() {
			return effectiveWeight;
		}

		public void setEffectiveWeight(int effectiveWeight) {
			this.effectiveWeight = effectiveWeight;
		}

		public int getCurrentWeight() {
			return currentWeight;
		}

		public void setCurrentWeight(int currentWeight) {
			this.currentWeight = currentWeight;
		}

		public boolean isDown() {
			return down;
		}

		public void setDown(boolean down) {
			this.down = down;
		}

		public Date getCheckedDate() {
			return checkedDate;
		}

		public void setCheckedDate(Date checkedDate) {
			this.checkedDate = checkedDate;
		}

	}

	public void init() {
		Server s1 = new Server("192.168.0.100", 1);
		Server s2 = new Server("192.168.0.101", 2);
		Server s3 = new Server("192.168.0.102", 3);
		Server s4 = new Server("192.168.0.103", 4);
		Server s5 = new Server("192.168.0.104", 5);
		Server s6 = new Server("192.168.0.105", 6);
		Server s7 = new Server("192.168.0.106", 7);
		Server s8 = new Server("192.168.0.107", 8);
		Server s9 = new Server("192.168.0.108", 9);
		serverList = new ArrayList<Server>();
		serverList.add(s1);
		serverList.add(s2);
		serverList.add(s3);
		serverList.add(s4);
		serverList.add(s5);
		serverList.add(s6);
		serverList.add(s7);
		serverList.add(s8);
		serverList.add(s9);
	}

	public void add(int i) {
		Server s = new Server("192.168.0.1" + i, i - 15);
		serverList.add(s);
	}

	public Server getServer(int i) {
		if (i < serverList.size()) {
			return serverList.get(i);
		}
		return null;
	}
}