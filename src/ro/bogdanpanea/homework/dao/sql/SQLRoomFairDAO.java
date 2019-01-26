package ro.bogdanpanea.homework.dao.sql;

import ro.bogdanpanea.homework.dao.RoomFairDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLRoomFairDAO implements RoomFairDAO {

    private BookingDb db;

    public SQLRoomFairDAO(BookingDb bookingDb) {
        this.db = bookingDb;
    }

    @Override
    public List<RoomFair> getAll() throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from room_fair;");
            List<RoomFair> roomFairs = new ArrayList<>();
            while (resultSet.next()) {
                RoomFair roomFair = mapResultSetToRoomFair(resultSet);
                roomFairs.add(roomFair);
            }
            return roomFairs;
        }
    }

    @Override
    public RoomFair getByRoomFairId(RoomFair  roomFair) throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from room_fair WHERE id=" + roomFair.getId() + ";");
            resultSet.next();

            return mapResultSetToRoomFair(resultSet);
        }
    }

    public RoomFair mapResultSetToRoomFair(ResultSet resultSet) throws SQLException {
        RoomFair roomFair = new RoomFair();
        roomFair.setId(resultSet.getInt("id"));
        roomFair.setValue(resultSet.getInt("value"));
        roomFair.setSeason(resultSet.getString("season"));
        return roomFair;
    }

    @Override
    public void delete(RoomFair roomFair) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM room_fair WHERE id=" + roomFair.getId() + ";");
        }
    }

    @Override
    public void add(RoomFair roomFair) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO room_fair(value, season) values('" + roomFair.getValue() + "', '" + roomFair.getSeason() + "');");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRVAL('room_fair_ids')");
            resultSet.next();
            roomFair.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(RoomFair room_fair) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE room_fair SET value = '" + room_fair.getValue() + "', season = '" + room_fair.getSeason() + "' WHERE id = " + room_fair.getId() + ";");
        }
    }
}
