package lk.ijse.dao.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.configaration.SessionFactoryConfig;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    Session session = SessionFactoryConfig.getInstance().getSession();

    @Override
    public boolean save(Student dto) {
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
    public boolean update(Student dto) {
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
            Student item = session.get(Student.class, id);
            session.delete(item);
            transaction.commit();
            return true;
        }catch (Exception exception){
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public List<Student> getAll() {
        try {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
            query.from(Student.class);
            List<Student> resultList = session.createQuery(query).getResultList();
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
    public Student getItem(String id) {
        try {
            Transaction transaction = session.beginTransaction();
            Student item = session.get(Student.class, id);
            transaction.commit();
            return item;
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
            String newId = "ST000";
            Transaction transaction = session.beginTransaction();
            List list = session.createNativeQuery("select student_id from student order by student_id desc limit 1").list();
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
