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
import java.util.ArrayList;
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

    /**
     * Update quote table against IEX source
     * - get all qutoes from the db
     * - foreach ticker get iexQuote
     * - convert iexQuote to quote entity
     * - persist the quote to db
     *
     *
     */

    public void updateMarketData(){
        Iterable<Quote> quotes = findAllQuotes();
        List<String> tickers = new ArrayList<>();
        quotes.forEach(quote -> tickers.add(quote.getTicker()));
        List<Quote> quoteList = saveQuotes(tickers);
        quoteDao.saveAll(quoteList);
    }

    /**
     * Helper method. Map a IexQuote to a Quote entity.
     */
    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setId(iexQuote.getSymbol());
        quote.setLastPrice(
                iexQuote.getLatestPrice() == null ? Double.valueOf(0.0)
                        : iexQuote.getLatestPrice());
        quote.setBidPrice(iexQuote.getIexBidPrice() == null ? Double.valueOf(0.0) :
                iexQuote.getIexBidPrice());
        quote.setBidSize(iexQuote.getIexBidSize() == null ? Integer.valueOf(0) :
                iexQuote.getIexBidSize());
        quote.setAskPrice(iexQuote.getIexAskPrice() == null ? Double.valueOf(0.0) :
                iexQuote.getIexAskPrice());
        quote.setAskSize(iexQuote.getIexAskSize() == null ? Integer.valueOf(0) :
                iexQuote.getIexAskSize());
        return quote;
    }

    /**
     * Validate (against IEX) and save given tickers to quote table.
     * - Get iexQuote(s)
     * - convert each iexQute to Quote entity
     * - persiste the quote to db
     * @param tickers
     * @return quoteList
     */
    public List<Quote> saveQuotes(List<String> tickers){
        List<IexQuote> iexQuotes = marketDataDao.findAllById(tickers);
        List<Quote> quoteList = new ArrayList<>();
        for (IexQuote iexQuote : iexQuotes){
            quoteList.add(buildQuoteFromIexQuote(iexQuote));
        }
        return quoteList;
    }

    /**
     * Method that find a single IexQuote base on the input ticker and
     * save the IexQuote as quote.
     * @param ticker
     * @return
     */
    public Quote saveQuote(String ticker){
        IexQuote iexQuote = findIexQuoteByTicker(ticker);
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        return quoteDao.save(quote);
    }

    /**
     * Update a given quote to quote table without validation
     * @param quote entity
     */
    public Quote saveQuote(Quote quote){ return quoteDao.save(quote);}

    /**
     * Find all quotes from the quote table
     */
    public List<Quote> findAllQuotes(){ return (List<Quote>) quoteDao.findAll();}

}
