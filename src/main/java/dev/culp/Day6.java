package dev.culp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public final class Day6 extends Puzzle<String> {

  Day6() {
    super(6, line -> line);
  }

  @Override
  public long part1(List<String> input) {
    return simulate(input, 80);
  }

  @Override
  public long part2(List<String> input) {
    return simulate(input, 256);
  }

  private static long[] asLongs(List<String> input) {
    return Arrays.stream(input.get(0).split(","))
        .map(Long::parseLong)
        .reduce(
            new long[9],
            (fish, n) -> {
              fish[n.intValue()]++;
              return fish;
            },
            Day6::combine);
  }

  private static long[] combine(long[] a, long[] b) {
    final var c = new long[a.length];
    for (int i = 0; i < a.length; i++) {
      c[i] = a[i] + b[i];
    }
    return c;
  }

  private static long simulate(List<String> input, int days) {
    final var output =
        IntStream.iterate(0, i -> i + 1)
            .boxed()
            .limit(days)
            .reduce(
                asLongs(input),
                (fish, x) -> {
                  final var newFish = fish[0];
                  System.arraycopy(fish, 1, fish, 0, fish.length - 1);

                  // new fish
                  fish[6] += newFish;
                  fish[8] = newFish;

                  return fish;
                },
                Day6::combine);

    return Arrays.stream(output).sum();
  }
}
