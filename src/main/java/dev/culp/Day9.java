package dev.culp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public final class Day9 extends Puzzle<String> {

  Day9() {
    super(9, line -> line);
  }

  @Override
  public long part1(List<String> input) {
    final var map = map(input);

    final var lowPoints = new ArrayList<Point>();
    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[y].length; x++) {
        final var point = new Point(x, y, map[y][x]);

        if (point.neighbors(map).stream().allMatch(p -> p.val() > point.val())) {
          lowPoints.add(point);
        }
      }
    }

    return lowPoints.stream().mapToInt(x -> x.val() + 1).sum();
  }

  private static int[][] map(List<String> input) {
    final int[][] map = new int[input.size()][input.get(0).length()];

    for (int i = 0; i < input.size(); i++) {
      final var line = input.get(i);
      for (int j = 0; j < line.length(); j++) {
        map[i][j] = Integer.valueOf(String.valueOf(line.charAt(j)));
      }
    }
    return map;
  }

  record Point(int x, int y, int val) {
    Set<Point> neighbors(int[][] map) {
      final var points = new HashSet<Point>();
      // left
      if (x > 0) {
        final var xx = x - 1;
        final var yy = y;
        points.add(new Point(xx, yy, map[yy][xx]));
      }

      // top
      if (y > 0) {
        final var xx = x;
        final var yy = y - 1;
        points.add(new Point(xx, yy, map[yy][xx]));
      }

      // right
      if (x + 1 < map[y].length) {
        final var xx = x + 1;
        final var yy = y;
        points.add(new Point(xx, yy, map[yy][xx]));
      }

      // bottom
      if (y + 1 < map.length) {
        final var xx = x;
        final var yy = y + 1;
        points.add(new Point(xx, yy, map[yy][xx]));
      }

      return points;
    }
  }

  @Override
  public long part2(List<String> input) {
    final var map = map(input);

    final var seen = new HashSet<Point>();
    final var basins = new ArrayList<Integer>();

    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[y].length; x++) {
        final var stack = new Stack<Point>();
        stack.add(new Point(x, y, map[y][x]));
        int size = 0;
        while (!stack.isEmpty()) {
          final var p = stack.pop();

          if (seen.contains(p) || p.val() == 9) {
            continue;
          }
          seen.add(p);
          size++;

          for (Point p2 : p.neighbors(map)) {
            stack.add(p2);
          }
        }
        basins.add(size);
      }
    }

    return basins.stream().sorted(Comparator.reverseOrder()).limit(3).reduce(1, (a, b) -> a * b);
  }
}
