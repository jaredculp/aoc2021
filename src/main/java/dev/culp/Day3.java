package dev.culp;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

public final class Day3 extends Puzzle<String> {

  Day3() {
    super(3, line -> line);
  }

  @Override
  public int part1(List<String> input) {
    return IntStream.range(0, input.get(0).length()).mapToObj(x -> x)
        .reduce(new Calculator(), (results, i) -> {
          final var counts = input.stream().collect(groupingBy(x -> x.charAt(i), counting()));
          if (counts.get('0') > counts.get('1')) {
            results.gamma().append("0");
            results.epsilon().append("1");
          } else {
            results.gamma().append("1");
            results.epsilon().append("0");
          }
          return results;
        }, (a, b) -> a.combine(b)).value();
  }

  record Calculator(StringBuilder gamma, StringBuilder epsilon) {
    Calculator() {
      this(new StringBuilder(), new StringBuilder());
    }

    Calculator combine(Calculator other) {
      return new Calculator(
          new StringBuilder().append(gamma.toString()).append(other.gamma().toString()),
          new StringBuilder().append(epsilon.toString()).append(other.epsilon().toString()));
    }

    int value() {
      return binary(gamma.toString()) * binary(epsilon.toString());
    }
  }

  @Override
  public int part2(List<String> input) {
    final var oxygen = filter(input, 1, 0).get(0);
    final var co2 = filter(input, 0, 0).get(0);

    return binary(oxygen) * binary(co2);
  }

  private static int binary(String a) {
    return Integer.parseInt(a, 2);
  }

  private static List<String> filter(List<String> input, int keep, int position) {
    if (input.size() == 1) {
      return input;
    }

    var zeros = 0;
    var ones = 0;
    for (int i = 0; i < input.size(); i++) {
      if (input.get(i).charAt(position) == '0') {
        zeros++;
      } else {
        ones++;
      }
    }

    // oxygen, keep most common, resolve ties with 1
    final char prefix;
    if (keep == 1) {
      prefix = zeros > ones ? '0' : '1';
      // co2, keep least common, resolve ties with 0
    } else {
      prefix = zeros > ones ? '1' : '0';
    }

    final var filtered = input.stream().filter(x -> x.charAt(position) == prefix).collect(toList());
    return filter(filtered, keep, position + 1);
  }
}
