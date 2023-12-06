package ES_23_24_1sem.LETI.Grupo_N;

public class Time implements Comparable<Time> {

    private int hours;
    private int minutes;
    private int seconds;

    public Time(String s) {
        String[] d = s.split(":");
        hours = Integer.parseInt(d[0]);
        minutes = Integer.parseInt(d[1]);
        seconds = Integer.parseInt(d[2]);
    }

    @Override
    public int compareTo(Time o) {
        int x = Integer.compare(hours, o.hours);
        if (x == 0)
            x = Integer.compare(minutes, o.minutes);
        if (x == 0)
            x = Integer.compare(seconds, o.seconds);
        return x;
    }
    
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
