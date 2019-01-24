package ro.bogdanpanea.homework;

import ro.bogdanpanea.homework.dao.RoomFairDAO;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLAccomodationFairRelationDAO;
import ro.bogdanpanea.homework.dao.sql.SQLRoomFairDAO;
import ro.bogdanpanea.homework.db.BookingDb;
import ro.bogdanpanea.homework.db.BookingDbException;
import ro.bogdanpanea.homework.model.Accomodation;
import ro.bogdanpanea.homework.model.AccomodationFairRelation;
import ro.bogdanpanea.homework.model.RoomFair;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            List<Accomodation> accomodations;
            List<RoomFair> roomFairs;
            List<AccomodationFairRelation> accomodationFairRelations;
            BookingDb.setUpDB();

            List<List<Object>>  objectList = new ArrayList<>();

            SQLAccomodationDAO sqlAccomodationDAO = new SQLAccomodationDAO(new BookingDb());
            Accomodation accomodation1 = new Accomodation();

            accomodation1.setType("Double room");
            accomodation1.setBedType("King size");
            accomodation1.setMaxGuests(2);
            accomodation1.setDescription("Only for those with money.");

            sqlAccomodationDAO.add(accomodation1);
            Accomodation accomodationTest;
            accomodationTest = sqlAccomodationDAO.getByAccomodationId(accomodation1);
            System.out.println(accomodationTest.toString());


            System.out.println("----------------------------------------------------------------------");


            SQLRoomFairDAO sqlRoomFairDAO = new SQLRoomFairDAO(new BookingDb());
            RoomFair roomFair1 = new RoomFair();
            RoomFair roomFair2 = new RoomFair();
            RoomFair roomFair3 = new RoomFair();

            roomFair1.setValue(5);
            roomFair1.setSeason("In season");
            sqlRoomFairDAO.add(roomFair1);

            roomFair2.setValue(10);
            roomFair2.setSeason("In season");
            sqlRoomFairDAO.add(roomFair2);

            roomFair3.setValue(8);
            roomFair3.setSeason("Between season");
            sqlRoomFairDAO.add(roomFair3);

            roomFairs = sqlRoomFairDAO.getAll();

            for (RoomFair c : roomFairs) {
                System.out.println(c.toString());
            }

            System.out.println("----------------------------------------------------------------------");

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

            accomodationFairRelations = sqlAccomodationFairRelationDAO.getAll();

            for (AccomodationFairRelation c : accomodationFairRelations) {
                System.out.println(c.toString());
            }


//            for (Accomodation c : accomodations) {
//                System.out.println(c.toString());
//            }
//
//            BookingDb.dropDB();

        } catch (BookingDbException | Exception e) {
            e.printStackTrace();
        }

    }
}
