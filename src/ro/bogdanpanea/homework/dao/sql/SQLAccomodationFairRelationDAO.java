package ro.bogdanpanea.homework.dao.sql;

import ro.bogdanpanea.homework.dao.AccomodationFairRelationDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;
import ro.bogdanpanea.homework.model.AccomodationFairRelation;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLAccomodationFairRelationDAO implements AccomodationFairRelationDAO {

    private BookingDb db;

    public SQLAccomodationFairRelationDAO(BookingDb bookingDb) {
        this.db = bookingDb;
    }

    @Override
    public List<AccomodationFairRelation> getAll() throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from accomodation_fair_relation;");
            List<AccomodationFairRelation> accomodationFairRelations = new ArrayList<>();
            while (resultSet.next()) {
                AccomodationFairRelation accomodationFairRelation = mapResultSetToAccomodationFairRelation(resultSet);
                accomodationFairRelations.add(accomodationFairRelation);
            }
            return accomodationFairRelations;
        }
    }

    @Override
    public List<AccomodationFairRelation> getByAccomodationFairRelationId(AccomodationFairRelation l) throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from accomodation_fair_relation WHERE id=" + l.getId() + ";");
            List<AccomodationFairRelation> accomodationFairRelations = new ArrayList<>();
            while (resultSet.next()) {
                AccomodationFairRelation accomodationFairRelation1 = mapResultSetToAccomodationFairRelation(resultSet);
                accomodationFairRelations.add(accomodationFairRelation1);
            }
            return accomodationFairRelations;
        }
    }

    private AccomodationFairRelation mapResultSetToAccomodationFairRelation(ResultSet resultSet) throws SQLException {
        AccomodationFairRelation accomodationFairRelation = new AccomodationFairRelation();
        accomodationFairRelation.setId(resultSet.getInt("id"));
        accomodationFairRelation.setIdAccomodation(resultSet.getInt("id_accomodation"));
        accomodationFairRelation.setIdRoomFair(resultSet.getInt("id_room_fair"));
        return accomodationFairRelation;
    }

    @Override
    public void delete(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM accomodation_fair_relation WHERE id=" + accomodationFairRelation.getId() + ";");
        }
    }

    @Override
    public void delete(Accomodation accomodation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM accomodation_fair_relation WHERE id=" + accomodation.getId() + ";");
        }
    }

    @Override
    public void delete(RoomFair roomFair) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM accomodation_fair_relation WHERE id=" + roomFair.getId() + ";");
        }
    }

    @Override
    public void add(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO accomodation_fair_relation(id_accomodation, id_room_fair) values('" + accomodationFairRelation.getIdAccomodation() + "', '" + accomodationFairRelation.getIdRoomFair() + "');");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRVAL('accomodation_fair_relation_ids')");
            resultSet.next();
            accomodationFairRelation.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(AccomodationFairRelation accomodationFairRelation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE accomodation_fair_relation SET id_accomodation = '" + accomodationFairRelation.getIdAccomodation() + "', id_room_fair = '" + accomodationFairRelation.getIdRoomFair() + ";");
        }
    }
}
