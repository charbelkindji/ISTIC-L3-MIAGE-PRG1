package fr.istic.prg1.list.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.junit.Test;

import fr.istic.prg1.list.MySet;
import fr.istic.prg1.list.SubSet;
import fr.istic.prg1.list_util.Iterator;
import fr.istic.prg1.list_util.SmallSet;

import static org.junit.Assert.assertTrue;

/**
 * @author Mickaël Foursov <foursov@univ-rennes1.fr>
 * @version 2.0
 * @since 2015-06-15
 * 
 *        Classe contenant les tests unitaires pour la classe MySet.
 */

public class TestMySet {

	/**
	 * @param l1
	 *            premier ensemble
	 * @param l2
	 *            deuxième ensemble
	 * @return true si les ensembles l1 et l2 sont égaux, false sinon
	 */
	public static boolean compareMySets(MySet l1, MySet l2) {
		Iterator<SubSet> it1 = l1.iterator();
		Iterator<SubSet> it2 = l2.iterator();
		boolean bool = true;
		while (!it1.isOnFlag() && bool) {
			SubSet s1 = it1.getValue();
			SubSet s2 = it2.getValue();
			if (!compareSubSets(s1, s2)) {
				bool = false;
			}
			it1.goForward();
			it2.goForward();
		}
		return bool && it1.isOnFlag() && it2.isOnFlag();
	}

	public static boolean compareSubSets(SubSet s1, SubSet s2) {
		System.out.println("s1.rank = " + s1.rank + " s2.rank " + s2.rank);
		return s1.rank == s2.rank && compareSmallSets(s1.set, s2.set);
	}

	public static boolean compareSmallSets(SmallSet s1, SmallSet s2) {
		System.out.println("s1.size() = " + s1.size() + " s1.toString() = " + s1.toString());
		System.out.println("s2.size() = " + s2.size() + " s2.toString() = " + s2.toString());
		return (s1.size() == 0 || s2.size() == 0) ? false : s1.toString()
				.equals(s2.toString());
	}

	/**
	 * @param mySet
	 *            ensemble à tester
	 * @return true si mySet est bien un ensemble creux
	 */
	public static boolean testSparsity(MySet mySet) {
		Iterator<SubSet> it = mySet.iterator();
		while (!it.isOnFlag() && it.getValue().set.size() != 0) {
			it.goForward();
		}
		return it.isOnFlag();
	}

