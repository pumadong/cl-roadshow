package com.cl.roadshow.designpattern.behavior;

/**
 * 模板方法模式：方法层次上的代码重构（Code Refactor）利器
 * 
 * 可以将大方法打破，拆成一个模板方法模式；建立取值方法；建立常量方法；以多态性取代条件转移；实施委派
 *
 * 对一个继承的等级结构做重构时，一个应当遵从的原则便是将行为尽量移动到结构的高端，而将状态尽量移动到结构的低端
 */
public class TemplateMethodDemo {
	public static void main(String[] args) {
		TemplateMethodDemo templateMethodDemo = new TemplateMethodDemo();
		AbstractClass ac = templateMethodDemo.new ConcreteClass();
		ac.templateMethod();
	}

	abstract class AbstractClass {
		// 模板方法的声明和实现
		public void templateMethod() {
			// 调用基本方法（由子类实现）
			doOp1();
			// 调用基本方法（由子类实现）
			doOp2();
			// 调用基本方法（已经）实现
			doOp3();
		}

		protected abstract void doOp1();

		protected abstract void doOp2();

		protected final void doOp3() {
			System.out.println("doOp3();");
		}
	}

	class ConcreteClass extends AbstractClass {
		@Override
		public void doOp1() {
			System.out.println("doOp1();");
		}

		@Override
		public void doOp2() {
			System.out.println("doOp2();");
		}
	}
}
