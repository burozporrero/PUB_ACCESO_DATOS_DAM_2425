package es.cheste.UD0.peces_linterna;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Advent2021_06 {

    private static final String input = "1,2,4,5,5,5,2,1,3,1,4,3,2,1,5,5,1,2,3,4,4,1,2,3,2,1,4,4,1,5,5,1,3,4,4,4,1,2,2,5,1,5,5,3,2,3,1,1,3,5,1,1,2,4,2,3,1,1,2,1,3,1,2,1,1,2,1,2,2,1,1,1,1,5,4,5,2,1,3,2,4,1,1,3,4,1,4,1,5,1,4,1,5,3,2,3,2,2,4,4,3,3,4,3,4,4,3,4,5,1,2,5,2,1,5,5,1,3,4,2,2,4,2,2,1,3,2,5,5,1,3,3,4,3,5,3,5,5,4,5,1,1,4,1,4,5,1,1,1,4,1,1,4,2,1,4,1,3,4,4,3,1,2,2,4,3,3,2,2,2,3,5,5,2,3,1,5,1,1,1,1,3,1,4,1,4,1,2,5,3,2,4,4,1,3,1,1,1,3,4,4,1,1,2,1,4,3,4,2,2,3,2,4,3,1,5,1,3,1,4,5,5,3,5,1,3,5,5,4,2,3,2,4,1,3,2,2,2,1,3,4,2,5,2,5,3,5,5,1,1,1,2,2,3,1,4,4,4,5,4,5,5,1,4,5,5,4,1,1,5,3,3,1,4,1,3,1,1,4,1,5,2,3,2,3,1,2,2,2,1,1,5,1,4,5,2,4,2,2,3";

    public static long run(int days) {

        Map<Integer, Long> fishTable = getTempFishTable();
        // Con expresiones lambda
        // List<Integer> fish = input.chars().filter(c -> (char) c != ',').mapToObj(n -> Integer.parseInt(Character
        // .toString((char) n))).collect(Collectors.toList());

        List<Integer> fish = getListIntFromString(input);

        for(int num : fish) {
            fishTable.put(num, fishTable.get(num) + 1);
        }

        for(int i = 0; i < days; i++) {
            Map<Integer, Long> tempFishTable = getTempFishTable();
            for(Map.Entry<Integer, Long> e : fishTable.entrySet()) {
                if(e.getKey() == 0 && e.getValue() > 0) {
                    tempFishTable.put(8, e.getValue());
                    tempFishTable.put(6, e.getValue());
                } else if (e.getKey() > 0 && e.getValue() > 0) {
                    tempFishTable.put(e.getKey() - 1, tempFishTable.get(e.getKey() - 1) + e.getValue());
                }
            }

            fishTable = tempFishTable;

        }

        return fishTable.entrySet().stream().map(e -> e.getValue()).mapToLong(e -> e).sum();
    }

    private static Map<Integer, Long> getTempFishTable() {
        Map<Integer, Long> tempFishTable = new HashMap<>();
        for(int i = 0; i <= 8; i++) {
            tempFishTable.put(i, 0L);
        }
        return tempFishTable;
    }

    private static List<Integer> getListIntFromString(String input) {
        List<Integer> intList = new ArrayList<>();
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) != ',') {
                intList.add(Integer.parseInt(Character.toString(input.charAt(i))));
            }
        }
        return intList;
    }

    public static void main(String[] args) {
        System.out.println(run(80));
        System.out.println(run(256));
    }
}
