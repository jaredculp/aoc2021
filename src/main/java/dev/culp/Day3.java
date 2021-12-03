package dev.culp;

import static java.util.stream.Collectors.toList;

import java.util.List;

public final class Day3 extends Puzzle<String> {

  Day3() {
    super(3, line -> line);
  }

  @Override
  public int part1(List<String> input) {
    final var gamma = new StringBuilder();
    final var epsilon = new StringBuilder();

    for (int j = 0; j < input.get(0).length(); j++) {
      var zeros = 0;
      var ones = 0;
      for (int i = 0; i < input.size(); i++) {
        if (input.get(i).charAt(j) == '0') {
          zeros++;
        } else {
          ones++;
        }
      }

      if (zeros > ones) {
        gamma.append("0");
        epsilon.append("1");
      } else {
        gamma.append("1");
        epsilon.append("0");
      }
    }

    return calculate(gamma.toString(), epsilon.toString());
  }

  @Override
  public int part2(List<String> input) {
    final var oxygen = filter(input, 1, 0).get(0);
    final var co2 = filter(input, 0, 0).get(0);

    return calculate(oxygen, co2);
  }

  private static int calculate(String a, String b) {
    return Integer.parseInt(a, 2) * Integer.parseInt(b, 2);
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
