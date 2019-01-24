package ro.bogdanpanea.homework.dao;

import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;
import ro.bogdanpanea.homework.model.AccomodationFairRelation;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for Client data
 */
public interface AccomodationFairRelationDAO {

    // R
    List<AccomodationFairRelation> getAll() throws Exception, BookingDbException;
    List<AccomodationFairRelation> getByAccomodationFairRelationId(AccomodationFairRelation accomodationFairRelationId) throws BookingDbException, SQLException;

    // D
    void delete(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException;
    void delete(Accomodation accomodation) throws BookingDbException, SQLException;
    void delete(RoomFair roomFair) throws BookingDbException, SQLException;

    // C
    void add(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException;

    // U
    void update(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException;
}
