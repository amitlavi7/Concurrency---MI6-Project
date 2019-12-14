package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future<String> future;
    @BeforeEach
    public void setUp(){
        future = new Future<>();
    }

    @Test
    public void test(){
        assertNull(future);
        assertFalse(future.isDone());
        future.resolve("hello");
        assertEquals("hello", future.get());
        assertTrue(future.isDone());
        future.resolve("goodbye");
        assertEquals("goodbye", future.get(3, TimeUnit.SECONDS));
    }
}
