package net.gcicom.cdr.processor.service;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.CompletionAwareAggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.gcicom.cdr.processor.entity.output.GCICDR;

@Component
public class CDRAggregator implements CompletionAwareAggregationStrategy  {

	private Logger logger = LoggerFactory.getLogger(CDRAggregator.class); 
	
	@Autowired
	private Auditor auditor;
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

		GCICDR cdr = newExchange.getIn().getBody(GCICDR.class);
		logger.debug("Aggregating " + cdr);

		ArrayList<GCICDR> cdrs = null;
	
		if (oldExchange == null) {
			
			cdrs = new ArrayList<GCICDR>();
			cdrs.add(cdr);
			newExchange.getIn().setBody(cdrs);
			return newExchange;
			
		} else {
			
			cdrs = oldExchange.getIn().getBody(ArrayList.class);
			cdrs.add(cdr);
			return oldExchange;
		}
	}
	
	@Override
	public void onCompletion(Exchange exchange) {

		logger.info("Aggregation completed");
		auditor.endEvent(exchange);
		
	}
}