package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.configaration.SessionFactoryConfig;
import lk.ijse.dao.custom.RoomDAO;
import lk.ijse.entity.Room;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {

    Session session = SessionFactoryConfig.getInstance().getSession();

    @Override
    public boolean save(Room dto) {
        try{
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
    public boolean update(Room dto) {
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
            Room room = session.get(Room.class, id);
            session.delete(room);
            transaction.commit();
            return true;
        }catch (Exception exception){
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Room> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Room> query = criteriaBuilder.createQuery(Room.class);
            query.from(Room.class);
            List<Room> resultList = session.createQuery(query).getResultList();
            transaction.commit();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    @Override
    public Room getItem(String id) {
        try {
            Transaction transaction = session.beginTransaction();
            Room room = session.get(Room.class, id);
            transaction.commit();
            return room;
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
            String newId = "RM000";
            Transaction transaction = session.beginTransaction();
            List list = session.createNativeQuery("select room_id from room order by room_id desc limit 1").list();
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
