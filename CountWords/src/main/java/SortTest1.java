import java.util.List;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;
import java.util.stream.*;



public class SortTest1 {

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

        public String toString(){
			return firstName + " " + lastName;
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
  
	List<Customer> getSortedCustomers(List<Customer> inputCustomers) {
      List<Customer> result=new ArrayList<>();
      Map<Integer,Customer> cmap= inputCustomers.stream().collect(toMap(e->e.id,e->e));
      
      Comparator<Customer> compByFirst= comparing(c->c.firstName,String.CASE_INSENSITIVE_ORDER);
        Comparator<Customer> compByLast= comparing(c->c.lastName,String.CASE_INSENSITIVE_ORDER);
        Comparator<Customer> compByName= compByFirst.thenComparing(compByLast);
         Comparator<Customer> compByLevel= comparingInt((Customer c)->c.membershipLevel.ordinal());
        Comparator<Customer> compBy= compByLevel.thenComparing(compByName);
      
      
      inputCustomers.stream().map(c->{ Customer tmp;  if((c.parentFamilyMember>0) && ((tmp=cmap.get(c.parentFamilyMember)).membershipLevel.ordinal()>c.membershipLevel.ordinal()))
     
        tmp.membershipLevel=c.membershipLevel;
      else tmp=c;
                                      return tmp;})
        
        
                                   .collect(toList()).parallelStream()
                            .map( c->{if(c.parentFamilyMember>0) {c.membershipLevel =cmap.get(c.parentFamilyMember).membershipLevel ;}
                                     return c;} )
                            .collect(groupingBy(  (Customer c)->{int i= c.parentFamilyMember>0?c.parentFamilyMember:c.id; return cmap.get(i);} ,
                                                ()->new TreeMap<Customer,TreeSet<Customer>>(compBy),
                                                toCollection(()->new TreeSet<Customer>(compByName))
                            
                            ))
                            .forEach((k,v)->{result.add(k) ;v.forEach(e->{if(e.id!=k.id) result.add(e);}); });
        
         
		return result;		
	}
	
}