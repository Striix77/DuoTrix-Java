package main;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScoreManager {

    //manages the scores of all previous players
    private static final File file = new File("res/scores.txt");

    public static void saveScoreToFile(String winner, String loser, int score1, int score2) {

        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(winner + " won with " + score1 + " points, " + loser + " lost with " + score2 + " points!\n");

            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadScoreFromFile(JTextArea textArea) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("res/scores.txt"));

            Set<String> namesSet = br.lines().map(line -> line.split("\\s+"))
                    .flatMap(array -> Stream.of(array[0], array[5])).collect(Collectors.toSet());
            br.close();
            List<String> names = new ArrayList<>(namesSet);
            names = names.reversed();
            br = new BufferedReader(new FileReader("res/scores.txt"));
            String line;
            while ((line = br.readLine()) != null) {

                String name1 = Arrays.stream(line.split("\\s+")).limit(1).findFirst().get();
                String name2 = Arrays.stream(line.split("\\s+")).skip(5).limit(1).findFirst().get();
                String score1 = Arrays.stream(line.split("\\s+")).skip(3).limit(1).findFirst().get();
                String score2 = Arrays.stream(line.split("\\s+")).skip(8).limit(1).findFirst().get();

                if(names.contains(name1)) {
                    names.set(names.indexOf(name1), name1 + " " + score1);
                }
                else{
                    names.add(name1 + " " + score1);
                }
                if(names.contains(name2)) {
                    names.set(names.indexOf(name2), name2 + " " + score2);
                }
                else{
                    names.add( name2 + " " + score2);
                }

            }


            textArea.setText(names.stream().collect(Collectors.joining("\n")));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
