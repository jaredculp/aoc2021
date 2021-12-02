package dev.culp;

import static java.util.stream.Collectors.toList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

public interface Puzzle {
  record Result(int part1Example, int part1, int part2Example, int part2) {}

  Result solve();

  record Inputs<T>(List<T> example, List<T> input) {}

  default <T> Inputs<T> readFile(int day, Function<String, T> mappingFn) {
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
