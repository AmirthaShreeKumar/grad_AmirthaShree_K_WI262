package com.example;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class ITest {

    @Test
    void testMethodCall() {

        I obj = mock(I.class);

        obj.abc();
        obj.abc();

        verify(obj, times(2)).abc();
    }
}