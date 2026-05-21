package vn.proX.todoapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DiscountCalculatorTest {

	@Test
	public void testNoDiscount() {
		DiscountCalculator calculator = new DiscountCalculator();

		// Test case 1: totalAmount < 100
		double discount1 = calculator.calculateDiscount(50);
		assertEquals(0, discount1); // Expected: 0

	}

	@Test
	public void test10Discount() {
		DiscountCalculator calculator = new DiscountCalculator();

		// Test case 2: 100 <= totalAmount < 500
		double discount2 = calculator.calculateDiscount(200);
		assertEquals(20, discount2); // Expected: 20

	}

	@Test
	public void test20Discount() {
		DiscountCalculator calculator = new DiscountCalculator();

		// Test case 3: totalAmount >= 500
		double discount3 = calculator.calculateDiscount(600);
		assertEquals(120, discount3); // Expected: 120
	}
}
