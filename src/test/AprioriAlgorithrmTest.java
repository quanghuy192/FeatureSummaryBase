package test;

import org.junit.Assert;
import org.junit.Test;

import controller.AprioriAlgorithrm;

public class AprioriAlgorithrmTest {

	@Test
	public void testCheckSubArrayContain0() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B" };
		AprioriAlgorithrm al = new AprioriAlgorithrm();
		Assert.assertTrue(al.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain1() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "B", "B" };
		AprioriAlgorithrm al = new AprioriAlgorithrm();
		Assert.assertFalse(al.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain2() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B", "C" };
		AprioriAlgorithrm al = new AprioriAlgorithrm();
		Assert.assertTrue(al.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain3() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B", "A" };
		AprioriAlgorithrm al = new AprioriAlgorithrm();
		Assert.assertFalse(al.checkSubArrayContain(parent, child));
	}

}
