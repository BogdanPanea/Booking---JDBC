package ro.bogdanpanea.homework.dao.sql;

import ro.bogdanpanea.homework.dao.AccomodationDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLAccomodationDAO implements AccomodationDAO {

    private BookingDb db;

    public SQLAccomodationDAO(BookingDb bookingDb) {
        this.db = bookingDb;
    }

    @Override
    public List<Accomodation> getAll() throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from accomodation;");
            List<Accomodation> accomodations = new ArrayList<>();
            while (resultSet.next()) {
                Accomodation accomodation = mapResultSetToAccomodation(resultSet);
                accomodations.add(accomodation);
            }
            return accomodations;
        }
    }

    @Override
    public Accomodation getByAccomodationId(Accomodation  accomodation) throws BookingDbException, SQLException {
        try (Connection conn = db.connect()) {
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * from accomodation WHERE id=" + accomodation.getId() + ";");
            resultSet.next();

            return mapResultSetToAccomodation(resultSet);
        }
    }

    private Accomodation mapResultSetToAccomodation(ResultSet resultSet) throws SQLException {
        Accomodation accomodation = new Accomodation();
        accomodation.setId(resultSet.getInt("id"));
        accomodation.setType(resultSet.getString("type"));
        accomodation.setBedType(resultSet.getString("bed_type"));
        accomodation.setMaxGuests(resultSet.getInt("max_guests"));
        accomodation.setDescription(resultSet.getString("description"));
        return accomodation;
    }

    @Override
    public void delete(Accomodation accomodation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM accomodation WHERE id=" + accomodation.getId() + ";");
        }
    }

    @Override
    public void add(Accomodation accomodation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO accomodation(type, bed_type, max_guests, description) values('" + accomodation.getType() + "', '" + accomodation.getBedType() + "', '" + accomodation.getMaxGuests() + "', '" + accomodation.getDescription() + "');");
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT CURRVAL('accomodation_ids')");
            resultSet.next();
            accomodation.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void update(Accomodation accomodation) throws BookingDbException, SQLException {
        try (Connection connection = db.connect()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE accomodation SET type = '" + accomodation.getType() + "', bed_type = '" + accomodation.getBedType() + "', max_guests = '" + accomodation.getMaxGuests() + "', description = '" + accomodation.getDescription() + "' WHERE id = " + accomodation.getId() + ";");
        }
    }
}
