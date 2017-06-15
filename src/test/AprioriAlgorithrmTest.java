package test;

import org.junit.Assert;
import org.junit.Test;

import model.Item;

public class AprioriAlgorithrmTest {

	@Test
	public void testCheckSubArrayContain0() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B" };
		Item i = new Item(null);
		Assert.assertTrue(i.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain1() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "B", "B" };
		Item i = new Item(null);
		Assert.assertFalse(i.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain2() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B", "C" };
		Item i = new Item(null);
		Assert.assertTrue(i.checkSubArrayContain(parent, child));
	}

	@Test
	public void testCheckSubArrayContain3() {
		String[] parent = { "A", "B", "C", "A", "D", "G", "B", "C", "E", "F" };
		String[] child = { "A", "D", "G", "B", "A" };
		Item i = new Item(null);
		Assert.assertFalse(i.checkSubArrayContain(parent, child));
	}

}
