package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao){
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Find an IexQuote
     *
     * @param ticker id
     * @return IexQuote object
     * @throws IllegalArgumentException if ticker is invalid
     */
    public IexQuote findIexQuoteByTicker(String ticker){
        return marketDataDao.findById(ticker)
                .orElseThrow(() -> new IllegalArgumentException(ticker + "is invalid"));
    }

//
//    public void updateMarketData(){}
//
//    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){}
//
//    public List<Quote> saveQuotes(List<String> tickers){}
//
//    public Quote saveQuote(String ticker){}
//
//    public Quote saveQuote(Quote quote){ return quoteDao.save(quote);}
//
//    public List<Quote> findAllQuotes(){ return (List<Quote>) quoteDao.findAll();}

}
