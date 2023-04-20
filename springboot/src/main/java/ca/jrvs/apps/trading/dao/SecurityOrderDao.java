package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder>{
    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
    private final String TABLE_NAME = "securityorder";
    private final String ID_COLUMN = "id";


    @Override
    public JdbcTemplate getJdbcTemplate() {
        return null;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getIdColumnName() {
        return null;
    }

    @Override
    Class<SecurityOrder> getEntityClass() {
        return null;
    }

    @Override
    public int updateOne(SecurityOrder entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(SecurityOrder securityOrder) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends SecurityOrder> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
