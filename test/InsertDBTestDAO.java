import org.junit.*;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationFairRelationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLRoomFairDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;
import ro.bogdanpanea.homework.model.AccomodationFairRelation;
import ro.bogdanpanea.homework.model.RoomFair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class InsertDBTestDAO {

    private BookingDb db;
    private SQLAccomodationDAO sqlAccomodationDAO;
    private SQLRoomFairDAO sqlRoomFairDAO;
    private SQLAccomodationFairRelationDAO sqlAccomodationFairRelationDAO;

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
        sqlAccomodationDAO = new SQLAccomodationDAO(db);
        sqlAccomodationFairRelationDAO = new SQLAccomodationFairRelationDAO(db);
        sqlRoomFairDAO = new SQLRoomFairDAO(db);
    }

    @After
    public void tearDown() throws BookingDbException, SQLException {
        db.dropDataFromTables();
    }

    @Test
    public void whenNewLinesInsertedIntoDB_getReturnsThem() throws BookingDbException, SQLException {

        List<Accomodation> accomodations = new ArrayList<>();
        List<RoomFair> roomFairs = new ArrayList<>();
        List<AccomodationFairRelation> accomodationFairRelations = new ArrayList<>();

        List<String> insertedObjectList = new ArrayList<>();
        List<String> objectsFromDB = new ArrayList<>();


        SQLAccomodationDAO sqlAccomodationDAO = new SQLAccomodationDAO(db);
        Accomodation accomodation1 = new Accomodation();

        accomodation1.setType("Double room");
        accomodation1.setBedType("King size");
        accomodation1.setMaxGuests(2);
        accomodation1.setDescription("Only for those with money.");

        sqlAccomodationDAO.add(accomodation1);

        accomodations.add(accomodation1);
        insertedObjectList.add(accomodations.toString());

        SQLRoomFairDAO sqlRoomFairDAO = new SQLRoomFairDAO(new BookingDb());
        RoomFair roomFair1 = new RoomFair();
        RoomFair roomFair2 = new RoomFair();
        RoomFair roomFair3 = new RoomFair();

        roomFair1.setValue(5);
        roomFair1.setSeason("In season");
        sqlRoomFairDAO.add(roomFair1);
        roomFairs.add(roomFair1);

        roomFair2.setValue(10);
        roomFair2.setSeason("In season");
        sqlRoomFairDAO.add(roomFair2);
        roomFairs.add(roomFair2);

        roomFair3.setValue(8);
        roomFair3.setSeason("Between season");
        sqlRoomFairDAO.add(roomFair3);
        roomFairs.add(roomFair3);

        insertedObjectList.add(roomFairs.toString());

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

        accomodationFairRelations.add(accomodationFairRelation1);
        accomodationFairRelations.add(accomodationFairRelation2);
        accomodationFairRelations.add(accomodationFairRelation3);

        insertedObjectList.add(accomodationFairRelations.toString());


        getDataFromDB(accomodations, roomFairs, accomodationFairRelations, objectsFromDB, sqlAccomodationDAO, accomodation1, sqlRoomFairDAO, sqlAccomodationFairRelationDAO);

        assertEquals(insertedObjectList.toString(), objectsFromDB.toString());

        System.out.println(insertedObjectList);
        System.out.println(objectsFromDB);
    }

    private void getDataFromDB(List<Accomodation> accomodations, List<RoomFair> roomFairs, List<AccomodationFairRelation> accomodationFairRelations, List<String> objectsFromDB, SQLAccomodationDAO sqlAccomodationDAO, Accomodation accomodation1, SQLRoomFairDAO sqlRoomFairDAO, SQLAccomodationFairRelationDAO sqlAccomodationFairRelationDAO) throws BookingDbException, SQLException {
        accomodations.removeAll(accomodations);
        roomFairs.removeAll(roomFairs);
        accomodationFairRelations.removeAll(accomodationFairRelations);


        Accomodation accomodationTest;
        accomodationTest = sqlAccomodationDAO.getByAccomodationId(accomodation1);
        accomodations.add(accomodationTest);
        objectsFromDB.add(accomodations.toString());

        roomFairs = sqlRoomFairDAO.getAll();
        objectsFromDB.add(roomFairs.toString());

        accomodationFairRelations = sqlAccomodationFairRelationDAO.getAll();
        objectsFromDB.add(accomodationFairRelations.toString());
    }

}
