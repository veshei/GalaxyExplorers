package hw04;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by veronicashei on 11/26/16.
 */
public class Solution {

  public Solution() {
  }

  public static void main(String[] args) {
    List<String> strings = readStrings();
    Integer[] explorers = explorers(strings);
    Integer[] galaxies = galaxies(strings);
    List<Integer> galaxyHelp = galaxyHelp(explorers, galaxies);
    TreeMap<Integer, ArrayList<Integer>> matching = matching(strings, explorers);
    Integer [][] bpGraph = bpGraph(galaxyHelp, explorers, galaxies, matching);
    Map<Integer, Integer> maxBPM = maxBPM(bpGraph, explorers, galaxyHelp);
    //Map<Integer, Integer> answer = answer(maxBPM);
    String output = printOutput(maxBPM, bpGraph);
    System.out.println(output);
  }

  private static List<String> readStrings() {
    List<String> myStrings = new ArrayList<String>();
    Scanner scanner = new Scanner(System.in);
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      myStrings.add(line + "\n");
    }
    return myStrings;
  }

  private static Integer[] explorers (List<String> input) {
    List<Integer> temp = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      String s = new String(input.get(i));
      Matcher matcher = Pattern.compile("\\d+").matcher(s);
      matcher.find();
      temp.add(Integer.valueOf(matcher.group()));
    }
    Integer [] explorers = temp.toArray(new Integer[temp.size()]);
    return explorers;
  }

  private static Integer[] galaxies(List<String> input) {
    List<Integer> temp = new ArrayList<>();
    String s = "";
    for (int i = 0; i < input.size(); i++) {
      s = input.get(i);
      Pattern p = Pattern.compile("[0-9]+");
      Matcher matcher = p.matcher(s);
      while (matcher.find()) {
        temp.add(Integer.valueOf(matcher.group()));
      }
    }
    Integer[] galaxies = temp.toArray(new Integer[temp.size()]);
    return galaxies;
  }

  private static List<Integer> galaxyHelp(Integer[] explorer, Integer[] galaxy) {
    List<Integer> result = new ArrayList<>();
    for (int r = 0; r < explorer.length; r++) {
      for (int i = 0; i < galaxy.length; i++) {
        if (explorer[r] != galaxy[i]) {
          if (galaxy[i] < 110) {
            Integer temp = galaxy[i] % 10;
            result.add(temp);
          } else {
            Integer temp = galaxy[i] % 100;
            result.add(temp);
          }
        }
      }
    }
    return result;
  }

  private static TreeMap<Integer, ArrayList<Integer>> matching(List<String> input, Integer[] explorer) {
    TreeMap<Integer, ArrayList<Integer>> result = new TreeMap<Integer, ArrayList<Integer>>();
    String s = "";
    for (int i = 0; i < input.size(); i++) {
      s = input.get(i).substring(2);
      Pattern p = Pattern.compile("[0-9]+");
      Matcher matcher = p.matcher(s);
      ArrayList<Integer> temp = new ArrayList<>();
      while(matcher.find()) {
        temp.add(Integer.valueOf(matcher.group()));
        result.put(explorer[i], temp);
      }
    }
    return result;
  }

  private static Integer[][] bpGraph(List<Integer> col, Integer[] explorer, Integer[] galaxy,
                                     TreeMap<Integer, ArrayList<Integer>> matching) {
    Integer m = explorer.length;
    Integer n = Collections.max(col);
    Integer[][] graph = new Integer[m][n];
    for (int a = 0; a < graph.length; a++) {
      for (int b = 0; b < graph[a].length; b++) {
        graph[a][b] = 0;
      }
    }
    for (int i = 0; i < m; i++) {
      for (int p = 0; p < matching.get(i + 1).size(); p++) {
        if (matching.get(i + 1).get(p) < 110) {
          Integer temp = matching.get(i + 1).get(p) % 10;
          graph[i][temp - 1] = matching.get(i + 1).get(p);
        }
        else {
          Integer temp = matching.get(i + 1).get(p) % 100;
          graph[i][temp - 1] = matching.get(i + 1).get(p);
        }
      }
    }
    return graph;
  }

  private static boolean bpm (Integer bpGraph[][], int u, boolean seen[], Integer matchR[],
                              Integer[] explorer, List<Integer> col, Map<Integer, Integer> answer) {
    Integer m = explorer.length;
    Integer n = Collections.max(col);
    for (int v = 0; v < n; v++) {
      if (bpGraph[u][v] != 0 && !seen[v]) {
        seen[v] = true;
        if (matchR[v] < 0 || bpm(bpGraph, matchR[v], seen, matchR, explorer, col, answer)) {
          matchR[v] = u;
          answer.put(matchR[v], v);
          //System.out.println(u);
          //System.out.println(v);

          return true;
        }
      }
    }
    return false;
  }

  private static Map<Integer, Integer> maxBPM(Integer bpGraph[][], Integer[] explorer, List<Integer> col) {
    Integer m = explorer.length;
    Integer n = Collections.max(col);
    Integer matchR[] = new Integer[n];
    Map<Integer, Integer> answer = new TreeMap<>();
    for (int i = 0; i < n; ++i) {
      matchR[i] = -1;
    }
    Integer result = 0;
    for (int u = 0; u < m; u++) {
      boolean seen[] = new boolean[n];
      for (int i = 0; i < n; ++i) {
        seen[i] = false;
      }
      if (bpm(bpGraph, u, seen, matchR, explorer, col, answer)) {
        result++;
      }
    }
    return answer;
  }

  private static String printOutput(Map<Integer, Integer> maxBPM,
                                                   Integer[][] bpGraph) {
    String output = "";
    Map<Integer, Integer> assignment = new TreeMap<>();
    for (int i = 0; i < maxBPM.size(); i++) {
      assignment.put(i + 1, bpGraph[i][maxBPM.get(i)]);
    }
    for (int r = 0; r < assignment.size(); r++) {
      output = output + (r + 1) + "," + assignment.get(r + 1) + "\n";
    }
    return output;
  }
}
