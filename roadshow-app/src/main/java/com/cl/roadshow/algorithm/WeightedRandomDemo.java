package com.cl.roadshow.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 简单的权重随机算法演示
 * 
 *
 */
public class WeightedRandomDemo {

	private static Random random = new Random();

	public static void main(String[] args) {

		WeightedRandomDemo demo = new WeightedRandomDemo();

		List<ConcreteWeitht> categorys = demo.initData();
		Integer weightSum = 0;
		for (ConcreteWeitht wc : categorys) {
			weightSum += wc.getWeight();
		}

		if (weightSum <= 0) {
			System.err.println("Error: weightSum=" + weightSum.toString());
			return;
		}
		int i = 0;
		while (i++ < 10) {
			demo.getWeight(categorys, weightSum);
		}
	}

	/**
	 * 根据权重随机获取对象
	 * 
	 * @param weights
	 * @param weightSum
	 * @return
	 */
	public AbstractWeight getWeight(List<? extends AbstractWeight> weights, Integer weightSum) {
		// n in [0, weightSum)
		Integer n = random.nextInt(weightSum);
		Integer m = 0;
		for (AbstractWeight aw : weights) {
			if ((m <= n) && (n < (m + aw.getWeight()))) {
				System.out.println("Current is " + aw.toString());
				return aw;
			}
			m += aw.getWeight();
		}
		return null;
	}

	public List<ConcreteWeitht> initData() {
		List<ConcreteWeitht> categorys = new ArrayList<ConcreteWeitht>();

		ConcreteWeitht wc1 = new ConcreteWeitht("A", 60);
		ConcreteWeitht wc2 = new ConcreteWeitht("B", 20);
		ConcreteWeitht wc3 = new ConcreteWeitht("C", 20);
		categorys.add(wc1);
		categorys.add(wc2);
		categorys.add(wc3);

		return categorys;
	}
}

/**
 * 所有权重相关的类的抽象基类
 * 
 *
 */
abstract class AbstractWeight {
	protected Integer weight;

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

}

class ConcreteWeitht extends AbstractWeight {
	private String name;

	public ConcreteWeitht(String name, Integer weight) {
		super();
		this.name = name;
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ConcreteWeitht [name=" + name + ", weight=" + weight + "]";
	}
}