package dev.culp;

import static java.util.stream.Collectors.toList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public final class Day1 implements Puzzle {

  private final List<Integer> example = readFile("day1-example.txt");
  private final List<Integer> input = readFile("day1.txt");

  public int part1Example() {
    return countIncreases(example);
  }

  public int part1() {
    return countIncreases(input);
  }

  public int part2Example() {
    return countIncreases(windowSum(example, 3));
  }

  public int part2() {
    return countIncreases(windowSum(input, 3));
  }

  private static List<Integer> readFile(String filename) {
    try {
      return Files.lines(Paths.get(Day1.class.getClassLoader().getResource(filename).toURI()))
          .map(Integer::parseInt)
          .collect(toList());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static int countIncreases(List<Integer> in) {
    return IntStream.range(0, in.size() - 1).map(i -> in.get(i) < in.get(i + 1) ? 1 : 0).sum();
  }

  private static List<Integer> windowSum(List<Integer> in, int size) {
    return IntStream.rangeClosed(0, in.size() - size)
        .mapToObj(i -> in.subList(i, i + size))
        .map(list -> list.stream().reduce(0, (a, b) -> a + b))
        .collect(toList());
  }
}
