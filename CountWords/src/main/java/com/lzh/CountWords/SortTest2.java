package com.lzh.CountWords;
import java.util.List;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.*;


public class SortTest2 {

	public static class Customer {
		public int id;
		public String firstName;
		public String lastName;
      
       //Membership level of the customer
		public MembershipLevel membershipLevel;
      //indicates the parent Family member id 
		public int parentFamilyMember;
		

      @Override
		public String toString() {
			return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", membershipLevel="
					+ membershipLevel + ", parentFamilyMember=" + parentFamilyMember + "]";
		}

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

//        public String toString(){
//			return firstName + " " + lastName;
//		}

		
		
		public enum MembershipLevel {
			PREMIUM, ENTERPRISE, COMMUNITY;
		}
	}
	
/**
 * 
 * Implement this method to Sort a collection of Customers as per the below specifications below.
  - Each customer object has First Name, Last Name, Membership level and optional parentFamilyMember. 
  -  If a member has a parent, the parent and all other siblings  are treated at highest membership level.
  -  Sort the customers in the order of highest membership level in the hierarchy followed by Parent's first name, last name.
  - Within a group Parent should be the first element followed by children sorted by their first name, last name.

 Assumptions:
  - All names are unique.
  - Each group will be having one level of hierarchy (No grand children)

  Example :

  INPUT:
   Parent-Q(COMMUNITY) -> Child-B(PREMIUM), Child-A(ENTERPRISE)
   Parent-R(ENTERPRISE) -> Child-C(COMMUNITY), Child-D(ENTERPRISE)
   Parent-S(PREMIUM)

  OUTPUT:
    Parent-Q , Child-A,Child-B ,Parent-S, Parent-R,Child-C, Child-D
 *
 * @param inputCustomers
 * @return sortedCustmers
 */
  
	List<Customer> getSortedCustomers_2(List<Customer> inputCustomers) {
      List<Customer> sortedCustmers=inputCustomers;
      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->{return new Customer(e.id,e.firstName,e.lastName, e.membershipLevel,e.parentFamilyMember);}));
         Map<Integer,Customer> cmap_original= inputCustomers.stream().collect(toMap(e->e.id,e->e));
         sortedCustmers.clear();
         Comparator<Customer> compByName= comparing((Customer c)->c.firstName).thenComparing(comparing(c->c.lastName));
         Comparator<Customer> compByLevel= comparingInt((Customer c)->c.membershipLevel.ordinal());
        Comparator<Customer> compBy= compByLevel.thenComparing(compByName);      
      cmap.values().stream().map(c->{ Customer tmp;  if((c.parentFamilyMember>0) && ((tmp=cmap.get(c.parentFamilyMember)).membershipLevel.ordinal()>c.membershipLevel.ordinal()))    	     
          tmp.membershipLevel=c.membershipLevel;  return c;}) .collect(toList()).stream()
                            .collect(groupingBy(  (Customer c)->{int i= c.parentFamilyMember>0?c.parentFamilyMember:c.id; return cmap.get(i);} ,
                                                ()->new TreeMap<Customer,TreeSet<Customer>>(compBy),
                                                toCollection(()->new TreeSet<Customer>(compByName))       ))                     
      .forEach((k,v)->{sortedCustmers.add(cmap_original.get(k.id)) ;v.forEach(e->{if(e.id!=k.id) sortedCustmers.add(cmap_original.get(e.id));}); });         
		return sortedCustmers;		
	}
	
	List<Customer> getSortedCustomers_UpdatedLevel(List<Customer> inputCustomers) {
		  List<Customer> sortedCustmers=inputCustomers;	    
	         Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->e));  sortedCustmers.clear();
	         Comparator<Customer> compByName= comparing((Customer c)->c.firstName).thenComparing(comparing(c->c.lastName));
	         Comparator<Customer> compByLevel= comparingInt((Customer c)->c.membershipLevel.ordinal());
	        Comparator<Customer> compBy= compByLevel.thenComparing(compByName);	      
	      cmap.values().stream().map(c->{ Customer tmp;  if((c.parentFamilyMember>0) && ((tmp=cmap.get(c.parentFamilyMember)).membershipLevel.ordinal()>c.membershipLevel.ordinal()))    	     
	          tmp.membershipLevel=c.membershipLevel;    return c;}) 	        
	               .collect(toList()).stream() .map( c->{if(c.parentFamilyMember>0) c.membershipLevel =cmap.get(c.parentFamilyMember).membershipLevel ;  return c;} )	                 
	                            .collect(toList()).stream().peek(e->System.out.println("----"+e))
	                            .collect(groupingBy(  (Customer c)->{int i= c.parentFamilyMember>0?c.parentFamilyMember:c.id; return cmap.get(i);} ,
	                                                ()->new TreeMap<Customer,TreeSet<Customer>>(compBy),
	                                                toCollection(()->new TreeSet<Customer>(compByName))                   
	                            ))	                  
	      .forEach((k,v)->{sortedCustmers.add(cmap.get(k.id)) ;v.forEach(e->{if(e.id!=k.id) sortedCustmers.add(cmap.get(e.id));}); });	         
			return sortedCustmers;		
		}
	

	public static void main(String[] args) {
	 Customer parentPremium1 = new Customer(1, "john", "tester", Customer.MembershipLevel.PREMIUM, -1);
		Customer xchildPremium1 = new Customer(11, "xbabyjohn", "tester", Customer.MembershipLevel.COMMUNITY, 1);
		Customer achildPremium1 = new Customer(12, "ababyjohn", "tester", Customer.MembershipLevel.PREMIUM, 1);

		Customer parentEnterprise1 = new Customer(2, "Michael", "manager", Customer.MembershipLevel.COMMUNITY, -1);
		Customer xchildEnterprise1 = new Customer(21, "xbabyMichael", "manager", Customer.MembershipLevel.ENTERPRISE, 2);
		Customer achildEnterprise1 = new Customer(22, "ababyMichael", "manager", Customer.MembershipLevel.COMMUNITY, 2);
		
		Customer parentCommunity1 = new Customer(3, "Dave", "developer", Customer.MembershipLevel.COMMUNITY, -1);
		
List<Customer> input = new ArrayList<>();
		
		input.add(xchildEnterprise1);
		input.add(achildPremium1);
		input.add(parentCommunity1);
		input.add(achildEnterprise1);
		input.add(parentEnterprise1);
		
		input.add(parentPremium1);
		input.add(xchildPremium1);
		
		input.forEach(e->System.out.println("original  "+e));
		 new SortTest2().getSortedCustomers_2(input).forEach(e->System.out.println(e));
		
		
		
	}
	
	

}