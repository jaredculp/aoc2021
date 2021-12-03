package dev.culp;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Day3 extends Puzzle<String> {

  Day3() {
    super(3, line -> line);
  }

  @Override
  public int part1(List<String> input) {
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

    return IntStream.range(0, input.get(0).length())
        .mapToObj(x -> x)
        .reduce(
            new Calculator(),
            (results, i) -> {
              final var counts = input.stream().collect(groupingBy(x -> x.charAt(i), counting()));
              if (counts.get('0') > counts.get('1')) {
                results.gamma().append("0");
                results.epsilon().append("1");
              } else {
                results.gamma().append("1");
                results.epsilon().append("0");
              }
              return results;
            },
            (a, b) -> a.combine(b))
        .value();
  }

  @Override
  public int part2(List<String> input) {
    enum Measurement {
      O2,
      CO2
    };
    record Iteration(List<String> input, Measurement measurement, int i) {}

    final Function<Measurement, String> calculate =
        (measurement) ->
            Stream.iterate(
                    new Iteration(input, measurement, 0),
                    itr -> {
                      final var partitioned =
                          itr.input().stream().collect(groupingBy(x -> x.charAt(itr.i())));
                      final var ones = partitioned.get('1');
                      final var zeros = partitioned.get('0');

                      final List<String> nextInput;
                      if (itr.measurement() == Measurement.O2) {
                        nextInput = zeros.size() > ones.size() ? zeros : ones;
                      } else {
                        nextInput = zeros.size() > ones.size() ? ones : zeros;
                      }
                      return new Iteration(nextInput, itr.measurement(), itr.i() + 1);
                    })
                .filter(itr -> itr.input().size() == 1)
                .findFirst()
                .orElseThrow()
                .input()
                .get(0);

    final var o2 = calculate.apply(Measurement.O2);
    final var co2 = calculate.apply(Measurement.CO2);

    return binary(o2) * binary(co2);
  }

  private static int binary(String a) {
    return Integer.parseInt(a, 2);
  }
}
