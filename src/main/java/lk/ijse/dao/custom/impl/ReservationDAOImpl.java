package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.configaration.SessionFactoryConfig;
import lk.ijse.dao.custom.ReservationDAO;
import lk.ijse.entity.Reservation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {

    Session session = SessionFactoryConfig.getInstance().getSession();

    @Override
    public boolean save(Reservation dto) {
        try {
            Transaction transaction = session.beginTransaction();
            Serializable save = session.save(dto);
            transaction.commit();
            return save != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }

    }

    @Override
    public boolean update(Reservation dto) {

        try {
            Transaction transaction = session.beginTransaction();
            session.update(dto);
            transaction.commit();
            return true;
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            return false;
        }finally {
            session.close();
        }

    }

    @Override
    public boolean delete(String id) {
        try {
            Transaction transaction = session.beginTransaction();
            Reservation reservation = session.get(Reservation.class, id);
            session.delete(reservation);
            transaction.commit();
            return true;
        }catch (Exception exception){
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Reservation> getAll() {
        return null;
    }

    @Override
    public Reservation getItem(String id) {
        try {
            Transaction transaction = session.beginTransaction();
            Reservation reservation = session.get(Reservation.class, id);
            transaction.commit();
            return reservation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public String getNextId() {
        try {
            String newId = "RES000";
            Transaction transaction = session.beginTransaction();
            List list = session.createNativeQuery("select res_id from reservation order by res_id desc limit 1").list();
            if (!list.isEmpty()) newId = (String) list.get(0);
            transaction.commit();
            session.close();
            return newId;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        }
    }
}
