package dev.culp;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

public final class Day1 extends Puzzle<Integer> {

  Day1() {
    super(1, Integer::parseInt);
  }

  @Override
  public int part1(List<Integer> input) {
    return countIncreases(input);
  }

  @Override
  public int part2(List<Integer> input) {
    final var size = 3;
    final var windows =
        IntStream.rangeClosed(0, input.size() - size)
            .mapToObj(i -> input.subList(i, i + size))
            .map(list -> list.stream().reduce(0, (a, b) -> a + b))
            .collect(toList());

    return countIncreases(windows);
  }

  private static int countIncreases(List<Integer> in) {
    return IntStream.range(0, in.size() - 1).map(i -> in.get(i) < in.get(i + 1) ? 1 : 0).sum();
  }
}
