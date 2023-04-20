package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class PositionDao implements CrudRepository<Position,Integer> {

    private static final String VIEW_NAME = "position";
    private static final String ID_COLUMN_NAME = "ticker";
    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Position> findById(Integer integer) {
        Position position;
        String selectSql = "SELECT * FROM " + VIEW_NAME + " WHERE " +
                ID_COLUMN_NAME + "=?";
        try{
            RowMapper<Position> rowMapper = BeanPropertyRowMapper.newInstance(Position.class);
            position = this.jdbcTemplate.queryForObject(selectSql,rowMapper,integer);
            return Optional.of(position);
        }catch (EmptyResultDataAccessException e){
            logger.debug("Can't find position by ticker" + integer, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return findById(integer).isPresent();
    }

    @Override
    public Iterable<Position> findAll() {
        String selectSql = "SELECT * FROM " + VIEW_NAME;
        RowMapper<Position> rowMapper = BeanPropertyRowMapper.newInstance(Position.class);
        Iterable<Position> positions = this.jdbcTemplate.query(selectSql,rowMapper);
        return positions;
    }

    @Override
    public long count() {
        String selectSql = "SELECT count(*) FROM " + VIEW_NAME;
        Long positionLong = this.jdbcTemplate.queryForObject(selectSql,Long.class);

        if (positionLong == null){
            throw new DataRetrievalFailureException("Count all position fail");
        }
        return positionLong;
    }

    @Override
    public <S extends Position> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends Position> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<Position> findAllById(Iterable<Integer> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(Integer integer) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(Position position) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Position> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
