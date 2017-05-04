package bookie;

import java.time.LocalTime;

/**
 * Converts two LocalTime objects to one TimeSpan object.
 */
public class TimeSpan
{
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSpan(final LocalTime startTime, final LocalTime endTime) {
	if (startTime.isAfter(endTime)) {
	    throw new IllegalArgumentException("Start time must preceed end time.");
	}
	this.startTime = startTime;
	this.endTime = endTime;
    }

    public LocalTime getStartTime() {
	return startTime;
    }

    public LocalTime getEndTime() {
	return endTime;
    }

    @Override public String toString() {
	return startTime + "-" + endTime;
    }
}
