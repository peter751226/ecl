package com.lzh.CountWords;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Count8 {

	static public Map<Integer, List<Entry<String,Integer>>> words=null;
	@SuppressWarnings("unchecked")
	static public Map<Integer, List<Entry<String,Integer>>> getWords(){
		Map<Integer, List<Entry<String,Integer>>> wor=new TreeMap<>(Comparator.reverseOrder());
		
		try { 
			      wor= Files.lines(Paths.get("src/main/resource/paragraph.txt"))
					// .peek(System.out::println)
					.flatMap(s -> Stream.of(s.split("\\W")))
					// .peek(System.out::println)
					.filter(s -> !s.isEmpty()).parallel()
					.collect(Collectors.toConcurrentMap(s -> s, s -> 1, Integer::sum))
					.entrySet().parallelStream()
					// .sorted((m1,m2)->m2.getValue()-m1.getValue())
					//.sorted(Comparator.comparingInt(m -> ((Entry<String, Integer>) m).getValue()).reversed()
				//			.thenComparing(m -> ((Entry<String, Integer>) m).getKey()))
					.peek(System.out::println)
				//	.collect(Collectors.groupingByConcurrent(m ->  m.getValue()))
					.collect( Collectors.groupingByConcurrent(
							m -> m.getValue()
				//			,ConcurrentSkipListMap::new
							,()->new ConcurrentSkipListMap <Integer,List<Entry<String,Integer>> >(Comparator.reverseOrder())
							,Collectors.mapping(s -> s,Collectors.toList())
				//			))
				//	.forEach(e->wor.put(e.getKey(), e.getValue()));
					)) ;
					//.collect(Collectors.toMap(HashMap::new,m -> m.getKey(), m -> m.getValue()))
				//	Collectors.ggroupingByConcurrent(classifier, mapFactory, downstream)(m->m.getValue(), m->m,);
			      
				
	
	} catch (IOException e) {
		System.err.println("File is not Found :" + e.getMessage());
	}
		return wor;
	}
	
	public static void main(String[] args) {
              
		            words=getWords();
		           // Arrays.asList(words).forEach(System.out::println);
		           System.out.println(words);
		           words.values().forEach(System.out::println);
			
	}
}
