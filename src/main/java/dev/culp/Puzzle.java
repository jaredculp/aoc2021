package dev.culp;

import static java.util.stream.Collectors.toList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public abstract class Puzzle<T> {
  private final Inputs<T> inputs;

  Puzzle(int day, Function<String, T> mappingFn) {
    this.inputs = readFile(day, mappingFn);
  }

  record Inputs<T>(List<T> example, List<T> input) {}

  record Result(long part1Example, long part1, long part2Example, long part2) {}

  public Result solve() {
    final var part1Example = part1(inputs.example());
    final var part1 = part1(inputs.input());
    final var part2Example = part2(inputs.example());
    final var part2 = part2(inputs.input());
    return new Result(part1Example, part1, part2Example, part2);
  }

  abstract long part1(List<T> input);

  abstract long part2(List<T> input);

  private static <T> Inputs<T> readFile(int day, Function<String, T> mappingFn) {
    final Function<String, List<T>> read =
        f -> {
          try {
            return Files.lines(Paths.get(Puzzle.class.getClassLoader().getResource(f).toURI()))
                .map(mappingFn)
                .collect(toList());
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        };

    final var example = String.format("day%d-example.txt", day);
    final var input = String.format("day%d.txt", day);
    return new Inputs<>(read.apply(example), read.apply(input));
  }
}
