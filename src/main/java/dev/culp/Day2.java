package dev.culp;

import java.util.List;
import java.util.function.Function;

public final class Day2 implements Puzzle {

  @Override
  public Result solve() {
    final Function<String, Command> mappingFn =
        line -> {
          final var parts = line.split(" ");
          return new Command(parts[0], Integer.parseInt(parts[1]));
        };
    final var inputs = readFile(2, mappingFn);

    final var part1Example = runCommands(inputs.example());
    final var part1 = runCommands(inputs.input());

    final var part2Example = runAimCommands(inputs.example());
    final var part2 = runAimCommands(inputs.input());

    return new Result(part1Example, part1, part2Example, part2);
  }

  private static int runCommands(List<Command> commands) {
    return commands.stream()
        .reduce(
            new Coordinates(0, 0),
            (coords, command) -> {
              var newX = coords.x;
              var newY = coords.y;

              if (command.op().equals("forward")) {
                newX += command.val();
              } else if (command.op().equals("down")) {
                newY += command.val();
              } else if (command.op().equals("up")) {
                newY -= command.val();
              }

              return new Coordinates(newX, newY);
            },
            (a, b) -> a.combine(b))
        .calculate();
  }

  private static int runAimCommands(List<Command> commands) {
    return commands.stream()
        .reduce(
            new AimCoordinates(0, 0, 0),
            (coords, command) -> {
              var newX = coords.x;
              var newY = coords.y;
              var newAim = coords.aim;

              if (command.op().equals("down")) {
                newAim += command.val();
              } else if (command.op().equals("up")) {
                newAim -= command.val();
              } else if (command.op().equals("forward")) {
                newX += command.val();
                newY += newAim * command.val();
              }

              return new AimCoordinates(newX, newY, newAim);
            },
            (a, b) -> a.combine(b))
        .calculate();
  }

  record Command(String op, int val) {}

  record Coordinates(int x, int y) {
    Coordinates combine(Coordinates other) {
      return new Coordinates(x + other.x, y + other.y);
    }

    int calculate() {
      return x * y;
    }
  }

  record AimCoordinates(int x, int y, int aim) {
    AimCoordinates combine(AimCoordinates other) {
      return new AimCoordinates(x + other.x, y + other.y, aim + other.aim);
    }

    int calculate() {
      return x * y;
    }
  }
}
