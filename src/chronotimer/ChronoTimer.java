package chronotimer;

import java.time.LocalTime;
import java.util.ArrayList;

import race.EventType;
import race.IND;

public class ChronoTimer {

	private boolean isOn;
	ArrayList<IND> runs;
	Channel[] channels;
	LocalTime time;
	
	private static ChronoTimer singleton = new ChronoTimer();
	
	// A private Constructor prevents any other class from instantiating.
	private ChronoTimer() {
		runs = new ArrayList<IND>();
		runs.add(new IND());

		channels = new Channel[12];
		for (int i = 0; i < channels.length; i++)
			channels[i] = new Channel();
		
		time = LocalTime.now();
	}
	
	// Static 'instance' method
	public static ChronoTimer getInstance() {
		return singleton;
	}

	// ON(if off) Turn system on, enter quiescent state 
	public void turnOn() {
		if (!isOn) {
			isOn = true;
		}
	}
	// OFF(if on) Turn system off (but stay in simulator) 
	public void turnOff() {
		if (isOn) {
			isOn = false;
		}
	}

	// EXIT Exit the simulator 
	public void exit() {
		System.exit(0);
	}

	// RESET Resets the System to initial state 
	public void reset() {
		if (isOn) {
			singleton = new ChronoTimer();
		}
	}

	// TIME <hour>:<min>:<sec> Set the current time 
	public void setTime(LocalTime time) {
		if (isOn) {
			this.time = time;
		}
	}

	// TOGGLE <channel> Toggle the state of the channel <CHANNEL> 
	public void toggleChannel(int channel) {
		if (isOn) {
			channels[channel-1].toggle();
		}
	}

	// CONN <sensor> <NUM> Connect a type of sensor to channel <NUM> <sensor> = {EYE, GATE, PAD} 
	public void connectSensor(Sensor sensor, int channel) {
		if (isOn) {
			channels[channel-1].connect(sensor);
		}
	}

	// DISC <NUM> Disconnect a sensor from channel <NUM> 
	public void disconnectSensor(int channel) {
		if (isOn) {
			channels[channel-1].disconnect();
		}
	}

	// EVENT <TYPE> IND | PARIND | GRP | PARGRP 
	public void setEvent(EventType type) {
		if (isOn) {
			switch (type) {
			case IND:
				runs.set(runs.size() - 1, new IND());
				break;

			default:
				throw new IllegalArgumentException("Event type not yet implemented");
			}
		}
	}

	// NEWRUN Create a new Run (must end a run first) 
	public void newRun() {
		if (isOn) {
			endRun();
			runs.add(new IND());
		}
	}

	// ENDRUN Done with a Run 
	public void endRun() {
		if (isOn) {
			getCurrentRun().end();
		}
	}

	// PRINT <RUN> Print the run on stdout 
	public void printRun(int runNumber) {
		if (isOn) {
			System.out.println(runs.get(runNumber - 1));
		}
	}

	// EXPORT <RUN> Export run in XML to file �RUN<RUN>� 
	public void exportRun(int runNumber) {
		if (isOn) {
			// TODO
		}
	}

	// NUM <NUMBER> Set <NUMBER> as the next competitor to start. 
	public void num(int bibNumber) {
		if (isOn) {
			getCurrentRun().num(bibNumber);
		}
	}

	// CLR <NUMBER> Clear <NUMBER> as the next competitor 
	public void clearNextCompetitor(int bibNumber) {
		if (isOn) {
			// TODO
		}
	}

	// SWAP Exchange next two competitors to finish in IND 
	public void swap() {
		if (isOn) {
			// Project Description states : If there is more than one racer active, the finish event is associated with racers	
			// in a	FIFO (first	in,	first out) basis. 
			// This means we just swap runs(0) with runs(1) since these two will be next two to finish according to FIFO rules.
			
			// Need at least two runners to perform a swap.
			if(runs.size() < 2) throw new IllegalStateException("Not enough runners to perform a swap.");
			IND swapSecondToFirst = runs.get(1);
			// Add previous second racer to beginning of list to swap next two finishing racers
			runs.remove(1);
			runs.add(0,swapSecondToFirst);
		}
	}

	// DNF The next competitor to finish will not finish 
	public void DNF() {
		if (isOn) {
			getCurrentRun().DNFRacer();
		}
	}

	// TRIG <NUM> Trigger channel <NUM>
	public void trigger(int channel) {
		if (isOn && channels[channel - 1].trigger()) {
			if (channel % 2 == 1) {
				getCurrentRun().startRacer(time);
			} else {
				getCurrentRun().finishRacer(time);
			}
		}
	}

	// START Start trigger channel 1 (shorthand for TRIG 1) 
	public void start() {
		if (isOn) {
			trigger(1);
		}
	}

	// FINISH Finish trigger channel 2 (shorthand for TRIG 2)
	public void finish() {
		if (isOn) {
			trigger(2);
		}
	}
	
	private IND getCurrentRun() {
		return runs.get(runs.size() - 1);
	}

}