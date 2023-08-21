package com.example.myWebFluxSSEApp.Controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.myWebFluxSSEApp.Data.ReactiveDataUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/mono")
public class MonoController {
	private final ReactiveDataUtil reactiveDataUtil;

    public MonoController(ReactiveDataUtil reactiveDataUtil) {
        this.reactiveDataUtil = reactiveDataUtil;
    }
    
	@GetMapping(value="/")
	@ResponseBody
	public String helloWorld(){
		return "Hello World!";
	} 
	
	@GetMapping(value="/all")
	@ResponseBody
	public Mono<String> all(){
		return Mono.<String>just("all2");
	} 
	
	// this path will call an request indefinitely for getting local dateTime until we close the connection
	@GetMapping(value="/date")
	@ResponseBody
	public Mono<LocalDateTime> getMonoLocalDateTime(){
		return Mono.just( LocalDateTime.now() ) ;
	} 
	
	// this path will call an request indefinitely each time to get one books from bookMap list until we close the connection
	@GetMapping(value="/book", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseBody
	public Mono<String> getBooks(){
		int nextBookIndex = reactiveDataUtil.getNextBookIndex();
		String bookName = reactiveDataUtil.booksMap.get(nextBookIndex);
		return Mono.just(bookName);
		// 		Below method just wont work as k starts from 0 every time, so we use synchronized method nextBookIndex
		//		return Mono.<String>just(reactiveDataUtil.booksMap.get(reactiveDataUtil.k++ % reactiveDataUtil.booksMap.size() )) ;
	} 
	
	@GetMapping("/fluxToMono")
	@ResponseBody
	public Mono<String> getFirstQuote(){
		return Flux.fromIterable(reactiveDataUtil.quotesMap.values())
				.next()
				.delayElement(Duration.ofSeconds(2));
	}

}
