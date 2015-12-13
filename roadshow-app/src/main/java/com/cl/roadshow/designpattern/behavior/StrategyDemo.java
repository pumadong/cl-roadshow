package com.cl.roadshow.designpattern.behavior;

import java.util.Arrays;

/* 
 排序算法有很多，所以在特定情景中使用哪一种算法很重要。为了选择合适的算法，可以按照建议的顺序考虑以下标准：  
 （1）执行时间  
 （2）存储空间  
 （3）编程工作  
 对于数据量较小的情形，（1）（2）差别不大，主要考虑（3）；而对于数据量大的，（1）为首要。

 比较以下4种常见的排序算法：
 冒泡排序，比较中庸，性能耗费中既有比较，也有有效的相邻交换这中交换操作；
 选择排序，性能耗费基本在比较，交换操作少；
 插入排序，性能耗费既有判断，也有很多无效的顺序交换这种交换操作，只适用于少量数据的，并且已经基本排好序的场景；
 快速排序，递归，二叉分区

 稳定排序，是指对于相同大小的元素，在排序过程中相对位置不会发生变化；不稳定排序是指会发生变化。
 
 JDK 7开始，Arrays.sort，根据不同的场景选择不同的排序算法，有归并、快排、插入：http://www.tuicool.com/articles/BfY7Nz
 */
public class StrategyDemo {
	private Strategy s;

	public void setStrategy(Strategy s) {
		this.s = s;
	}

	public void sort() {
		s.sort();
	}

	public static void main(String[] args) {
		int[] a = new int[] { 1, 3, 77, 99, 89, 3, 99, 300, 6, 7 };
		StrategyDemo strategyDemo = new StrategyDemo();

		Strategy s1 = strategyDemo.new BubbleSort(a);
		strategyDemo.setStrategy(s1);
		strategyDemo.sort();
		System.out.println("冒泡排序：" + Arrays.toString(a));

		a = new int[] { 1, 3, 77, 99, 89, 3, 99, 300, 6, 7 };
		Strategy s2 = strategyDemo.new SelectSort(a);
		strategyDemo.setStrategy(s2);
		strategyDemo.sort();
		System.out.println("选择排序：" + Arrays.toString(a));

		a = new int[] { 1, 3, 77, 99, 89, 3, 99, 300, 6, 7 };
		Strategy s3 = strategyDemo.new InsertSort(a);
		strategyDemo.setStrategy(s3);
		strategyDemo.sort();
		System.out.println("插入排序：" + Arrays.toString(a));

		a = new int[] { 1, 3, 77, 99, 89, 3, 99, 300, 6, 7 };
		Strategy s4 = strategyDemo.new QuickSort(a);
		strategyDemo.setStrategy(s4);
		strategyDemo.sort();
		System.out.println("快速排序：" + Arrays.toString(a));

		a = new int[] { 1, 3, 77, 99, 89, 3, 99, 300, 6, 7 };
		Strategy s5 = strategyDemo.new MergeSort(a);
		strategyDemo.setStrategy(s5);
		strategyDemo.sort();
		System.out.println("归并排序：" + Arrays.toString(a));
	}

	// 演示一个策略模式：排序策略
	abstract class Strategy {
		protected int[] a;

		public Strategy(int[] a) {
			this.a = a;
		}

		protected void change(int i, int j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}

		public abstract void sort();

	}

	/**
	 * 冒泡排序（稳定排序）：时间复杂度：O(n^2)，原理是：共n-1轮排序处理，第j轮进行n-j次比较和至多n-j次交换
	 *
	 */
	class BubbleSort extends Strategy {
		public BubbleSort(int[] a) {
			super(a);
		}

		@Override
		public void sort() {
			int n = a.length;
			for (int i = 0; i < (n - 1); i++) {
				for (int j = 0; j < (n - i - 1); j++) {
					if (a[j + 1] < a[j]) {
						change(j + 1, j); // 比较交换相邻元素
					}
				}
			}
		}
	}

	/**
	 * 选择排序（不稳定排序）：时间复杂度：O(n^2)，原理是：共n-1轮排序处理，每轮找出最小的放在相应位置
	 *
	 */
	class SelectSort extends Strategy {

