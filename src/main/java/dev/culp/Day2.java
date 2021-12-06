package dev.culp;

import java.util.List;

public final class Day2 extends Puzzle<Day2.Command> {

  Day2() {
    super(
        2,
        line -> {
          final var parts = line.split(" ");
          return new Command(parts[0], Integer.parseInt(parts[1]));
        });
  }

  @Override
  public long part1(List<Command> input) {
    return input.stream()
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
            Coordinates::combine)
        .calculate();
  }

  @Override
  public long part2(List<Command> input) {
    return input.stream()
        .reduce(
            new AimCoordinates(0, 0, 0),
            (coords, command) -> {
              var newX = coords.x;
              var newY = coords.y;
              var newAim = coords.aim;

              switch (command.op()) {
                case "down" -> newAim += command.val();
                case "up" -> newAim -= command.val();
                case "forward" -> {
                  newX += command.val();
                  newY += newAim * command.val();
                }
              }

              return new AimCoordinates(newX, newY, newAim);
            },
            AimCoordinates::combine)
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
