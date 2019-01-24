package ro.bogdanpanea.homework.dao;

import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for Client data
 */
public interface AccomodationDAO {

    // R
    List<Accomodation> getAll() throws Exception, BookingDbException;
    Accomodation getByAccomodationId(Accomodation accomodation) throws BookingDbException, SQLException;

    // D
    void delete(Accomodation accomodation) throws BookingDbException, SQLException;

    // C
    void add(Accomodation accomodation) throws BookingDbException, SQLException;

    // U
    void update(Accomodation accomodation) throws BookingDbException, SQLException;
}
