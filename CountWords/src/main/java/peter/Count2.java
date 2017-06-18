package peter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author peter
 *
 */
public class Count2 {
	 public static List<Entry<String, Integer>> getWords() {
		 
			String aa[]={"fd","s"}; 
			"opq".chars().mapToObj(i->String.valueOf((char)i)).forEach(System.out::println);
			Integer[] ii={3,4,5};
			Stream.of("kkj".chars().boxed().toArray()).forEach(System.out::println);
			Stream.of(ii).forEach(System.out::println);
			Stream.of("opqw".split("")).forEach(System.out::println);;
			
		
		List<Entry<String, Integer>> words = new ArrayList<>();
		try {
			
			Files.lines(Paths.get("src/main/resource/paragraph.txt")).flatMap(s -> Stream.of(s.split("\\W")))
					.filter(s -> !s.isEmpty()).parallel()
					.collect(Collectors.toConcurrentMap(s -> s, s -> 1, Integer::sum)).entrySet().stream()
					.sorted((m1, m2) -> m2.getValue() - m1.getValue()).forEach(e -> words.add(e));
		} catch (IOException e) {
			System.err.println("File is not Found :" + e.getMessage());
		}
		return words;
	}

	public static void main(String[] args) {
		getWords().forEach(System.out::println);

	}
}