package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {
    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;


    @Autowired
    public QuoteDao(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }



    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())){
            int updatedRowNo = updateOne(quote);
            if (updatedRowNo != 1){
                throw new DataRetrievalFailureException("Unable to update quote");
            }
        }else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * helper method that saves one quote
     * @param quote
     */
    private void addOne(Quote quote){
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row !=1){
            throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
        }
    }

    /**
     * helper method that updates one quote.
     * @param quote
     * @return
     */
    private int updateOne(Quote quote){
        String update_sql = "UPDATE " + TABLE_NAME + " SET last_price=?, bid_price=?, bid_size=?, " +
                "ask_price=?, ask_size=? WHERE " + ID_COLUMN_NAME + "=?";
        return jdbcTemplate.update(update_sql,makeUpdateValues(quote));
    }

    /**
     * helper method that makes sql update values objects
     * @param quote to be updated
     * @return UPDATE_SQL values
     */
    private Object[] makeUpdateValues(Quote quote){
        Object[] values = new Object[]{quote.getLastPrice(),quote.getBidPrice(),quote.getBidSize(),quote.getAskPrice()
        ,quote.getAskSize(),quote.getTicker()};
        return values;
    }

    @Override
    public <S extends Quote> List<S> saveAll(Iterable<S> quotes) {
        List<Quote> existQuote = new ArrayList<>();
        List<Quote> nonExistQuote = new ArrayList<>();
        int count = 0;
        for ( Quote quote : quotes){
            count ++;
            if (existsById(quote.getTicker())){
                existQuote.add(quote);
            }else {
                nonExistQuote.add(quote);
            }
        }
        int updateNo = updateAll(existQuote);
        int addNo = addAll(nonExistQuote);
        if (updateNo + addNo != count){
            throw new IncorrectResultSizeDataAccessException("Number of rows", count, updateNo+addNo);
        }
        return (List<S>) quotes;
    }

    private int updateAll(Iterable<Quote> quotes){
        String updateSql = "UPDATE " + TABLE_NAME + " SET last_price=?, bid_price=?, bid_size=?, " +
                "ask_price=?, ask_size=? WHERE " + ID_COLUMN_NAME + "=?";
        List<Object[]> batch = new ArrayList<>();
        quotes.forEach(quote -> {batch.add(makeUpdateValues(quote));});
        int[] rows = jdbcTemplate.batchUpdate(updateSql, batch);
        return Arrays.stream(rows).sum();
    }
    private int addAll(Iterable<Quote> quotes){
        AtomicInteger count = new AtomicInteger();
        quotes.forEach(quote -> {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
            int row = simpleJdbcInsert.execute(parameterSource);
            if (row !=1){
                throw new IncorrectResultSizeDataAccessException("Failed to insert", 1, row);
            }else {
                count.getAndIncrement();
            }});
        return count.get();
    }

    /**
     * find a quote by ticker
     * @param s
     * @return
     */
    @Override
    public Optional<Quote> findById(String s) {
        Quote quote;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + "=?";

        try{
            RowMapper<Quote> rowMapper = BeanPropertyRowMapper.newInstance(Quote.class);
            quote = this.jdbcTemplate.queryForObject(selectSql, rowMapper,s);
            return Optional.of(quote);
        }catch (EmptyResultDataAccessException e){
            logger.debug("Can't find quote by ticker" + s, e);
        }
        return Optional.empty();
    }
    @Override
    public Iterable<Quote> findAll() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        RowMapper<Quote> rowMapper = BeanPropertyRowMapper.newInstance(Quote.class);
        Iterable<Quote> quotes = this.jdbcTemplate.query(selectSql,rowMapper);
        return quotes;
    }

    @Override
    public boolean existsById(String s) {
        if (findById(s).equals(Optional.empty())){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public long count() {
        String selectSql = "SELECT count(*) FROM " + TABLE_NAME;
        Long quoteLong = this.jdbcTemplate.queryForObject(selectSql,Long.class);

        if (quoteLong == null){
            throw new DataRetrievalFailureException("Count all quote fail");
        }
        return quoteLong;

    }

    @Override
    public void deleteById(String s) {
        if (s == null){
            throw new IllegalArgumentException("ID can't be null");
        }
        String deleteSqp = "DELETE FROM quote WHERE ticker=?";
        this.jdbcTemplate.update(deleteSqp,s);
    }

    @Override
    public void deleteAll() {
        String deleteAllSql = "DELETE FROM "+ TABLE_NAME;
        this.jdbcTemplate.update(deleteAllSql);

    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");

    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");

    }


}
