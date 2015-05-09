package com.cl.roadshow.designpattern.behavior;

import java.util.Enumeration;
import java.util.Observable;
import java.util.Vector;

/**
 * 观察者模式
 * 
 * 在分布式的编程框架中，在面向服务的编程模型中，即使我们根本不知道观察者模式，但是我们可能一直在使用着其思想。
 *
 */
public class ObserverDemo {
	public static void main(String[] args) {
		ObserverDemo observerDemo = new ObserverDemo();

		Subject subject = observerDemo.new ConcreteSubject();
		Observer observer = observerDemo.new ConcreteObserver();

		subject.attach(observer);
		subject.notifyObservers();

		System.out.println("\n观察者模式在JDK中的应用");

		// 创建被观察者对象
		Watched watched = observerDemo.new Watched();
		// 创建观察者对象，并将被观察者对象登记
		observerDemo.new Watcher(watched);

		// 给给观察者对象的状态赋值2次
		watched.changeData("I am Tom");
		watched.changeData("I am Jack");
	}

	interface Subject {
		// 调用这个方法登记一个新的观察者对象
		void attach(Observer observer);

		// 调用这个方法删除一个登记过的观察者对象
		void detach(Observer observer);

		// 调用这个方法通知所有登记过的观察者对象
		void notifyObservers();
	}

	class ConcreteSubject implements Subject {
		private Vector<Observer> observersVector = new Vector<Observer>();

		@Override
		public void attach(Observer observer) {
			observersVector.addElement(observer);
		}

		@Override
		public void detach(Observer observer) {
			observersVector.removeElement(observer);
		}

		@Override
		public void notifyObservers() {
			Enumeration<Observer> e = observers();
			while (e.hasMoreElements()) {
				e.nextElement().update();
			}
		}

		@SuppressWarnings("unchecked")
		public Enumeration<Observer> observers() {
			return ((Vector<Observer>) observersVector.clone()).elements();
		}
	}

	interface Observer {
		void update();
	}

	class ConcreteObserver implements Observer {
		@Override
		public void update() {
			System.out.println("I am notified");
		}
	}

	// 观察者模式在JDK中的应用
	class Watched extends Observable {
		private String data = "";

		public String retrieveData() {
			return data;
		}

		public void changeData(String data) {
			if (!this.data.equals(data)) {
				this.data = data;
				setChanged();
			}
			notifyObservers();
		}
	}

	class Watcher implements java.util.Observer {
		public Watcher(Watched w) {
			w.addObserver(this);
		}

		@Override
		public void update(Observable ob, Object arg) {
			System.out.println("Data has been changed to: '" + ((Watched) ob).retrieveData() + "'");
		}
	}
}
