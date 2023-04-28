package ca.jrvs.apps.trading.dao;


import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;


@Repository
public class SecurityOrderDao extends JdbcCrudDao<SecurityOrder>{
    private static final Logger logger = LoggerFactory.getLogger(SecurityOrderDao.class);
    private final String TABLE_NAME = "security_order";
    private final String ID_COLUMN = "id";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;



    @Autowired
    public SecurityOrderDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME).usingGeneratedKeyColumns(ID_COLUMN);

    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    public SimpleJdbcInsert getSimpleJdbcInsert() {
        return simpleInsert;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String getIdColumnName() {
        return ID_COLUMN;
    }

    @Override
    Class<SecurityOrder> getEntityClass() {
        return SecurityOrder.class;
    }

    public Optional<SecurityOrder> findByAccountId(Integer integer) {
        SecurityOrder securityOrder;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE account_id =?";
        try{
            RowMapper<SecurityOrder> rowMapper = BeanPropertyRowMapper.newInstance(SecurityOrder.class);
            securityOrder = this.jdbcTemplate.queryForObject(selectSql,rowMapper,integer);
            return Optional.of(securityOrder);
        }catch (EmptyResultDataAccessException e){
            logger.debug("Can't find account by trader id" + integer, e);
        }
        return Optional.empty();
    }
    public void deleteByAccountId(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + getTableName()+ " WHERE account_id =?";
        getJdbcTemplate().update(deleteSql,id);

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
