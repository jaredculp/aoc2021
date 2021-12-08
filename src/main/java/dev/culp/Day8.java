package dev.culp;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Day8 extends Puzzle<String> {

  Day8() {
    super(8, line -> line);
  }

  @Override
  public long part1(List<String> input) {
    final var uniqueLengths = Set.of(2, 3, 4, 7);
    return input.stream()
        .flatMap(line -> Arrays.stream(line.split("\\|")[1].split(" ")).map(String::trim))
        .filter(segment -> uniqueLengths.contains(segment.length()))
        .count();
  }

  @Override
  public long part2(List<String> input) {
    return input.stream().map(Day8::solveLine).reduce(Long::sum).get();
  }

  private static long solveLine(String line) {
    final var segments =
        Arrays.stream(line.split("\\|")[0].split(" ")).map(String::trim).collect(toSet());

    String one, four, seven, eight;
    one = four = seven = eight = "";
    final var counts = new HashMap<Character, Integer>();

    for (String s : segments) {
      switch (s.length()) {
        case 2 -> one = s;
        case 3 -> seven = s;
        case 4 -> four = s;
        case 7 -> eight = s;
      }

      for (char c : s.toCharArray()) {
        final var count = counts.get(c);
        counts.put(c, count == null ? 1 : count + 1);
      }
    }

    final var mappings = new HashMap<Character, Character>();
    final var b = find(counts, 6);
    mappings.put('b', b);
    final var f = find(counts, 9);
    mappings.put('f', f);
    final var e = find(counts, 4);
    mappings.put('e', e);
    final var c = find(one, f);
    mappings.put('c', c);
    final var a = find(seven, c, f);
    mappings.put('a', a);
    final var d = find(four, b, c, f);
    mappings.put('d', d);
    final var g = find(eight, b, f, e, c, a, d);
    mappings.put('g', g);

    final var words =
        Map.of(
            key("abcefg", mappings), "0",
            key("cf", mappings), "1",
            key("acdeg", mappings), "2",
            key("acdfg", mappings), "3",
            key("bcdf", mappings), "4",
            key("abdfg", mappings), "5",
            key("abdefg", mappings), "6",
            key("acf", mappings), "7",
            key("abcdefg", mappings), "8",
            key("abcdfg", mappings), "9");

    return Arrays.stream(line.split("\\|")[1].split(" "))
        .map(String::trim)
        .filter(x -> !x.isBlank())
        .map(Day8::sortChars)
        .map(words::get)
        .collect(collectingAndThen(joining(""), Long::parseLong));
  }

  private static char find(Map<Character, Integer> counts, int target) {
    return counts.entrySet().stream()
        .filter(x -> x.getValue() == target)
        .map(Map.Entry::getKey)
        .findFirst()
        .orElseThrow();
  }

  private static char find(String base, char... not) {
    final var filters = new HashSet<Character>();
    for (char n : not) {
      filters.add(n);
    }
    return base.chars()
        .mapToObj(i -> (char) i)
        .filter(x -> !filters.contains(x))
        .findFirst()
        .orElseThrow();
  }

  private static String sortChars(String s) {
    return s.chars()
        .sorted()
        .mapToObj(i -> (char) i)
        .reduce("", (s1, s2) -> s1 + s2, ((s1, s2) -> s1 + s2));
  }

  private static String key(String actual, Map<Character, Character> mappings) {
    final var value =
        actual
            .chars()
            .mapToObj(i -> (char) i)
            .map(mappings::get)
            .reduce("", (a, b) -> a + b, (a, b) -> a + b);
    return sortChars(value);
  }
}
