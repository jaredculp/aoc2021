package dev.culp;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day1Tests {
  final Puzzle puzzle = new Day1();

  @Test
  void part1() {
    assertEquals(7, puzzle.part1Example());
    System.out.println(puzzle.part1());
  }

  @Test
  void part2() {
    assertEquals(5, puzzle.part2Example());
    System.out.println(puzzle.part2());
  }
}
