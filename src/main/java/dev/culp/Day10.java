package dev.culp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public final class Day10 extends Puzzle<String> {

  Day10() {
    super(10, line -> line);
  }

  private static final Map<Character, Character> PAIRS =
      Map.of('(', ')', '[', ']', '{', '}', '<', '>');

  @Override
  public long part1(List<String> input) {
    var score = 0;
    for (String s : input) {
      final var stack = new Stack<Character>();
      for (char c : s.toCharArray()) {
        if (PAIRS.containsKey(c)) {
          stack.push(c);
        } else if (PAIRS.get(stack.peek()) != c) {
          score +=
              switch (c) {
                case ')' -> 3;
                case ']' -> 57;
                case '}' -> 1197;
                case '>' -> 25137;
                default -> 0;
              };
          break;
        } else {
          stack.pop();
        }
      }
    }
    return score;
  }

  @Override
  public long part2(List<String> input) {
    final var scores = new ArrayList<Long>();
    for (String s : input) {
      final var stack = new Stack<Character>();
      for (char c : s.toCharArray()) {
        if (PAIRS.containsKey(c)) {
          stack.push(c);
        } else if (PAIRS.get(stack.peek()) != c) {
          stack.clear();
          break;
        } else {
          stack.pop();
        }
      }

      var score = 0L;
      while (!stack.isEmpty()) {
        score *= 5L;
        score +=
            switch (stack.pop()) {
              case '(' -> 1L;
              case '[' -> 2L;
              case '{' -> 3L;
              case '<' -> 4L;
              default -> 0L;
            };
      }
      if (score > 0) scores.add(score);
    }

    Collections.sort(scores);
    return scores.get(scores.size() / 2);
  }
}
