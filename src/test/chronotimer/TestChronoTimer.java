package test.chronotimer;

import static org.junit.Assert.*;

import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

import race.EventType;
import race.IND;
import race.PARIND;
import chronotimer.ChronoTimer;
import chronotimer.Sensor;


public class TestChronoTimer {
	
	private ChronoTimer chronotimer;
	private LocalTime time;
	
	@Before
	public void before() {
		chronotimer = new ChronoTimer();
		chronotimer.ON();
		time = LocalTime.now();
	}
	
	@Test
	public void testON(){
		chronotimer.OFF();
		assertFalse(chronotimer.isOn());
		
		chronotimer.ON();
		assertTrue(chronotimer.isOn());
	}
	
	@Test
	public void testOFF(){
		assertTrue(chronotimer.isOn());
		
		chronotimer.OFF();
		assertFalse(chronotimer.isOn());
	}
	
	@Test
	public void testEXIT(){
		// Cannot be tested, system exit's before assertion
	}
	
	@Test
	public void testRESET(){
		// TODO
	}
	
	@Test
	public void testTIME(){
		LocalTime time = this.time.plusMinutes(5);
		assertNotEquals(time, chronotimer.getTime());
		
		chronotimer.TIME(time);
		assertEquals(time, chronotimer.getTime());
	}
	
	@Test
	public void testTOGGLE(){
		assertFalse(chronotimer.getChannels()[0].isArmed());
		
		chronotimer.TOGGLE(0);
		assertTrue(chronotimer.getChannels()[0].isArmed());
		
		chronotimer.TOGGLE(0);
		assertFalse(chronotimer.getChannels()[0].isArmed());
	}
	
	@Test
	public void testCONN(){
		assertEquals(Sensor.NONE, chronotimer.getChannels()[0].getSensor());
		
		chronotimer.CONN(Sensor.EYE, 0);
		assertEquals(Sensor.EYE, chronotimer.getChannels()[0].getSensor());
	}
	
	@Test
	public void testDISC(){
		chronotimer.CONN(Sensor.EYE, 0);
		assertEquals(Sensor.EYE, chronotimer.getChannels()[0].getSensor());

		chronotimer.DISC(0);
		assertEquals(Sensor.NONE, chronotimer.getChannels()[0].getSensor());
	}
	
	@Test
	public void testEVENT(){
		assertTrue(chronotimer.getCurrentRun() instanceof IND);
		
		chronotimer.EVENT(EventType.PARIND);
		assertTrue(chronotimer.getCurrentRun() instanceof PARIND);
	}
	
	@Test
	public void testNEWRUN(){
		assertEquals(1, chronotimer.getRuns().size());
		
		chronotimer.NEWRUN();
		assertEquals(2, chronotimer.getRuns().size());
	}
	
	@Test
	public void testENDRUN(){
		assertEquals(1, chronotimer.getRuns().size());
		
		chronotimer.ENDRUN();
		assertEquals(2, chronotimer.getRuns().size());
		assertEquals(null, chronotimer.getCurrentRun());
	}
	
	@Test
	public void testPRINT(){
		// TODO
	}
	
	@Test
	public void testEXPORT(){
		// TODO
	}
	
	@Test
	public void testNUM(){
		// TODO
	}
	
	@Test
	public void testCLR(){
		// TODO
	}
	
	@Test
	public void testSWAP(){
		// TODO
	}
	
	@Test
	public void testDNF(){
		// TODO
	}
	
	@Test
	public void testTRIG(){
		// TODO
	}
	
	@Test
	public void testSTART(){
		// TODO
	}
	
	@Test
	public void testFINISH(){
		// TODO
	}
}
