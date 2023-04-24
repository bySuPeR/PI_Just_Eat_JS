package es.unex.pi.util;

import java.util.Comparator;
import es.unex.pi.model.Restaurant;

public class RestaurantComparator implements Comparator <Restaurant> {
	
	@Override
	public int compare (Restaurant r1, Restaurant r2) {
		if (r1.getGradesAverage () == r2.getGradesAverage ()) {
			return 0;
		}
		else if (r1.getGradesAverage () > r2.getGradesAverage ()) {
			return -1;
		}
		else {
			return 1;
		}
	}
}
