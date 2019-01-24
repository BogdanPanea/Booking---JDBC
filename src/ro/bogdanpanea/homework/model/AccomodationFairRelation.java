package ro.bogdanpanea.homework.model;

public class AccomodationFairRelation {

    private int id;
    private int idAccomodation;
    private int idRoomFair;

    public void setId(int id) {
        this.id = id;
    }

    public void setIdAccomodation(int idAccomodation) {
        this.idAccomodation = idAccomodation;
    }

    public void setIdRoomFair(int idRoomFair) {
        this.idRoomFair = idRoomFair;
    }

    public int getId() {
        return id;
    }

    public int getIdAccomodation() {
        return idAccomodation;
    }

    public int getIdRoomFair() {
        return idRoomFair;
    }

    @Override
    public String toString() {
        return "AccomodationFairRelation{" +
                "id=" + id +
                ", idAccomodation=" + idAccomodation +
                ", idRoomFair=" + idRoomFair +
                '}';
    }
}
