package dev.culp;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public final class Day1 implements Puzzle {

  @Override
  public Result solve() {
    final Function<String, Integer> mappingFn = Integer::parseInt;
    final var inputs = readFile(1, mappingFn);

    final var part1Example = countIncreases(inputs.example());
    final var part1 = countIncreases(inputs.input());

    final var part2Example = countIncreases(windowSum(inputs.example(), 3));
    final var part2 = countIncreases(windowSum(inputs.input(), 3));

    return new Result(part1Example, part1, part2Example, part2);
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
