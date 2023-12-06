package ES_23_24_1sem.LETI.Grupo_N;

public class Date implements Comparable<Date> {

    private int day;
    private int month;
    private int year;

    public Date(String s) {
        String[] d = s.split("/");
        day = Integer.parseInt(d[0]);
        month = Integer.parseInt(d[1]);
        year = Integer.parseInt(d[2]);
    }

    @Override
    public int compareTo(Date o) {
        int x = Integer.compare(year, o.year);
        if (x == 0)
            x = Integer.compare(month, o.month);
        if (x == 0)
            x = Integer.compare(day, o.day);
        return x;
    }

    @Override
    public boolean equals(Object o) {
        Date newO;
        if (this == o) 
            return true;
        if(o instanceof Date) {
            newO = (Date) o;    
        } else
            return false;
        return this.compareTo(newO) == 0;
    }
}