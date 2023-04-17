package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
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
        String update_sql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, " +
                "ask_price=?, ask_size=? WHERE ticker=?";
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
        String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, " +
                "ask_price=?, ask_size=? WHERE ticker=?";
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
        String selectSql = "SELECT * FROM quote WHERE ticker=?";
        try{
            quote = this.jdbcTemplate.queryForObject(selectSql, Quote.class ,s);
            return Optional.of(quote);
        }catch (EmptyResultDataAccessException e){
            logger.debug("Can't find quote by ticker" + s, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Quote> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void deleteAll() {

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
