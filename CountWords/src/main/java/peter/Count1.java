package peter;

import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import static java.util.stream.Collectors.*;
import java.util.stream.Stream;

/**
 *@author Peter Li 05/14/2017
 * I write two other methods to solve the problem and use junit4 to test. Method 1 could be more faster 
 * when dealing with a little big file because almost all steps in method 1 is parallel; Method 2 is
 *  more easer to be understood. The result the I tested is in result1.jpg and result2.jpg files;
 * The compile and run commands is in the a.bat file and Steps are as follows:
 * Put all files in one directory and go into the dirctory, Execute the following commands in commandline  
 * javac -cp . Count1.java
 * java -cp . Count1
 * javac -cp .;junit-4.12.jar Count1Test.java  
 * java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore Count1Test
 *
 */

public class Count1 {
	private static final String FILE_PATH = "src/main/resource/paragraph.txt";
	private static Path p=Paths.get(FILE_PATH);
	
	/*Method 1:Using parallel compute could be more faster when dealing with a little big file */	
	public static ConcurrentMap<Integer, List<Entry<String, Integer>>> getWordsCon() {
		
		ConcurrentMap<Integer, List<Entry<String, Integer>>> words = null;		
	
		try {	
			
			words = Files.lines(p).parallel().unordered().flatMap(s -> Stream.of(s.split("\\W")))
					.filter(s -> !s.isEmpty()).parallel().unordered()
					.collect(toConcurrentMap(s -> s, s -> 1, Integer::sum)).entrySet().parallelStream().unordered()
					.collect(
							groupingByConcurrent(m -> m.getValue(),
									() -> new ConcurrentSkipListMap<Integer, List<Entry<String, Integer>>>(
											Comparator.reverseOrder()),
									mapping(s -> s, toList())));

		} catch (UncheckedIOException e) {
			System.err.println("File include inproper charse :" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Reader file error :" + e.getMessage());
			e.printStackTrace();
		}
		return words;
	}
	
	/*Method 2:this could be more easer to understand */
	public static List<Entry<String, Integer>> getWords() {

		List<Entry<String, Integer>> words = new ArrayList<>();

		try {
			Files.lines(p).parallel().flatMap(s -> Stream.of(s.split("\\W")))
					.filter(s -> !s.isEmpty()).parallel().collect(toConcurrentMap(s -> s, s -> 1,(x, y) -> x + y )).entrySet()
					.stream().sorted((m1, m2) -> m2.getValue() - m1.getValue()).forEach(e -> words.add(e));

		} catch (UncheckedIOException e) {
			System.err.println("File include inproper charse :" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Reader file error :" + e.getMessage());
			e.printStackTrace();
		}

		return words;

	}

	public static void main(String[] args) {
		
		/*Check file exists. */		
		if(Files.notExists(p)){
			System.err.println(FILE_PATH +" can not be found!");
			System.exit(0);
			}
		
		/* Use Method 1 to get result*/		
		ConcurrentMap<Integer, List<Entry<String, Integer>>> words = getWordsCon();
		if(words.isEmpty())
			System.err.println("File do not include any word!") ;
		else 
			words.values().forEach(System.out::println);
	
		/* Use Method 2 to get result */
		List<Entry<String, Integer>> words_2 = getWords();
		if(words_2.isEmpty())
			System.err.println("File do not include any word!") ;
		else 
		  words_2.forEach(System.out::println);
	}
}

