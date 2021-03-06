package se.liu.ida.dx17.tddd78.bookie.Calendar;

import se.liu.ida.dx17.tddd78.bookie.User.User;
import se.liu.ida.dx17.tddd78.bookie.User.UserList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles rules and functionality for Calendars.
 * Each calendar is connected to a User.
 */
public class Calendar {

    /*
    A constant value independent of the object.
     */
    private static final int MINUTES_PER_HOUR = 60;

    private User user;
    private String name;
    private List<Appointment> appointments;

    public Calendar(final User user, final String name) {
	this.user = user;
	this.name = name;
	appointments = new ArrayList<>();
    }

    public void book(final LocalDate date, final TimeSpan span, final String subject) {
	if (date.isBefore(LocalDate.now())) {
	    throw new IllegalArgumentException("Cannot book past date.");
	}
	if (isAlreadyBooked(span, date)) {
	    throw new IllegalArgumentException("Time is already booked");
	}
	appointments.add(new Appointment(date, span, subject));
	appointments.sort(new AppointmentComparator());
    }

    public void cancelAppointment(Appointment app) {
	if (appointments.contains(app)) {
	    appointments.remove(app);
	} else throw new IllegalArgumentException("This appointment is not booked");
    }

    private boolean isTimeInSpan(LocalTime time, TimeSpan span) {
	if (time.isAfter(span.getStartTime()) && time.isBefore(span.getEndTime())) {
	    return true;
	}
	return false;
    }


    private boolean overlapsBooking(LocalTime time, LocalDate date, List<Appointment> appointments) {
	for (Appointment app : appointments) {
	    if (app.getDate().compareTo(date) == 0 && isTimeInSpan(time, app.getSpan())) {
		return true;
	    }
	}
	return false;
    }

    private boolean isAlreadyBooked(TimeSpan span, LocalDate date) {
    /*
    	Checks every time between this appointment's starttime and endtime
    	and compares to every other appointment's starttime and endtime.
    	 */
	for (int hour = span.getStartTime().getHour(); hour <= span.getEndTime().getHour(); hour++) {
	    for (int minute = 0; minute < MINUTES_PER_HOUR; minute++) {
		if ((LocalTime.of(hour, minute).isAfter(span.getStartTime()) &&
		     LocalTime.of(hour, minute).isBefore(span.getEndTime())) && overlapsBooking(LocalTime.of(hour, minute), date, appointments)) {
		    return true;
		}
	    }
	}
	return false;
    }


    /**
     * Checks if a calendar is already existing.
     *
     * @param cal Calendar to check for existance.
     *
     * @return true if cal does exist, false if it does not.
     */
    public static boolean isExistingCalendar(Calendar cal) {
	final UserList userList = UserList.getInstance();
	for (User user : userList.getExistingUsers()) {
	    if (!user.getCalendars().isEmpty()) {
		for (Calendar calendar : user.getCalendars()) {
		    if (cal.name.equals(calendar.name)) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    public List<Appointment> getAppointments() {
	return appointments;
    }

    public User getUser() {
	return user;
    }

    @Override public String toString() {
	return name;
    }
}