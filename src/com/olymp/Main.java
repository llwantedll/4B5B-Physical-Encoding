package com.olymp;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        PhysicalLayer physicalLayer = new PhysicalLayer();
        boolean cond = true;System.out.println("4B5B coding method");
        while (cond) {
            System.out.println("Choose the action:");
            System.out.println("1. Coding");
            System.out.println("2. Decoding");
            System.out.println("0. Exit");
            Scanner sc = new Scanner(System.in);
            int entered = sc.nextInt();
            String s;
            switch (entered) {
                case 1:
                    System.out.println("Enter the byte sequence to encode");
                    s = sc.next();
                    System.out.println("Result bits = " + physicalLayer.encode(s));

                    break;
                case 2:
                    System.out.println("Enter the bit sequence to decode");
                    s = physicalLayer.fiveB4B(sc.next());
                    System.out.print("Result = ");
                    Arrays.stream(s.split("(?<=\\G.{8})")).forEach(tt -> System.out.print((char) Integer.parseInt(tt, 2)));
                    System.out.println();
                    break;
                default:
                    cond = false;
                    break;
            }
            System.out.println();
        }
    }
}

