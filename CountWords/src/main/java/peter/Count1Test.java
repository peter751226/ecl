package peter;

import java.util.AbstractMap;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author Peter Li 05/14/2017
 * Test two methods with the given paragragh.txt file.
 */
public class Count1Test {

	@Test
	public void testGetWordsCon() {
		Assert.assertTrue(Count1.getWordsCon().get(8).contains( new AbstractMap.SimpleEntry<String, Integer>("you", 8)));
		Assert.assertTrue(Count1.getWordsCon().get(10).contains( new AbstractMap.SimpleEntry<String, Integer>("that", 10)));
	}
	@Test
	public void testGetWords() {
		Assert.assertTrue(Count1.getWords().contains( new AbstractMap.SimpleEntry<String, Integer>("you", 8)));
		Assert.assertTrue(Count1.getWords().contains( new AbstractMap.SimpleEntry<String, Integer>("that", 10)));
	}	

}
