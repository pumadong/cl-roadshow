package com.cl.roadshow.guava;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * [Google Guava] 排序: Guava强大的”流畅风格比较器”
 * http://ifeve.com/google-guava-ordering/
 * @author dongyongjin
 *
 */
public class GuavaOrderingDemo {
	
	public static void main(String[] args) {
		List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        
        System.out.println("list:"+ list);

        Ordering<String> naturalOrdering = Ordering.natural();        
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();
        
        System.out.println("naturalOrdering:"+ naturalOrdering.sortedCopy(list));     
        System.out.println("usingToStringOrdering:"+ usingToStringOrdering.sortedCopy(list));        
        System.out.println("arbitraryOrdering:"+ arbitraryOrdering.sortedCopy(list));
        
        Ordering<String> byLengthOrdering = new Ordering<String>() {
        	public int compare(String left, String right) {
        		return Ints.compare(left.length(), right.length());
        	}
        };

        System.out.println("byLengthOrdering:"+ byLengthOrdering.sortedCopy(list));
        
        Foo f1 = new Foo();
        f1.sortedBy = "b";
        Foo f2 = new Foo();
        f2.sortedBy = "a";
        Foo f3 = new Foo();
        List<Foo> foolist = Lists.newArrayList();
        foolist.add(f1);
        foolist.add(f2);
        foolist.add(f3);
        Ordering<Foo> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<Foo, String>() {
    	  public String apply(Foo foo) {
    	    return foo.sortedBy;
    	  }
    	});
        System.out.println("ordering:"+ ordering.sortedCopy(foolist));
	}
	
	public static class Foo {
	    String sortedBy;
	    int notSortedBy;
		@Override
		public String toString() {
			return "Foo [sortedBy=" + sortedBy + ", notSortedBy=" + notSortedBy
					+ "]";
		}
	}
}
