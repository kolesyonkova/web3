package test;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

public class DataBase {
    private static final String PERSISTENCE_UNIT_NAME = "data";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;

    public List<Point> loadEntries() {
        return entityManager.createQuery("SELECT e FROM test.Point e").getResultList();
    }

    public void addEntryToDB(Point point) throws Exception {
        userTransaction.begin();
        entityManager.persist(point);
        userTransaction.commit();
    }

    public void clearDB() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        userTransaction.begin();
        entityManager.createQuery("DELETE from test.Point").executeUpdate();
        userTransaction.commit();
    }
}
