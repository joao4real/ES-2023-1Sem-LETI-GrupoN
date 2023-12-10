package ES_23_24_1sem.LETI.Grupo_N;

/**
 * The Date class represents a date with day, month, and year components.
 * It implements the Comparable interface for comparing dates and overrides
 * the equals method for equality comparison.
 */
public class Date implements Comparable<Date> {

    private int day;
    private int month;
    private int year;

    /**
     * Constructs a Date object based on a string representation in the format "day/month/year".
     *
     * @param s The string representation of the date.
     */
    public Date(String s) {
        String[] d = s.split("/");
        String[] conf = App.getDateConf();
        for(int i = 0; i < d.length;i++)
            switch(conf[i]) {
            case "aaaa":
                year = Integer.parseInt(d[i]);
                break;
            case "mm":
                month = Integer.parseInt(d[i]);
                break;
            default:
                day = Integer.parseInt(d[i]);
            }
   }

    /**
     * Compares this Date object with another Date object for order.
     *
     * @param o The Date object to be compared.
     * @return A negative integer, zero, or a positive integer as this date is less than,
     *         equal to, or greater than the specified date.
     */
    @Override
    public int compareTo(Date o) {
        int x = Integer.compare(year, o.year);
        if (x == 0)
            x = Integer.compare(month, o.month);
        if (x == 0)
            x = Integer.compare(day, o.day);
        return x;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The object to compare with this Date.
     * @return True if the specified object is a Date and is equal to this Date; false otherwise.
     */
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