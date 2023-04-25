package es.unex.pi.util;

import java.util.*;
import es.unex.pi.model.Hosting;


public class LikesComparator implements Comparator <Hosting>{
	
	@Override
	public int compare (Hosting h1, Hosting h2) {
		if (h1.getLikes () == h2.getLikes ()) {
			return h1.getTitle ().compareTo (h2.getTitle ());
		}
		else {
			if (h1.getLikes () > h2.getLikes ()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}
}
