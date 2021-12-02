package dev.culp;

import static java.util.stream.Collectors.toList;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class Day2 implements Puzzle {

  private final List<Command> example = readFile("day2-example.txt");
  private final List<Command> input = readFile("day2.txt");

  public int part1Example() {
    return runCommands(example);
  }

  public int part1() {
    return runCommands(input);
  }

  public int part2Example() {
    return runAimCommands(example);
  }

  public int part2() {
    return runAimCommands(input);
  }

  private static List<Command> readFile(String filename) {
    try {
      return Files.lines(Paths.get(Day1.class.getClassLoader().getResource(filename).toURI()))
          .map(
              line -> {
                final var parts = line.split(" ");
                return new Command(parts[0], Integer.parseInt(parts[1]));
              })
          .collect(toList());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
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
