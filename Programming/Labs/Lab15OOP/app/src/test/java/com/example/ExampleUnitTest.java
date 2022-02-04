package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testTrue() {
        assertEquals(true, true);
    }

    @Test
    public void divideZeroIsNotCorrect() {assertEquals(4/0, null);}


}