import org.junit.*;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationFairRelationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLRoomFairDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;
import ro.bogdanpanea.homework.model.AccomodationFairRelation;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class InsertSelectPrintTest {

    private BookingDb db;
    private SQLAccomodationDAO sqlAccomodationDAO;
    private SQLRoomFairDAO sqlRoomFairDAO;
    private SQLAccomodationFairRelationDAO sqlAccomodationFairRelationDAO;
    private int counter;

    @BeforeClass
    public static void initTests() throws BookingDbException, SQLException {
        BookingDb.setUpDB();
    }

    @AfterClass
    public static void discardTests() throws BookingDbException, SQLException {
        BookingDb.dropDB();
    }

    @Before
    public void setUp() {
        db = new BookingDb();
    }

    @After
    public void tearDown() throws BookingDbException, SQLException {
        db.dropDataFromTables();
    }

    @Test
    public void whenNewLinesInsertedIntoDB_getReturnsThem() throws BookingDbException, SQLException {

        List<String> insertedObjectList = new ArrayList<>();
        List<String> objectsFromDB = new ArrayList<>();


        insertTestData(insertedObjectList);
        selectDataFromDB(objectsFromDB);

        assertEquals(insertedObjectList, objectsFromDB);

    }

    private void selectDataFromDB(List<String> objectsFromDB) throws SQLException {
        SQLAccomodationDAO accomodationDAOTest = new SQLAccomodationDAO(db);
        SQLAccomodationFairRelationDAO sqlAccomodationFairRelationDAO1Test = new SQLAccomodationFairRelationDAO(db);
        SQLRoomFairDAO sqlRoomFairDAO1Test = new SQLRoomFairDAO(db);


        try (Connection conn = db.connect()) {
            ResultSet resultSet;
            PreparedStatement statement = conn.prepareStatement("SELECT * from accomodation a inner join accomodation_fair_relation afr on a.id = afr.id_accomodation inner join room_fair r on r.id = afr.id_room_fair;");
            resultSet = statement.executeQuery();

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {

                if (counter == 0) {
                    objectsFromDB.add(accomodationDAOTest.mapResultSetToAccomodation(resultSet).toString());
                    objectsFromDB.add(sqlRoomFairDAO1Test.mapResultSetToRoomFair(resultSet).toString());
                    objectsFromDB.add(sqlAccomodationFairRelationDAO1Test.mapResultSetToAccomodationFairRelation(resultSet).toString());
                }

                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + " - " + columnValue);
                }
                System.out.println(";");

                counter += 1;
            }
        } catch (BookingDbException e) {
            e.printStackTrace();
        }
    }

    private void insertTestData(List<String> insertedObjectList) throws BookingDbException, SQLException {
        SQLAccomodationDAO sqlAccomodationDAO = new SQLAccomodationDAO(db);
        Accomodation accomodation1 = new Accomodation();

        accomodation1.setType("Double room");
        accomodation1.setBedType("King size");
        accomodation1.setMaxGuests(2);
        accomodation1.setDescription("Only for those with money.");

        sqlAccomodationDAO.add(accomodation1);
        insertedObjectList.add(accomodation1.toString());


        SQLRoomFairDAO sqlRoomFairDAO = new SQLRoomFairDAO(new BookingDb());
        RoomFair roomFair1 = new RoomFair();
        RoomFair roomFair2 = new RoomFair();
        RoomFair roomFair3 = new RoomFair();

        roomFair1.setValue(5);
        roomFair1.setSeason("In season");
        sqlRoomFairDAO.add(roomFair1);
        insertedObjectList.add(roomFair1.toString());

        roomFair2.setValue(10);
        roomFair2.setSeason("Out of season");
        sqlRoomFairDAO.add(roomFair2);

        roomFair3.setValue(8);
        roomFair3.setSeason("Between season");
        sqlRoomFairDAO.add(roomFair3);

        SQLAccomodationFairRelationDAO sqlAccomodationFairRelationDAO = new SQLAccomodationFairRelationDAO(new BookingDb());
        AccomodationFairRelation accomodationFairRelation1 = new AccomodationFairRelation();
        AccomodationFairRelation accomodationFairRelation2 = new AccomodationFairRelation();
        AccomodationFairRelation accomodationFairRelation3 = new AccomodationFairRelation();

        accomodationFairRelation1.setIdAccomodation(sqlAccomodationDAO.getByAccomodationId(accomodation1).getId());
        accomodationFairRelation1.setIdRoomFair(sqlRoomFairDAO.getByRoomFairId(roomFair1).getId());

        accomodationFairRelation2.setIdAccomodation(sqlAccomodationDAO.getByAccomodationId(accomodation1).getId());
        accomodationFairRelation2.setIdRoomFair(sqlRoomFairDAO.getByRoomFairId(roomFair2).getId());

        accomodationFairRelation3.setIdAccomodation(sqlAccomodationDAO.getByAccomodationId(accomodation1).getId());
        accomodationFairRelation3.setIdRoomFair(sqlRoomFairDAO.getByRoomFairId(roomFair3).getId());

        sqlAccomodationFairRelationDAO.add(accomodationFairRelation1);
        sqlAccomodationFairRelationDAO.add(accomodationFairRelation2);
        sqlAccomodationFairRelationDAO.add(accomodationFairRelation3);

        insertedObjectList.add(accomodationFairRelation1.toString());
    }

}
