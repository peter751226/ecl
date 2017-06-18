import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.*;

import com.lzh.CountWords.SortTest2.Customer;

public class SortTest {

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
	
	static class Helper{
		public int fLevel;      //customer's family group level ordinal for family level comparator  
		public String fFirst;   // family firstname and lastname
		public String fLast;    // family firstname and lastname
		public String first;    // parent should be first inside family compare by setting parent name to "" 
		public String last;     // parent should be first inside family compare by setting parent name to "" 
		public Helper(int fLevel, String fFirst, String fLast, String first, String last) {
			super();
			this.fLevel = fLevel;
			this.fFirst = fFirst;
			this.fLast = fLast;
			this.first = first;
			this.last = last;
		}
		
	}
	
	/*Solution 4: compose all the comparators  mapper key to a structure Helper class */
	
	List<Customer> getSortedCustomers_simple(List<Customer> inputCustomers) {
		//Collectors.toConcurrentMap(keyMapper, valueMapper)
	      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toConcurrentMap(e->e.id,e->e)); // helper to get parent customer
	      Map<Integer,Helper> helpmap = new HashMap<>();  //k is customer id , v is customer's mapping key for comparator          
	      inputCustomers.stream().filter(c->  c.parentFamilyMember<0).forEach(c->helpmap.put(c.id, new Helper(c.membershipLevel.ordinal(), c.firstName ,c.lastName,"","") ));  
	      inputCustomers.stream().filter(c->  c.parentFamilyMember>=0)
	      .forEach(c->{ Helper h;helpmap.put(c.id, new Helper(c.membershipLevel.ordinal(), cmap.get(c.parentFamilyMember).firstName, cmap.get(c.parentFamilyMember).lastName, c.firstName ,c.lastName) )  ;
	          if((h=helpmap.get(c.parentFamilyMember)).fLevel>c.membershipLevel.ordinal())  h.fLevel= c.membershipLevel.ordinal();});  
	                                                                                         // select max level of children to parent    
	      inputCustomers.stream()
	      .filter(c->  c.parentFamilyMember>0)
	      .forEach(c-> helpmap.get(c.id).fLevel= helpmap.get(c.parentFamilyMember).fLevel);     // set parent level to all children and got family level
	      
