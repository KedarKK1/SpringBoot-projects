package com.example.myWebFluxSSEApp.Data;

import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReactiveDataUtil {

    public int i = 0, j = 0, k = 0;

    public Map<Integer, String> quotesMap = new ConcurrentHashMap<Integer, String>() {
        {
            put(0, "You only live once, but if you do it right, once is enough. — Mae West");
            put(1, "If you want to live a happy life, tie it to a goal, not to people or things. – Albert Einstein");
            put(2, "Turn your wounds into wisdom. — Oprah Winfrey");
            put(3, "Life is ten percent what happens to you and ninety percent how you respond to it. — Charles Swindoll");
            put(4, "If you cannot do great things, do small things in a great way. — Napoleon Hill");
            put(5, "Life’s tragedy is that we get old too soon and wise too late. — Benjamin Franklin");
        }
    };

    public Map<Integer, String> booksMap = new ConcurrentHashMap<Integer, String>() {
        {
            put(0, "Mahabharat — Maharshi Vyasa");
            put(1, "Ramayan – Maharshi Walmiki");
            put(2, "Don Quixote — Miguel De Cervantes");
            put(3, "The Hound of the Baskervilles — Arthur Conan Doyle");
            put(4, "To Kill a Mockingbird — Harper Lee");
        }
    };
    
    // synchronized keyword in Java is used to create synchronized methods or blocks, which ensure that only one thread can execute the synchronized code at a time. This helps in preventing concurrent access to shared resources, which can lead to data inconsistencies and unexpected behavior.
    public synchronized int getNextBookIndex() {
        return k++ % booksMap.size();
    }

}