	public static MySet readFileToMySet(String fileName) {
		MySet set = new MySet();
		try {
			InputStream is = null;
			is = new FileInputStream(fileName);
			set.add(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Test
	public void testSetCreation() {
		MySet mySet1 = readFileToMySet("test-desordre.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		assertTrue("set creation in disorder", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testContainment1() {
		MySet mySet = readFileToMySet("f0.ens");
		boolean bool1 = mySet.contains(128);
		boolean bool2 = mySet.contains(129);
		boolean bool3 = mySet.contains(32767);
		boolean bool4 = mySet.contains(22222);
		assertTrue("appartenance 1", bool1 && !bool2 && bool3 && !bool4);
	}

	@Test
	public void testContainment2() {
		MySet mySet = readFileToMySet("f0.ens");
		boolean bool = mySet.contains(32511);
		assertTrue("appartenance 2", !bool);
	}

	@Test
	public void testSetAddition() throws FileNotFoundException {
		MySet mySet1 = readFileToMySet("f0.ens");
		InputStream is = new FileInputStream("f1.ens");
		MySet mySet2 = readFileToMySet("test-u01.ens");
		mySet1.add(is);
		assertTrue("set addition f0 f1", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		mySet1.removeNumber(64);
		mySet1.removeNumber(32767);
		MySet mySet2 = readFileToMySet("test-d05.ens");
		assertTrue("deletion sparsity 1", testSparsity(mySet1));
		assertTrue("deletion 1", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval2() {
		MySet mySet1 = new MySet();
		mySet1.addNumber(0);
		mySet1.addNumber(512);
		MySet mySet2 = new MySet();
		mySet2.addNumber(0);
		mySet2.addNumber(512);

		mySet1.removeNumber(256);
		assertTrue("deletion sparsity 2", testSparsity(mySet1));
		assertTrue("deletion 2", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testRemoval3() {
		MySet mySet1 = new MySet();
		mySet1.addNumber(64);
		mySet1.removeNumber(64);
		assertTrue("deletion sparsity 3", testSparsity(mySet1));
		assertTrue("deletion 3", mySet1.isEmpty());
	}

	@Test
	public void testRemoval4() {
		MySet mySet1 = new MySet();
		mySet1.addNumber(64);
		mySet1.addNumber(3333);
		mySet1.removeNumber(64);
		mySet1.removeNumber(3333);
		assertTrue("deletion sparsity 4", testSparsity(mySet1));
		assertTrue("deletion 4", mySet1.isEmpty());
	}

	@Test
	public void testRemoval5() throws FileNotFoundException {
		MySet mySet1 = readFileToMySet("f0.ens");
		mySet1.remove(new FileInputStream(new File("f1.ens")));
		MySet mySet2 = readFileToMySet("test-d01.ens");
		assertTrue("deletion sparsity 5", testSparsity(mySet1));
		assertTrue("deletion 5", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testSize1() {
		MySet mySet = readFileToMySet("f0.ens");
		int size = mySet.size();
		assertTrue("size", size == 14);
	}

	@Test
	public void testSize2() {
		MySet mySet = new MySet();
		mySet.iterator().getValue().set.add(22);
		;
		int size = mySet.size();
		assertTrue("size", size == 0);
	}

	@Test
	public void testDifference1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		MySet mySet3 = readFileToMySet("test-d03.ens");
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f3", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference2() {
		MySet mySet1 = readFileToMySet("f3.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("test-d30.ens");
		mySet1.difference(mySet2);
		assertTrue("difference f3 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference3() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f1.ens");
		MySet mySet3 = readFileToMySet("test-d01.ens");
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f1", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(100);

		mySet3.addNumber(300);

		mySet1.difference(mySet2);
		assertTrue("difference 100+300 and 100", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testDifference5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);

		mySet2.addNumber(301);
		mySet2.addNumber(100);

		mySet1.difference(mySet2);
		assertTrue("difference 100 and 100+301", mySet1.isEmpty());
	}

	@Test
	public void testDifference6() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet1.difference(mySet2);
		assertTrue("difference f0 and f0 : version 1", mySet1.isEmpty());
	}

	@Test
	public void testDifference7() {
		MySet mySet1 = readFileToMySet("f0.ens");
		mySet1.difference(mySet1);
		assertTrue("difference f0 and f0 : version 2", mySet1.isEmpty());
	}

	@Test
	public void testIntersection1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		MySet mySet3 = readFileToMySet("test-i03.ens");
		mySet1.intersection(mySet2);
		assertTrue("intersection f0 and f3", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection2() {
		MySet mySet1 = readFileToMySet("f3.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("test-i03.ens");
		mySet1.intersection(mySet2);
		assertTrue("intersection f3 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection7Perso() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("f0.ens");
		mySet1.intersection(mySet2);
		assertTrue("intersection f0 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(100);
		mySet2.addNumber(301);

		mySet3.addNumber(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+300 and 100+301",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+300 and 100",
				compareMySets(mySet1, mySet2));
	}

	@Test
	public void testIntersection5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);

		mySet2.addNumber(100);
		mySet2.addNumber(301);

		mySet3.addNumber(100);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100 and 100+301",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testIntersection6() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(999);

		mySet2.addNumber(200);
		mySet2.addNumber(301);

		mySet1.intersection(mySet2);
		assertTrue("intersection 100+999 and 200+301", mySet1.isEmpty());
	}

	@Test
	public void testUnion1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		MySet mySet3 = readFileToMySet("test-u03.ens");
		mySet1.union(mySet2);
		assertTrue("union f0 and f3", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testUnion2() {
		MySet mySet1 = readFileToMySet("f3.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("test-u03.ens");
		mySet1.union(mySet2);
		assertTrue("union f3 and f0", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testUnion3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(100);

		mySet3.addNumber(100);
		mySet3.addNumber(300);

		mySet1.union(mySet2);
		assertTrue("union 100+300 and 100", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testUnion4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);

		mySet2.addNumber(100);
		mySet2.addNumber(301);

		mySet1.union(mySet2);
		assertTrue("union 100 and 100+301", compareMySets(mySet1, mySet2));
	}

	@Test
	public void testUnion5() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		MySet mySet3 = readFileToMySet("test-u03.ens");
		mySet1.union(mySet2);
		mySet2.addNumber(8201);
		assertTrue("union f0 and f3 (bis)", compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference1() {
		MySet mySet1 = readFileToMySet("f1.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("test-s01.ens");
		mySet1.symmetricDifference(mySet2);
		assertTrue("symmetric difference f1 and f0",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference2() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f1.ens");
		MySet mySet3 = readFileToMySet("test-s01.ens");
		mySet1.symmetricDifference(mySet2);
		assertTrue("symmetric difference f0 and f1",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(100);

		mySet3.addNumber(300);

		mySet1.symmetricDifference(mySet2);
		assertTrue("symmetric difference 100+300 and 100",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();

		mySet1.addNumber(100);

		mySet2.addNumber(100);
		mySet2.addNumber(301);

		mySet3.addNumber(301);

		mySet1.symmetricDifference(mySet2);
		assertTrue("symmetric difference 100 and 100+301",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference5() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet1.symmetricDifference(mySet2);
		assertTrue("symmetric difference f0 and f0 :version 1",
				mySet1.isEmpty());
	}

	@Test
	public void testSymmetricDifference6() {
		MySet mySet1 = readFileToMySet("f0.ens");
		mySet1.symmetricDifference(mySet1);
		assertTrue("symmetric difference f0 and f0 : version 2",
				mySet1.isEmpty());
	}

	@Test
	public void testSymmetricDifference7() {
		MySet mySet1 = readFileToMySet("f1.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		MySet mySet3 = readFileToMySet("test-s01.ens");
		mySet1.symmetricDifference(mySet2);
		mySet2.addNumber(5001);
		/*
		System.out.println();
		System.out.println("-------------TEST mySet1------------------------------------");
		System.out.println(mySet1.toString());
		System.out.println("-------------------------------------------------");
		System.out.println();
		
		System.out.println();
		System.out.println("-------------TEST mySet2------------------------------------");
		System.out.println(mySet2.toString());
		System.out.println("-------------------------------------------------");
		System.out.println();
		*/
		assertTrue("symmetric difference f1 and f0 (bis)",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testSymmetricDifference8() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		MySet mySet3 = new MySet();
		mySet1.addNumber(100);
		mySet1.addNumber(300);

		mySet2.addNumber(150);
		mySet2.addNumber(800);

		mySet3.addNumber(100);
		mySet3.addNumber(150);
		mySet3.addNumber(300);
		mySet3.addNumber(800);

		mySet1.symmetricDifference(mySet2);

		assertTrue("symmetric difference 100+300 and 150+800",
				compareMySets(mySet1, mySet3));
	}

	@Test
	public void testEquality1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		assertTrue("equality f0 == f0", mySet1.equals(mySet2));
	}

	@Test
	public void testEquality2() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet2.addNumber(8888);
		assertTrue("equality f0 == f0 + 8888", !mySet1.equals(mySet2));
	}

	@Test
	public void testEquality3() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet2.addNumber(5001);
		assertTrue("equality f0 == f0 + 5001", !mySet1.equals(mySet2));
	}

	@Test
	public void testEquality4() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet2.addNumber(8888);
		assertTrue("equality f0 + 8888 == f0", !mySet2.equals(mySet1));
	}

	@Test
	public void testEquality5() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f0.ens");
		mySet2.addNumber(5001);
		assertTrue("equality f0 + 5001 == f0", !mySet2.equals(mySet1));
	}

	@Test
	public void testEquality6() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		mySet1.addNumber(100);

		mySet2.addNumber(100);
		mySet2.addNumber(300);

		assertTrue("equality 100+300 == 100", !mySet2.equals(mySet1));
	}

	@Test
	public void testEquality7() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);

		mySet2.addNumber(100);
		mySet2.addNumber(300);

		assertTrue("equality 100 == 100+300", !mySet1.equals(mySet2));
	}

	@Test
	public void testEquality8() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(0);
		mySet1.addNumber(1000);

		mySet2.addNumber(256);
		mySet2.addNumber(1000);

		assertTrue("equality 0+1000 == 256+1000", !mySet1.equals(mySet2));
	}

	@Test
	public void testInclusion1() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		mySet1.union(mySet2);
		assertTrue("inclusion f3 in f0 u f3", mySet2.isIncludedIn(mySet1));
	}

	@Test
	public void testInclusion2() {
		MySet mySet1 = readFileToMySet("f0.ens");
		MySet mySet2 = readFileToMySet("f3.ens");
		assertTrue("inclusion f3 in f0", !mySet2.isIncludedIn(mySet1));
	}

	@Test
	public void testInclusion3() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(101);
		mySet1.addNumber(300);

		mySet2.addNumber(100);
		mySet2.addNumber(300);

		assertTrue("inclusion 3", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion4() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(101);
		mySet1.addNumber(300);

		mySet2.addNumber(300);

		assertTrue("inclusion 4", !mySet1.isIncludedIn(mySet2));
	}

	@Test
	public void testInclusion5() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(100);
		mySet1.addNumber(200);
		mySet1.addNumber(300);

		mySet2.addNumber(100);
		mySet2.addNumber(200);

		assertTrue("inclusion 5", !mySet1.isIncludedIn(mySet2));
	}


	@Test
	public void testInclusion6() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();

		mySet1.addNumber(10);

		mySet2.addNumber(1034);
		mySet2.addNumber(5555);

		assertTrue("inclusion 6", !mySet1.isIncludedIn(mySet2));
	}
	
	@Test
	public void testAddTail() {
		MySet mySet1 = new MySet();
		MySet mySet2 = new MySet();
		int bigValue = 32000;

		mySet1.addNumber(100);
		mySet1.addNumber(10000);
		mySet1.addNumber(bigValue);

		mySet2.addNumber(100);
		mySet2.addNumber(10000);

		SmallSet smallSet = new SmallSet();
		smallSet.add(bigValue % 256);
		SubSet subset = new SubSet(bigValue / 256, smallSet);
		mySet2.addTail(subset);
		assertTrue("addTail", compareMySets(mySet1, mySet2));
	}

}