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
  void runAll(Puzzle<?> puzzle, long example1, long example2) {
    final var result = puzzle.solve();

    assertEquals(example1, result.part1Example());
    System.out.println(puzzle.getClass().getSimpleName() + " part1: " + result.part1());

    assertEquals(example2, result.part2Example());
    System.out.println(puzzle.getClass().getSimpleName() + " part2: " + result.part2());
  }

  static Stream<Arguments> puzzles() {
    return Stream.of(
        arguments(new Day1(), 7L, 5L),
        arguments(new Day2(), 150L, 900L),
        arguments(new Day3(), 198L, 230L),
        arguments(new Day4(), 4512L, 1924L),
        arguments(new Day5(), 5L, 12L),
        arguments(new Day6(), 5934L, 26984457539L),
        arguments(new Day7(), 37L, 168L));
  }
}
