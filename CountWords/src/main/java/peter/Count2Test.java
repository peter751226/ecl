package peter;

import java.util.AbstractMap;
import org.junit.Test;
import junit.framework.Assert;
import peter.Count2;

public class Count2Test {

	@Test
	public void testGetWords() {
		Assert.assertTrue(Count2.getWords().contains( new AbstractMap.SimpleEntry<String, Integer>("you", 8)));
		Assert.assertTrue(Count2.getWords().contains( new AbstractMap.SimpleEntry<String, Integer>("that", 10)));
	}	
}
