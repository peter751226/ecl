package com.lzh.CountWords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
	static public Map<String, Integer> words=null;
	@SuppressWarnings("unchecked")
	static public Map<String, Integer> getWords(){
		Map<String, Integer> wor=new HashMap<>();
		try { 
			       Files.lines(Paths.get("src/main/resource/paragraph.txt"))
					// .peek(System.out::println)
					.flatMap(s -> Stream.of(s.split("\\W")))
					// .peek(System.out::println)
					.filter(s -> !s.isEmpty()).parallel()
				//	.collect(Collectors.toConcurrentMap(s -> s, s -> 1, Integer::sum,ConcurrentSkipListMap::new))
					.collect(Collectors.toConcurrentMap(s -> s, s -> 1, Integer::sum))
					.entrySet().stream().peek(System.out::println)
					 .sorted((m1,m2)->m2.getValue()-m1.getValue())
					//.sorted(Comparator.comparingInt(m -> ((Entry<String, Integer>) m).getValue()).reversed()
				//			.thenComparing(m -> ((Entry<String, Integer>) m).getKey()))
					//.peek(System.out::println)
					.forEach(e->wor.put(e.getKey(), e.getValue()));
					 ;
					//.collect(Collectors.toMaptoMap(HashMap::new,m -> m.getKey(), m -> m.getValue()))
					
				
	
	} catch (IOException e) {
		System.err.println("File is not Found :" + e.getMessage());
	}
		return wor;
	}
	
	public static void main(String[] args) {
              
		            words=getWords();
		           // Arrays.asList(words).forEach(System.out::println);
		        //   System.out.println(words);
			

	}
}
		