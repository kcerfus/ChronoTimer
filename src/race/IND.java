package race;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/** Individual timed events (such as ski races, bobsled runs) In the IND events,
 * racers queue up for single �runs� of the race. Each racer has a start event
 * and end event. By convention the start is on channel 1, the finish is on
 * channel 2, but there is nothing particularly special about the events on each
 * channel. When a start event is received, it is associated with the next racer
 * in the start queue. When a finish event is received, it is associated with
 * the next racer that is to finish. If there is more than one racer active, the
 * finish event is associated with racers in a FIFO (first in, first out) basis
 */
public class IND {

	private Queue<Racer> pendingRacers;
	private Queue<Racer> startedRacers;
	private ArrayList<Racer> finishedRacers;
	private boolean isOngoing;

	/** Default constructor for IND
	 */
	public IND() {
		startedRacers = new LinkedList<Racer>();
		pendingRacers = new LinkedList<Racer>();
		finishedRacers = new ArrayList<Racer>();
		isOngoing = true;
	}
	
	/** Sets the next competitor to start
	 * @param bibNumber the bib number of the next competitor to start
	 */
	public void num(int bibNumber) {
		if (isOngoing) {
			pendingRacers.add(new Racer(bibNumber));
		} else {
			throw new IllegalStateException("This event is ended");
		}
	}
	
	public void clear(int bibNumber) {
		// We need to clear the racer whose bibId matches from pending
		Queue<Racer> replacePending = new LinkedList<Racer>();
		
		// If the next racers bib != bibId add it to new queue; don't add racer we want to clear.
		while(!pendingRacers.isEmpty()) {
			if(pendingRacers.peek().getBib() != bibNumber) 
				replacePending.add(pendingRacers.remove());
			else pendingRacers.remove();
		}
		
		//set pending queue to new queue that doesn't include cleared racers
		pendingRacers = replacePending;
	}
	
	/** Marks the next active racer waiting to finish as canceled
	 */
	public void cancelRacer() {
		if (isOngoing) {
			startedRacers.peek().cancel();
			finishedRacers.add(startedRacers.poll());
		} else {
			throw new IllegalStateException("This event is ended");
		}
	}

	/** Starts the next racer waiting to start
	 * @param start the time the racer started at
	 */
	public void startRacer(LocalTime start) {
		if (isOngoing) {
			pendingRacers.peek().setStart(start);
			startedRacers.add(pendingRacers.poll());
		} else {
			throw new IllegalStateException("This event is ended");
		}
	}

	/** Finishes the next active racer waiting to finish
	 * @param finish the time the racer finished at
	 */
	public void finishRacer(LocalTime finish) {
		if (isOngoing) {
			startedRacers.peek().setFinish(finish);
			finishedRacers.add(startedRacers.poll());
		} else {
			throw new IllegalStateException("This event is ended");
		}
	}

	/** Marks the next active racer waiting to finish as DNF
	 */
	public void DNFRacer() {
		if (isOngoing) {
			startedRacers.peek().DNF();
			finishedRacers.add(startedRacers.poll());
		} else {
			throw new IllegalStateException("This event is ended");
		}
	}
	
	/** Checks if the run is ongoing
	 * @return true if the run is ongoing, false if ended
	 */
	public boolean isOngoing() {
		return isOngoing;
	}
	
	/** Marks the run as ended
	 */
	public void end() {
		isOngoing = false;
	}

	public void swap() {
		if(startedRacers.size() < 2) throw new IllegalStateException("Need at least 2 racers to perform swap!");
		Racer swapWithSecond = startedRacers.remove();
		
		// Need a temporary queue to replace current queue with swapped racers
		Queue<Racer> newQueue = new LinkedList<Racer>();
		
		//swap first two racers
		newQueue.add(startedRacers.remove());
		newQueue.add(swapWithSecond);
		
		// Add rest of racers to temp queue
		newQueue.addAll(startedRacers);
		
		// Relpace original Queue with temp queue
		startedRacers = newQueue;
	}
	
	@Override
	public String toString() {
		String description = "";
		for (Racer racer : finishedRacers)
			description += racer + "\n";
		for (Racer racer : startedRacers)
			description += racer + "\n";
		for (Racer racer : pendingRacers)
			description += racer + "\n";
		
		return description;
	}
	// TESTING PURPOSES
	public Queue<Racer> getStartedRacerQueue() { return startedRacers; }
	public Queue<Racer> getPendingRacerQueue() { return pendingRacers; }
	public ArrayList<Racer> getFinishedRacers() { return finishedRacers; }
}