	    Comparator<Customer> compByGroupLevel = comparingInt((Customer c)-> helpmap.get(c.id).fLevel);        // compare according to family group level
	    Comparator<Customer> compByGroupFirst = comparing(c->helpmap.get(c.id).fFirst);
	    Comparator<Customer> compByGroupLast = comparing(c->helpmap.get(c.id).fLast);
	    Comparator<Customer> compByGroupName = compByGroupFirst.thenComparing(compByGroupLast);   //  compare according to family firstname and lastname
	    Comparator<Customer> compByGroup= compByGroupLevel.thenComparing(compByGroupName);        //  compare according to family level and then by family name
	    Comparator<Customer> compByFirst= comparing(c->helpmap.get(c.id).first);
	    Comparator<Customer> compByLast= comparing(c->helpmap.get(c.id).last);
	    Comparator<Customer> compByName= compByFirst.thenComparing(compByLast);   //  inside family compare according to name,parent should be first by set mapkey to "". 
	    Comparator<Customer> com=compByGroup.thenComparing(compByName);      // first by family level and then family name and third by name.
	    Collections.sort(inputCustomers ,com);            
			return inputCustomers;		
		}	
	
	
	/**  
	 *  @author By Peter Li 06/15/2017 
	 *  I think of three different solutions about the problem. All solutions could pass junit test method in the test.
	 *  The first solution with using pure comparators is the simplest; The second uses both comparators and collectors;	   
	 *  The third one can correctly sort and also update all customers' membershipLevel at the same time ,This maybe good in real project,
	 * 
	 *      Solution 1: This solution with using comparators is simplest and should pass the junit test method in the test.  
	 *      Solution 2: This solution with using both comparators and collectors should pass the junit test method in the test.  
	 *      Solution 3: This solution can correctly sort and update all customers' membershipLevel while sorting ,This maybe good in real project,
	 *   */
	
	/**     Solution 1: This solution with using comparators is simplest and should pass the junit test method in the test.   */	
	List<Customer> getSortedCustomers(List<Customer> inputCustomers) {
      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->e)); // helper to get parent customer
      Map<Integer,Integer> groupLevel = new HashMap<>();  //k is customer id , v is customer's family group level ordinal for family level comparator          
      inputCustomers.forEach(c->groupLevel.put(c.id, c.membershipLevel.ordinal()));  
      inputCustomers.stream().filter(c->  c.parentFamilyMember>0)
      .filter(c->groupLevel.get(c.parentFamilyMember)>c.membershipLevel.ordinal())
      .forEach(c->{if(groupLevel.get(c.parentFamilyMember)>c.membershipLevel.ordinal())  groupLevel.put(c.parentFamilyMember, c.membershipLevel.ordinal());});  
                                                                                         // select max level of children to parent    
      inputCustomers.stream()
      .filter(c->  c.parentFamilyMember>0)
      .forEach(c->{  groupLevel.put(c.id, groupLevel.get(c.parentFamilyMember));});     // set parent level to all children and got family level
      
    Comparator<Customer> compByGroupLevel = comparingInt((Customer c)-> groupLevel.get(c.id));        // compare according to family group level
    Comparator<Customer> compByGroupFirst = comparing(c->{int tmp=c.parentFamilyMember;  if(tmp>0) return cmap.get(tmp).firstName;  else return c.firstName;});
    Comparator<Customer> compByGroupLast = comparing(c->{int tmp=c.parentFamilyMember;  if(tmp>0)  return cmap.get(tmp).lastName;  else return c.lastName;});
    Comparator<Customer> compByGroupName = compByGroupFirst.thenComparing(compByGroupLast);   //  compare according to family firstname and lastname
    Comparator<Customer> compByGroup= compByGroupLevel.thenComparing(compByGroupName);        //  compare according to family level and then by family name
    Comparator<Customer> compByFirst= comparing(c->{if(c.parentFamilyMember<0)  return "" ; else return c.firstName;});
    Comparator<Customer> compByLast= comparing(c->{if(c.parentFamilyMember<0)  return "" ;  else return c.lastName;});
    Comparator<Customer> compByName= compByFirst.thenComparing(compByLast);   //  inside family compare according to name,parent should be first by set mapkey to "". 
    Comparator<Customer> com=compByGroup.thenComparing(compByName);      // first by family level and then family name and third by name.
    Collections.sort(inputCustomers ,com);            
		return inputCustomers;		
	}	
	
	/**     Solution 2: This solution with using both comparators and collectors should pass the junit test method in the test.   */
	List<Customer> getSortedCustomers_2(List<Customer> inputCustomers) {
	      List<Customer> sortedCustmers=inputCustomers;
	      Map<Integer,Customer> cmap_original= inputCustomers.stream().collect(toMap(e->e.id,e->e)); //keep original customers
	      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->{return new Customer(e.id,e.firstName,e.lastName, e.membershipLevel,e.parentFamilyMember);}));         
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
		
	/**     Solution 3: This solution can correctly sort and update all customers' membershipLevel while sorting ,This maybe good in real project,
	 *  it pass the junit test method in the test.   */
		List<Customer> getSortedCustomers_UpdatedLevel(List<Customer> inputCustomers) {
			  List<Customer> sortedCustmers=inputCustomers;	    
		         Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->e));  sortedCustmers.clear();
		         Comparator<Customer> compByName= comparing((Customer c)->c.firstName).thenComparing(comparing(c->c.lastName));
		         Comparator<Customer> compByLevel= comparingInt((Customer c)->c.membershipLevel.ordinal());
		        Comparator<Customer> compBy= compByLevel.thenComparing(compByName);	      
		      cmap.values().stream().map(c->{ Customer tmp;  if((c.parentFamilyMember>0) && ((tmp=cmap.get(c.parentFamilyMember)).membershipLevel.ordinal()>c.membershipLevel.ordinal()))    	     
		          tmp.membershipLevel=c.membershipLevel;    return c;}) 	        
		               .collect(toList()).stream() .map( c->{if(c.parentFamilyMember>0) c.membershipLevel =cmap.get(c.parentFamilyMember).membershipLevel ;  return c;} )	                 
		                            .collect(toList()).stream()
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
		
List<SortTest.Customer> input = new ArrayList<>();
		
		input.add(xchildEnterprise1);
		input.add(achildPremium1);
		input.add(parentCommunity1);
		input.add(achildEnterprise1);
		input.add(parentEnterprise1);
		
		input.add(parentPremium1);
		input.add(xchildPremium1);
		
		System.out.println("  original order :");
		input.forEach(e->System.out.println(e));
		System.out.println("\n  Sorted with pure comparators :\n");
		 new SortTest().getSortedCustomers(input).forEach(e->System.out.println(e));
		 
		   System.out.println("\n  Sorted with comparators and collectors :\n");
		 new SortTest().getSortedCustomers_2(input).forEach(e->System.out.println(e));
		 System.out.println("\n  Sorted with comparators and collectors and updated all customer's membershipLevel :\n");
		 new SortTest().getSortedCustomers_UpdatedLevel(input).forEach(e->System.out.println(e));
		 
		 System.out.println("\n  Helper:\n");
		 new SortTest().getSortedCustomers_simple(input).forEach(e->System.out.println(e));
	}

}