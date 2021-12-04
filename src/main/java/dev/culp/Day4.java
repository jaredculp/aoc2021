package dev.culp;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public final class Day4 extends Puzzle<String> {

  Day4() {
    super(4, line -> line);
  }

  static final class Game {

    final List<Integer> numbers;
    final List<Board> boards;
    final Set<Integer> winners;

    Game(List<Integer> numbers, List<Board> boards) {
      this.numbers = numbers;
      this.boards = boards;
      this.winners = new LinkedHashSet<>();
    }

    void play() {
      numbers.forEach(
          n ->
              boards.forEach(
                  b -> {
                    b.play(n);
                    if (b.winner) {
                      winners.add(b.score);
                    }
                  }));
    }

    int getFirstWinner() {
      return winners.stream().reduce((a, b) -> a).get();
    }

    int getLastWinner() {
      return winners.stream().reduce((a, b) -> b).get();
    }
  }

  static final class Board {
    private final List<Integer> numbers;
    private boolean winner;
    private int score;
    private final Set<Integer> playedPositions;

    Board(List<Integer> numbers) {
      this.numbers = numbers;
      this.winner = false;
      this.score = numbers.stream().mapToInt(x -> x).sum();
      this.playedPositions = new HashSet<>();
    }

    private static final List<List<Integer>> WINNERS =
        List.of(
            // horizontals
            List.of(0, 1, 2, 3, 4),
            List.of(5, 6, 7, 8, 9),
            List.of(10, 11, 12, 13, 14),
            List.of(15, 16, 17, 18, 19),
            List.of(20, 21, 22, 23, 24),

            // verticals
            List.of(0, 5, 10, 15, 20),
            List.of(1, 6, 11, 16, 21),
            List.of(2, 7, 12, 17, 22),
            List.of(3, 8, 13, 18, 23),
            List.of(4, 9, 14, 19, 24));

    void play(int number) {
      if (winner) {
        return;
      }

      final var idx = numbers.indexOf(number);
      if (idx != -1) {
        playedPositions.add(idx);
        score -= number;
      }

      if (WINNERS.stream().anyMatch(playedPositions::containsAll)) {
        winner = true;
        score *= number;
      }
    }
  }

  private static Game createGame(List<String> input) {
    final var numbers =
        Arrays.stream(input.get(0).split(",")).map(Integer::parseInt).collect(toList());

    final var counter = new AtomicInteger();
    final var boards =
        input.stream()
            .skip(1)
            .filter(line -> !line.isBlank())
            .flatMap(line -> Arrays.stream(line.split("\\s+")))
            .filter(num -> !num.isBlank())
            .map(Integer::parseInt)
            .collect(groupingBy(it -> counter.getAndIncrement() / 25))
            .values()
            .stream()
            .map(Board::new)
            .collect(toList());

    return new Game(numbers, boards);
  }

  @Override
  public int part1(List<String> input) {
    final var game = createGame(input);
    game.play();
    return game.getFirstWinner();
  }

  @Override
  public int part2(List<String> input) {
    final var game = createGame(input);
    game.play();
    return game.getLastWinner();
  }
}
