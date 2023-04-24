package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import net.bytebuddy.build.Plugin;
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
public class AccountDao extends JdbcCrudDao<Account>{
    private static final Logger logger = LoggerFactory.getLogger(AccountDao.class);

    private final String TABLE_NAME = "account";
    private final String ID_COLUMN = "id";
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleInsert;
    @Autowired
    public AccountDao(DataSource dataSource){
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
    Class<Account> getEntityClass() {
        return Account.class;
    }

    @Override
    public int updateOne(Account entity) {
        String updateSql = "UPDATE " + TABLE_NAME + " SET amount=? WHERE " + getIdColumnName() +"=?";
        Object[] values = new Object[]{
                entity.getAmount(),
                entity.getId()
        };
        return jdbcTemplate.update(updateSql,values);
    }

    @Override
    public void delete(Account account) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Account> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }
    public Optional<Account> findByTraderId(Integer integer) {
        Account account;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE trader_id =?";
        try{
            RowMapper<Account> rowMapper = BeanPropertyRowMapper.newInstance(Account.class);
            account = this.jdbcTemplate.queryForObject(selectSql,rowMapper,integer);
            return Optional.of(account);
        }catch (EmptyResultDataAccessException e){
            logger.debug("Can't find account by trader id" + integer, e);
        }
        return Optional.empty();
    }
}
