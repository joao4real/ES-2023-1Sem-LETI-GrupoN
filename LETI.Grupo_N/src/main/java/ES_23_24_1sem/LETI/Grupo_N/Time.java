package ES_23_24_1sem.LETI.Grupo_N;

/**
 * The Time class represents a time with hours, minutes, and seconds and provides comparison methods.
 */
public class Time implements Comparable<Time> {

    private int hours;
    private int minutes;
    private int seconds;

    /**
     * Constructs a Time object from a formatted string.
     *
     * @param s The formatted string (e.g., "HH:mm:ss").
     */
    public Time(String s) {
        String[] d = s.split(":");
        hours = Integer.parseInt(d[0]);
        minutes = Integer.parseInt(d[1]);
        seconds = Integer.parseInt(d[2]);
    }

    /**
     * Compares this Time object with another Time object.
     *
     * @param o The other Time object to compare.
     * @return A negative integer, zero, or a positive integer if this Time is less than, equal to, or greater than the specified Time.
     */
    @Override
    public int compareTo(Time o) {
        int x = Integer.compare(hours, o.hours);
        if (x == 0)
            x = Integer.compare(minutes, o.minutes);
        if (x == 0)
            x = Integer.compare(seconds, o.seconds);
        return x;
    }
    
    /**
     * Checks if this Time object is equal to another object.
     *
     * @param o The other object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        Time newO;
        if (this == o) 
            return true;
        if(o instanceof Time) {
            newO = (Time) o;    
        } else
            return false;
        return this.compareTo(newO) == 0;
    }
}
