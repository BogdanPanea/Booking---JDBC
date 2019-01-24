package ro.bogdanpanea.homework.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingDb {

    // Creates a connection to the PostgreSQL without selecting a database
    private Connection connectToPostgreSQL() throws SQLException, BookingDbException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            Connection connection = null;
            DriverManager.setLoginTimeout(60);
            String url = new StringBuilder()
                    .append("jdbc:")
                    .append("postgresql")       // “mysql” / “db2” / “mssql” / “oracle” / ...
                    .append("://")
                    .append("localhost")
                    .append(":")
                    .append(5432)
                    .append("/")
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("1234").toString();
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BookingDbException("Could not load DB driver.", e);
        }
    }

    public Connection connect() throws BookingDbException, SQLException {
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            Connection connection = null;
            DriverManager.setLoginTimeout(60);
            String url = new StringBuilder()
                    .append("jdbc:")
                    .append("postgresql")       // “mysql” / “db2” / “mssql” / “oracle” / ...
                    .append("://")
                    .append("localhost")
                    .append(":")
                    .append(5432)
                    .append("/")
                    .append("booking")
                    .append("?user=")
                    .append("postgres")
                    .append("&password=")
                    .append("1234").toString();
            return DriverManager.getConnection(url);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new BookingDbException("Could not load DB driver.", e);
        }
    }

    public static void setUpDB() throws BookingDbException, SQLException {
        BookingDb bookingDb = new BookingDb();
        try (Connection connection = bookingDb.connectToPostgreSQL()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE DATABASE booking;");
        }

        // connect to newly created tests database and create tables.
        try (Connection connection = bookingDb.connect()) {
            StringBuilder builder = new StringBuilder();
            builder.append("CREATE SEQUENCE accomodation_ids;");
            builder.append("CREATE TABLE accomodation(id INT PRIMARY KEY DEFAULT NEXTVAL('accomodation_ids'), type VARCHAR(32), bed_type VARCHAR(32),  max_guests INT, description  VARCHAR(512));");
            builder.append("CREATE SEQUENCE room_fair_ids;");
            builder.append("CREATE TABLE room_fair(id INT PRIMARY KEY DEFAULT NEXTVAL('room_fair_ids'), value double precision, season VARCHAR(32));");
            builder.append("CREATE SEQUENCE accomodation_fair_relation_ids;");
            builder.append("CREATE TABLE accomodation_fair_relation(id INT PRIMARY KEY DEFAULT NEXTVAL('accomodation_fair_relation_ids'), id_accomodation INT REFERENCES accomodation(id), id_room_fair INT REFERENCES room_fair(id));");

            Statement statement = connection.createStatement();
            statement = connection.createStatement();
            statement.execute(builder.toString());

        }
    }

    public void dropDataFromTables() throws BookingDbException, SQLException {
        try(Connection connection = connect()){
            StringBuilder builder = new StringBuilder();
            builder.append("DELETE FROM accomodation_fair_relation;");
            builder.append("DELETE FROM room_fair;");
            builder.append("DELETE FROM accomodation;");

            Statement statement = connection.createStatement();
            statement = connection.createStatement();
            statement.execute(builder.toString());
        }
    }

    public static void dropDB() throws BookingDbException, SQLException {
        BookingDb tdb = new BookingDb();
        try(Connection connection = tdb.connectToPostgreSQL()) {
            Statement statement = connection.createStatement();
            statement.execute("DROP DATABASE booking;");
        }
    }
}
