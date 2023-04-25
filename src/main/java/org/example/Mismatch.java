package org.example;

import java.lang.foreign.MemorySegment;
import java.util.Arrays;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Mismatch {
    public static void main( String[] args ) {
        notABug();
        bug();
        arraysBehavior();
    }

    public static void notABug() {
        var bob = MemorySegment.ofArray("bob".getBytes(UTF_8));
        var rob = MemorySegment.ofArray("rob".getBytes(UTF_8));
        long m = MemorySegment.mismatch(bob, 0, 3,
                                        rob, 0, 3);
        System.out.printf("rob != bob in distinct MemorySegment (expects 0): %d\n", m);
    }

    public static void bug() {
        var bobrob = MemorySegment.ofArray("bobrob".getBytes(UTF_8));
        long m = MemorySegment.mismatch(bobrob, 0, 3,
                                        bobrob, 3, 6); // wrong -1 return
        System.out.printf("rob != bob in SAME MemorySegment (expects 0): %d\n", m);
    }

    public static void arraysBehavior() {
        var bobrob = "bobrob".getBytes(UTF_8);
        int m = Arrays.mismatch(bobrob, 0, 3,
                                bobrob, 3, 6);
        System.out.printf("rob != bob in same array (expects 0): %d\n", m);
    }

}
