package ro.bogdanpanea.homework.db;

public class BookingDbException extends Throwable {

    public BookingDbException(String s, Exception e) {
        super(s, e);
    }

    public BookingDbException(String s) {
        super(s);
    }
}
