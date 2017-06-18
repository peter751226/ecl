package lzh;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import lzh.SortTest.Customer;

public class TwoTest {
	static List<SortTest.Customer> input = new ArrayList<>();
	static List<SortTest.Customer> expected = new ArrayList<>();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 Customer parentPremium1 = new Customer(1, "john", "tester", Customer.MembershipLevel.PREMIUM, -1);
			Customer xchildPremium1 = new Customer(11, "xbabyjohn", "tester", Customer.MembershipLevel.COMMUNITY, 1);
			Customer achildPremium1 = new Customer(12, "ababyjohn", "tester", Customer.MembershipLevel.PREMIUM, 1);

			Customer parentEnterprise1 = new Customer(2, "Michael", "manager", Customer.MembershipLevel.COMMUNITY, -1);
			Customer xchildEnterprise1 = new Customer(21, "xbabyMichael", "manager", Customer.MembershipLevel.ENTERPRISE, 2);
			Customer achildEnterprise1 = new Customer(22, "ababyMichael", "manager", Customer.MembershipLevel.COMMUNITY, 2);
			
			Customer parentCommunity1 = new Customer(3, "Dave", "developer", Customer.MembershipLevel.COMMUNITY, -1);
			
	
			
			input.add(xchildEnterprise1);
			input.add(achildPremium1);
			input.add(parentCommunity1);
			input.add(achildEnterprise1);
			input.add(parentEnterprise1);
			
			input.add(parentPremium1);
			input.add(xchildPremium1);
			
		
			
			expected.add(parentPremium1);
			expected.add(achildPremium1);
			expected.add(xchildPremium1);
			
			expected.add(parentEnterprise1);
			expected.add(achildEnterprise1);			
			expected.add(xchildEnterprise1);
			expected.add(parentCommunity1);
	}

	@Test
	public void test() {
		Assert.assertEquals(expected,  new SortTest().getSortedCustomers_UpdatedLevel(input));
	}
	
	@Test
	public void test2() {
		Assert.assertEquals(expected,  new SortTest().getSortedCustomers_UpdatedLevel(input));
	}

}
