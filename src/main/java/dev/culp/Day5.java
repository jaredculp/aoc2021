package dev.culp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Day5 extends Puzzle<Day5.Line> {

  Day5() {
    super(
        5,
        line -> {
          final var pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
          final var matcher = pattern.matcher(line);
          matcher.find();

          // matcher.group(0) is the whole line!
          final var a =
              new Point(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
          final var b =
              new Point(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)));
          return new Line(a, b);
        });
  }

  record Point(int x, int y) {}

  record Line(Point a, Point b) {}

  @Override
  public long part1(List<Line> input) {
    return solve(input, false);
  }

  @Override
  public long part2(List<Line> input) {
    return solve(input, true);
  }

  private static int solve(List<Line> input, boolean includeDiagonal) {
    return (int)
        input.stream()
            .reduce(
                new HashMap<Point, Integer>(),
                (results, line) -> {
                  // horizontal line
                  final Stream<Point> markings;
                  if (line.a().y() == line.b().y()) {
                    final int y = line.a().y();
                    final int start = Math.min(line.a().x(), line.b().x());
                    final int end = Math.max(line.a().x(), line.b().x());

                    markings = IntStream.rangeClosed(start, end).mapToObj(x -> new Point(x, y));
                  }
                  // vertical line
                  else if (line.a().x() == line.b().x()) {
                    final int x = line.a().x();
                    final int start = Math.min(line.a().y(), line.b().y());
                    final int end = Math.max(line.a().y(), line.b().y());

                    markings = IntStream.rangeClosed(start, end).mapToObj(y -> new Point(x, y));

                  }
                  // diagonal line (only 45deg)
                  else if (includeDiagonal) {
                    final var vals = new ArrayList<Point>();

                    final int dx = line.b().x() - line.a().x() > 0 ? 1 : -1;
                    final int dy = line.b().y() - line.a().y() > 0 ? 1 : -1;

                    var x = line.a().x();
                    var y = line.a().y();
                    vals.add(new Point(x, y));
                    while (x != line.b().x()) {
                      x += dx;
                      y += dy;
                      vals.add(new Point(x, y));
                    }

                    markings = vals.stream();
                  } else {
                    markings = Stream.of();
                  }

                  markings.forEach(x -> results.merge(x, 1, Integer::sum));
                  return results;
                },
                (a, b) -> {
                  a.putAll(b);
                  return a;
                })
            .values()
            .stream()
            .filter(val -> val >= 2)
            .count();
  }
}
