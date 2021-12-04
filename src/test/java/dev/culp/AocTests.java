package dev.culp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AocTests {

  @ParameterizedTest
  @MethodSource("puzzles")
  void runAll(Puzzle<?> puzzle, int example1, int example2) {
    final var result = puzzle.solve();

    assertEquals(example1, result.part1Example());
    System.out.println(puzzle.getClass().getSimpleName() + " part1: " + result.part1());

    assertEquals(example2, result.part2Example());
    System.out.println(puzzle.getClass().getSimpleName() + " part2: " + result.part2());
  }

  static Stream<Arguments> puzzles() {
    return Stream.of(
        arguments(new Day1(), 7, 5),
        arguments(new Day2(), 150, 900),
        arguments(new Day3(), 198, 230),
        arguments(new Day4(), 4512, 1924));
  }
}
