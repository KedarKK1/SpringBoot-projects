package com.example.myWebFluxSSEApp.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.lang.model.element.Element;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.myWebFluxSSEApp.Data.ReactiveDataUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;

// @RestController - 

@Controller
@RequestMapping("/flux")
public class FluxController {
	
		private final ReactiveDataUtil reactiveDataUtil;
		
		public FluxController(ReactiveDataUtil reactiveDataUtil) {
	        this.reactiveDataUtil = reactiveDataUtil;
	    }

		
		// this path will call an request indefinitely until we close the connection, on restarting the request, numbers start continuying from that stopped number
		@GetMapping(value="/numbers", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		@ResponseBody
		public Flux<Integer> getCountin(){
			return Flux.<Integer>generate(element -> element.next(reactiveDataUtil.i++))
					.delayElements(Duration.ofMillis(500));
		}
		
		@GetMapping(value="/quotes", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		@ResponseBody
		public Flux<String> getQuotes(){
			return Flux.<String>generate(quote -> quote.next(reactiveDataUtil.quotesMap.get(reactiveDataUtil.j++ % reactiveDataUtil.quotesMap.size() )))
					.delayElements(Duration.ofMillis(450));
		} 
		
		// this path will call an request indefinitely each time to get one books from bookMap list until we close the connection
		@GetMapping(value="/books", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		@ResponseBody
		public Flux<String> getBooks(){
			return Flux.<String>generate(book -> book.next(reactiveDataUtil.booksMap.get(reactiveDataUtil.k++ % reactiveDataUtil.booksMap.size() )))
					.delayElements(Duration.ofSeconds(1));
		} 

		// this path will call an request indefinitely for getting local dateTime until we close the connection
		@GetMapping(value="/date", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		@ResponseBody
		public Flux<LocalDateTime> getLocalDateTime(){
			return Flux.<LocalDateTime>generate(quote -> quote.next(LocalDateTime.now() ))
					.delayElements(Duration.ofSeconds(1));
		} 
		
		// this path will call an request indefinitely for getting random numbers until we close the connection
		@GetMapping(value="/random", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		@ResponseBody
		public Flux<Double> getRandomNumber(){
			return Flux.<Double>generate(n -> n.next(Math.random() * 100 )).delayElements(Duration.ofMillis(300));
		} 
		
		// filtering and transforming elements using operators like map, filter, and flatMap.
		@GetMapping("/filteredQuotes")
		@ResponseBody
		public Flux<String> getFilteredQuotes() {
		    return Flux.fromIterable(reactiveDataUtil.quotesMap.values())
//		    	.doOnNext(quote -> System.out.println("Original: " + quote))
		    	.filter(quote -> quote.length() > 50)
//		    	.doOnNext(filteredQuote -> System.out.println("Filtered: " + filteredQuote))
		        .map(quote -> quote.toUpperCase() + " <br /> ")
//		        .doOnNext(upperCaseQuote -> System.out.println("Upper Case: " + upperCaseQuote)) // make sure it have ; at the ending
		        .delayElements(Duration.ofMillis(450));
		}
		
		// Combining Multiple Flux Streams using operators like concat, merge, and zip.
		@GetMapping("/combinedStreams")
		@ResponseBody
		public Flux<String> getCombinedFlux() {
			Flux<String> quotesFlux = Flux.fromIterable(reactiveDataUtil.quotesMap.values());
			Flux<String> booksFlux = Flux.fromIterable(reactiveDataUtil.booksMap.values());
			
			return Flux.<String>concat(quotesFlux, booksFlux)
//					.doOnNext(item -> System.out.println("" + item))
					.map(Element -> Element + " <br /> ")
					.delayElements(Duration.ofSeconds(1));
			
//		    Flux<String> combinedFlux = Flux.concat(quotesFlux, booksFlux)
//		            .delayElements(Duration.ofSeconds(1))
//		            .collectList()
//		            .flatMapMany(list -> Flux.just(String.join("\n", list)));

//		    return combinedFlux;
		}
		
		// error handling using the onErrorResume and onErrorReturn operators.
		@GetMapping("/errorHandling")
		@ResponseBody
		public Flux<String> getWithErrorHandling() {
		    Flux<String> fluxWithErrors = Flux.generate(quote -> {
		        if (Math.random() < 0.5) {
		            quote.next(reactiveDataUtil.quotesMap.get(reactiveDataUtil.j++ % reactiveDataUtil.quotesMap.size()));
		        } else {
		            quote.error(new RuntimeException("Random error occurred."));
		        }
		    });

		    return fluxWithErrors
		        .onErrorResume(error -> Flux.just("Error occurred: " + error.getMessage()))
		        .delayElements(Duration.ofSeconds(2));
		}
		
		// simulate backpressure by controlling the rate at which data is emitted using the limitRate operator
		@GetMapping("/backpressureHandling")
		@ResponseBody
		public Flux<String> getWithBackpressure() {
			return Flux.<String>generate(quote -> quote.next(reactiveDataUtil.quotesMap.get(reactiveDataUtil.j++ % reactiveDataUtil.quotesMap.size() )))
					.map(Element -> Element + " <br /> ")
					.delayElements(Duration.ofMillis(400))
//					.delayElements(Duration.ofMillis(300))
					.limitRate(2);
		}

		
};