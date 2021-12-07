package dev.culp;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public final class Day7 extends Puzzle<String> {

  Day7() {
    super(7, line -> line);
  }

  private static List<Integer> parse(List<String> input) {
    return input.stream()
        .flatMap(line -> Arrays.stream(line.split(",")))
        .map(Integer::parseInt)
        .sorted()
        .collect(toList());
  }

  @Override
  public long part1(List<String> input) {
    final var nums = parse(input);
    final var target = nums.get(nums.size() / 2);

    return nums.stream()
        .reduce(
            0,
            (sum, crab) -> {
              sum += Math.abs(crab - target);
              return sum;
            },
            Integer::sum);
  }

  @Override
  public long part2(List<String> input) {
    final var nums = parse(input);

    final var max = nums.stream().mapToInt(x -> x).max().getAsInt();
    var best = Integer.MAX_VALUE;
    for (int i = 0; i < max; i++) {
      var fuel = 0;
      for (int num : nums) {
        final var distance = Math.abs(num - i);
        fuel += (distance * (distance + 1) / 2);
      }
      if (fuel < best) {
        best = fuel;
      }
    }

    return best;
  }
}
