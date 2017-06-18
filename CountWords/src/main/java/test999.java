import java.util.List;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.*;



public class test999 {
/*
	public static class Customer {
		public int id;
		public String firstName;
		public String lastName;
      
       //Membership level of the customer
		public MembershipLevel membershipLevel;
      //indicates the parent Family member id 
		public int parentFamilyMember;
		

      //Customer object that needs to be sorted.
		public Customer(int id, String firstName, String lastName, MembershipLevel membershipLevel,
				int parentFamilyMember) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.membershipLevel = membershipLevel;
			this.parentFamilyMember = parentFamilyMember;
		}

  
		@Override
		public String toString() {
			return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", membershipLevel="
					+ membershipLevel + ", parentFamilyMember=" + parentFamilyMember + "]";
		}


		public enum MembershipLevel {
			PREMIUM, ENTERPRISE, COMMUNITY;
		}
	}
	

  
	List<Customer> getSortedCustomers(List<Customer> inputCustomers) {
   //   List<Customer> result=new ArrayList<>();
      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->e));
         Map<Integer,Customer> cmap1;
      
      Comparator<Customer> compByFirst= comparing(c->c.firstName);
        Comparator<Customer> compByLast= comparing(c->c.lastName);
        Comparator<Customer> compByName= compByFirst.thenComparing(compByLast);
         Comparator<Customer> compByLevel= comparingInt((Customer c)->c.membershipLevel.ordinal());
        Comparator<Customer> compBy= compByLevel.thenComparing(compByName);
  
      
      
    cmap1=  inputCustomers.stream().map(c->{ Customer tmp;  if((c.parentFamilyMember>0) && (cmap.get(c.parentFamilyMember).membershipLevel.ordinal()>c.membershipLevel.ordinal()))
      {
                                     tmp=  cmap.get(c.parentFamilyMember);
        tmp.membershipLevel=c.membershipLevel;
      }
                                      return c;})
        
        
                                   .collect(toList()).parallelStream()
                            .map( c->{if(c.parentFamilyMember>0) {c.membershipLevel =cmap.get(c.parentFamilyMember).membershipLevel ;}
                                     return c;} )
                            .collect(toMap(c->c.id,c->c));
    
    Comparator<Customer> com=comparing(e->cmap1.get(e.id) ,compBy);
   
    Collections.sort(inputCustomers ,com);
    
                           
                            .collect(groupingBy(  (Customer c)->{int i= c.parentFamilyMember>0?c.parentFamilyMember:c.id; return cmap.get(i);} ,
                                                ()->new TreeMap<Customer,TreeSet<Customer>>(compBy),
                                                toCollection(()->new TreeSet<Customer>(compByName))
                            
                            ))
                            .forEach((k,v)->{result.add(cmap1.get(k.id)) ;v.forEach(e->{if(e.id!=k.id) result.add(cmap1.get(e.id));}); });
        
         
		return inputCustomers;		
	}
	

	

	public static void main(String[] args) {
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
		
		input.forEach(e->System.out.println(e.id));
		 new SortTest().getSortedCustomers(input).forEach(e->System.out.println(e));
		
		
		
	}
	
	
*/
}

