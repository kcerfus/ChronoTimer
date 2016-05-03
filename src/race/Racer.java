package race;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

import chronotimer.Time;

/** Racer who runs in a race
 */
public class Racer  {
	
	private int bibNumber;
	private LocalTime startTime;
	private LocalTime finishTime;

	/** Constructor for a racer with a specified bib number
	 * @param bibNumber the bib number of the racer
	 */
	public Racer(int bibNumber) {
		setBib(bibNumber);
	}

	/** Sets the start time of the racer
	 * @param time the start time of the racer
	 */
	public void setStart(LocalTime time) {
		if (time == null) {
			System.out.println("Time cannot be null");
		} else {
			startTime = time;
		}
	}
	
	/** Sets the finish time of the racer
	 * @param time the finish time of the racer
	 */
	public void setFinish(LocalTime time) {
		if (time == null) {
			System.out.println("Time cannot be null");
		} else if (startTime == null) {
			System.out.println("Must start before finish");
		} else {
			finishTime = time;
		}
	}
	
	/** Marks the racer as DNF
	 */
	public void DNF() {
		if (startTime == null) {
			System.out.println("Must start before DNF");
		} else {
			finishTime = null;
		}
	}
	
	/** Gets the start time of the racer
	 * @return the start time of the racer
	 */
	public LocalTime getStart() {
		return startTime;
	}

	/** Gets the finish time of the racer
	 * @return the finish time of the racer
	 */
	public LocalTime getFinish() {
		return finishTime;
	}
	
	/** Gets the bib of the racer
	 * @return the bib of the racer
	 */
	public int getBib() {
		return bibNumber;
	}
	
	/** Sets the bib of the racer
	 * @param bibNumber the bib of the racer
	 */
	public void setBib(int bibNumber) {
		if(bibNumber > 99999 || bibNumber < 0) {
			throw new IllegalArgumentException("Valid racer bib numbers are 0-99999");
		} else {
			this.bibNumber = bibNumber;
		}
	}

	/** Gets the duration time of the racer
	 * @return the duration time of the racer
	 */
	public Duration getDuration() {
		if (startTime == null || finishTime == null) {
			System.out.println("Must have a start and finish to get duration");
			return null;
		} else {
			return Duration.between(startTime, finishTime);
		}
	}

	@Override
	public String toString() {
		if (startTime == null && finishTime == null) {
			return bibNumber + " CANCEL"; 
		} else if (startTime != null && finishTime == null) {
			return bibNumber + " DNF"; 
		} else {
			Duration duration = getDuration();
			return bibNumber + " ELAPSED " + Time.toString(LocalTime.MIDNIGHT.plus(duration));
		}
	}


	
	public static class Comparators {
		public static Comparator<Racer> DURATION = new Comparator<Racer>() {
			@Override
			public int compare(Racer e1, Racer e2){
					return e1.getDuration().compareTo(e2.getDuration());
				
			}
		};

	}

}

