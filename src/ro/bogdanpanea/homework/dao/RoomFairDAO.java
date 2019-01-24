package ro.bogdanpanea.homework.dao;

import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.SQLException;
import java.util.List;

/**
 * CRUD for Client data
 */
public interface RoomFairDAO {

    // R
    List<RoomFair> getAll() throws Exception, BookingDbException;
    RoomFair getByRoomFairId(RoomFair roomFair) throws BookingDbException, SQLException;

    // D
    void delete(RoomFair roomFair) throws BookingDbException, SQLException;

    // C
    void add(RoomFair roomFair) throws BookingDbException, SQLException;

    // U
    void update(RoomFair roomFair) throws BookingDbException, SQLException;
}
