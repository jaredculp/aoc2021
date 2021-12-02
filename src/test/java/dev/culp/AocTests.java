package dev.culp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class AocTests {

  @Test
  void day1() {
    final Puzzle puzzle = new Day1();

    assertEquals(7, puzzle.part1Example());
    System.out.println(puzzle.part1());

    assertEquals(5, puzzle.part2Example());
    System.out.println(puzzle.part2());
  }

  @Test
  void day2() {
    final Puzzle puzzle = new Day2();

    assertEquals(150, puzzle.part1Example());
    System.out.println(puzzle.part1());

    assertEquals(900, puzzle.part2Example());
    System.out.println(puzzle.part2());
  }
}