		public SelectSort(int[] a) {
			super(a);
		}

		@Override
		public void sort() {
			int n = a.length;
			for (int i = 0; i < (n - 1); i++) {
				int k = i;
				for (int j = i + 1; j < n; j++) {
					if (a[j] < a[k]) {
						k = j;
					}
				}
				if (k != i) {
					change(k, i);
				}
			}
		}
	}

	/**
	 * 插入排序（稳定排序）：时间复杂度：O(n^2)，原理是：对数组中的第i个元素，认为它前面的i-1个已经排序好，然后将它插入到前面的i-1个元素中。
	 *
	 */
	class InsertSort extends Strategy {

		public InsertSort(int[] a) {
			super(a);
		}

		@Override
		public void sort() {
			int n = a.length;
			for (int i = 1; i < n; i++) // 循环从第二个数组元素开始，因为第一个作为最初已排序部分
			{
				int temp = a[i]; // temp标记为未排序第一个元素
				int j = i - 1;
				while ((j >= 0) && (a[j] > temp)) // 将temp与已排序元素从小到大比较，寻找temp应插入的位置
				{
					a[j + 1] = a[j];
					j--;
				}
				a[j + 1] = temp;
			}
		}
	}

	/**
	 * 快速排序（不稳定排序）：时间复杂度，O(nlogn)，最坏情况为O(n^2)，是对冒泡排序的改进，适用于排序大列表
	 * 基本思想：通过一次比较将要排序的数据分割成独立的两部分，其中一部分的所有数据都比另一部分的小，然后对这两部分继续快速排序，达到整个数据变成有序序列
	 *
	 */
	class QuickSort extends Strategy {

		public QuickSort(int[] a) {
			super(a);
		}

		@Override
		public void sort() {
			sort(0, a.length - 1);
		}

		private int partition(int low, int high) {
			int key = a[low]; // 标杆数据，小于标杆数据的放到一侧，大于的放到另一侧
			while (low < high) {
				// 小于标杆数据的放到左侧
				while ((low < high) && (a[high] >= key)) {
					high--;
				}
				a[low] = a[high];

				// 大于标杆数据的放到右侧
				while ((low < high) && (a[low] <= key)) {
					low++;
				}
				a[high] = a[low];
			}

			a[low] = key; // 标杆数据放到中间
			return low; // 返回经过计算后标杆数据所在位置
		}

		private void sort(int low, int high) {
			if (low < high) // 递归的死循环，会导致StackOverFlow
			{
				int r = partition(low, high);
				sort(low, r - 1);
				sort(r + 1, high);
			}
		}
	}

	/**
	 * 归并排序是建立在归并操作上的一种有效的排序算法,该算法是采用分治法（Divide and Conquer）的一个非常典型的应用
	 * 时间复杂度 Q(n log(n))，最差情况：在已经反向排好序的输入时(n^2)，JDK中使用优化过的归并TimSort解决这一问题
	 */
	class MergeSort extends Strategy {
		public MergeSort(int[] a) {
			super(a);
		}

		@Override
		public void sort() {
			merge(a, 0, a.length - 1, new int[a.length]);
		}

		private void merge(int[] a, int first, int last, int[] temp) {
			if (first >= last) {
				return;
			}
			int mid = (first + last) / 2;
			merge(a, first, mid, temp); // 左边排序
			merge(a, mid + 1, last, temp); // 右边排序
			mergeArray(a, first, mid, last, temp);
		}

		// 将两个有序数组a[first..mid]和a[mid+1..last]合并
		private void mergeArray(int[] a, int first, int mid, int last, int[] temp) {
			int i = first, j = mid + 1;
			int k = 0;

			while ((i <= mid) && (j <= last)) {
				if (a[i] <= a[j]) {
					temp[k++] = a[i++];
				} else {
					temp[k++] = a[j++];
				}
			}

			while (i <= mid) {
				temp[k++] = a[i++];
			}

			while (j <= last) {
				temp[k++] = a[j++];
			}

			for (i = 0; i < k; i++) {
				a[first + i] = temp[i];
			}
		}
	}
}
