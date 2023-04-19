package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(value = "quote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Controller
@RequestMapping("/quote")
public class QuoteController {

    private QuoteService quoteService;
    @Autowired
    public QuoteController(QuoteService quoteService){ this.quoteService = quoteService; }

    //@ApiOperation(value = "Show iexQuote", notes = "Show iexQuote for a given ticker/symbol")
    //@ApiResponses(value = {@ApiResponse(code=404, message = "ticker is not found")})
    @GetMapping(path = "/iex/ticker/{ticker}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public IexQuote getQuote(@PathVariable String ticker){
        try{
            return quoteService.findIexQuoteByTicker(ticker);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    /**
     * Get /quote/dailyList Show the dailyList
     */
    @GetMapping(path = "/dailyList")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> getDailyList(){
        try{
            return quoteService.findAllQuotes();
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    /**
     * POST /quote/tickerId/ Add a new ticker to the dailyList
     */
    @PostMapping(path = "/tickerId/{tickerId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Quote createQuote(@PathVariable String tickerId){
        try{
            return quoteService.saveQuote(tickerId);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    /**
     * PUT /quote/ Update a given quote in the quote table
     *
     */
    @PutMapping(path = "/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Quote putQuote(@RequestBody Quote quote){
        try{
            return quoteService.saveQuote(quote);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }

    /**
     * Put /quote/iexMarketData Update quote table using iex data
     */
    @PutMapping(path = "/iexMarketData")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Quote> updateMarketData(){
        try{
            return quoteService.updateMarketData();
        } catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }
    }
}
