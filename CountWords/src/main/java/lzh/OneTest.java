package lzh;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import lzh.SortTest.Customer;

public class OneTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test() {
		 Customer parentPremium1 = new Customer(1, "john", "tester", Customer.MembershipLevel.PREMIUM, -1);
			Customer xchildPremium1 = new Customer(11, "xbabyjohn", "tester", Customer.MembershipLevel.COMMUNITY, 1);
			Customer achildPremium1 = new Customer(12, "ababyjohn", "tester", Customer.MembershipLevel.PREMIUM, 1);

			Customer parentEnterprise1 = new Customer(2, "Michael", "manager", Customer.MembershipLevel.COMMUNITY, -1);
			Customer xchildEnterprise1 = new Customer(21, "xbabyMichael", "manager", Customer.MembershipLevel.ENTERPRISE, 2);
			Customer achildEnterprise1 = new Customer(22, "ababyMichael", "manager", Customer.MembershipLevel.COMMUNITY, 2);
			
			Customer parentCommunity1 = new Customer(3, "Dave", "developer", Customer.MembershipLevel.COMMUNITY, -1);
			
	List<SortTest.Customer> input = new ArrayList<>();
			
			input.add(xchildEnterprise1);
			input.add(achildPremium1);
			input.add(parentCommunity1);
			input.add(achildEnterprise1);
			input.add(parentEnterprise1);
			
			input.add(parentPremium1);
			input.add(xchildPremium1);
			
			List<SortTest.Customer> expected = new ArrayList<>();
			
			expected.add(parentPremium1);
			expected.add(achildPremium1);
			expected.add(xchildPremium1);
			
			expected.add(parentEnterprise1);
			expected.add(achildEnterprise1);			
			expected.add(xchildEnterprise1);
			expected.add(parentCommunity1);
		
         // in this case assertEquals only care about the reference of the customer object , do not care about the field of the reference  
			
			Assert.assertEquals(expected,  new SortTest().getSortedCustomers(input));
			Assert.assertEquals(expected,  new SortTest().getSortedCustomers_2(input));
			Assert.assertEquals(expected,  new SortTest().getSortedCustomers_UpdatedLevel(input));
			Assert.assertEquals(expected,  new SortTest().getSortedCustomers_simple(input));
			
		
	}

}
