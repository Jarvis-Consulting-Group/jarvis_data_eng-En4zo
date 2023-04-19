package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JdbcCrudDao <T extends Entity<Integer>> implements CrudRepository<T,Integer> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcCrudDao.class);

    abstract public JdbcTemplate getJdbcTemplate();
    abstract public SimpleJdbcInsert getSimpleJdbcInsert();
    abstract public String getTableName();
    abstract public String getIdColumnName();
    abstract Class<T> getEntityClass();

    /**
     * Save an entity and update auto-generated integer ID
     * @param entity to be saved
     * @return save entity
     * @param <S>
     */
    @Override
    public <S extends T> S save(S entity) {
        if (existsById(entity.getId())){
            if (updateOne(entity) != 1){
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        }else {
            addOne(entity);
        }
        return entity;
    }
    private <S extends T> void addOne(S entity){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        Number newId = getSimpleJdbcInsert().executeAndReturnKey(parameterSource);
        entity.setId(newId.intValue());
    }
    abstract public int updateOne(T entity);

    @Override
    public Optional<T> findById(Integer id) {
        Optional<T> entity = Optional.empty();
        String selectSql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " =?";
        try{
            entity = Optional.ofNullable((T) getJdbcTemplate().queryForObject(selectSql,
                    BeanPropertyRowMapper.newInstance(getEntityClass()),id));
        }catch (IncorrectResultSizeDataAccessException e){
            logger.debug("Can't find trader id:" + id ,e);
        }
        return entity;
    }

    @Override
    public boolean existsById(Integer id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<T> findAll() {
        String selectSql = "SELECT * FROM "+ getTableName();

        Iterable<T> iterable = (Iterable<T>) getJdbcTemplate().queryForObject(
                selectSql, BeanPropertyRowMapper.newInstance(getEntityClass()));
        return iterable;
    }

    @Override
    public Iterable<T> findAllById(Iterable<Integer> ids) {
        List<T> iterables = new ArrayList<>();
        ids.forEach(id -> iterables.add(findById(id).get()));
        return iterables;
    }

    @Override
    public long count() {
        String selectSql = "SELECT count(*) FROM " + getTableName();
        Long countNum = getJdbcTemplate().queryForObject(selectSql, Long.class);
        if (countNum == null){
            throw new DataRetrievalFailureException("Count all quote fail");
        }
        return countNum;
    }

    @Override
    public void deleteById(Integer id) {
        if (id == null){
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSql = "DELETE FROM " + getTableName()+ " WHERE "+ getIdColumnName() + " =?";
        getJdbcTemplate().update(deleteSql,id);

    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM "+ getTableName();
        getJdbcTemplate().update(deleteAllSql);
    }
}
