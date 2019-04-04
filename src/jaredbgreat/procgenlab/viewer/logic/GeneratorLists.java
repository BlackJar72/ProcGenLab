package jaredbgreat.procgenlab.viewer.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorLists {
	private static final GeneratorLists genlists = new GeneratorLists();
	private final Map<String, List<String>> categories = new HashMap<>();
	
	
	public static GeneratorLists getGeneratorLists() {
		return genlists;
	}

}
