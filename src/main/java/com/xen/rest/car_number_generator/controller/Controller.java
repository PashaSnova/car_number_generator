package com.xen.rest.car_number_generator.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping(value = "/number", produces = "text/plain;charset=UTF-8")
public class Controller {

    private static final String END = " 116 RUS";

    private Set<String> numbers = new HashSet<>();

    private String tmp = "";

    private static char[] possibleLetters = new char[]{'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У',  'Х'};



    @GetMapping("/random")
    public String random() {
        Random r = new Random();
        int num = r.nextInt(1000);
        String s;

        do {
            s =
            possibleLetters[r.nextInt(possibleLetters.length)]
                    + String.format("%03d", num)
                    + possibleLetters[r.nextInt(possibleLetters.length)]
                    + possibleLetters[r.nextInt(possibleLetters.length)]
                    + END;
        } while (numbers.contains(s));
        numbers.add(s);
        tmp = s;
        return s;
    }

    @GetMapping("/next")
    public String next() {
        if (numbers.size() != 12 * 12 * 12 * 1000) {
            int n = Integer.parseInt(tmp.substring(1, 4));
            StringBuilder s;
            String result;
            do {

                n = (n + 1) % 1000;
                s = new StringBuilder(tmp.charAt(0) + tmp.substring(4, 6));

                result = String.format("%03d", n);

                if (n == 0) {

                    int j = 2;
                    int index;
                    do {
                        index = Arrays.binarySearch(possibleLetters, s.charAt(j));
                        index = (index + 1) % 12;
                        s.setCharAt(j, possibleLetters[index]);
                        j--;
                        System.out.println(s);
                    } while (index == 0 && j >= 0);

                }
                tmp = s.charAt(0) + result + s.substring(1, 3) + END;
            } while (numbers.contains((s.charAt(0) + result + s.substring(1, 3) + END)));
            numbers.add(s.charAt(0) + result + s.substring(1, 3) + END);
            return s.charAt(0) + result + s.substring(1, 3) + END;
        }
        return "Номера закончились";
    }


}


