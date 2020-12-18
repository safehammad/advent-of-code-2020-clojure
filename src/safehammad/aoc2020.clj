(ns safehammad.aoc2020
  (:require [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]
            [safehammad.day3 :as day3]
            [safehammad.day4 :as day4]
            [safehammad.day5 :as day5]
            [safehammad.day6 :as day6]
            [safehammad.day7 :as day7] 
            [safehammad.day8 :as day8]
            [safehammad.day9 :as day9]
            [safehammad.day10 :as day10]
            [safehammad.day11 :as day11]
            [safehammad.day12 :as day12])
  (:gen-class))

(defn -main
  [& args]
  (println "Day 1, part 1: " (day1/run :part1))
  (println "Day 1, part 2: " (day1/run :part2))
  (println "Day 2, part 1: " (day2/run :part1))
  (println "Day 2, part 2: " (day2/run :part2))
  (println "Day 3, part 1: " (day3/run :part1))
  (println "Day 3, part 2: " (day3/run :part2))
  (println "Day 4, part 1: " (day4/run :part1))
  (println "Day 4, part 2: " (day4/run :part2))
  (println "Day 5, part 1: " (day5/run :part1))
  (println "Day 5, part 2: " (day5/run :part2))
  (println "Day 6, part 1: " (day6/run :part1))
  (println "Day 6, part 2: " (day6/run :part2))
  (println "Day 7, part 1: " (day7/run :part1))
  (println "Day 7, part 2: " (day7/run :part2))
  (println "Day 8, part 1: " (day8/run :part1))
  (println "Day 8, part 2: " (day8/run :part2))
  (println "Day 9, part 1: " (day9/run :part1))
  (println "Day 9, part 2: " (day9/run :part2))
  (println "Day 10, part 1: " (day10/run :part1))
  (println "Day 10, part 2: " (day10/run :part2))
  (println "Day 11, part 1: " (day11/run :part1))
  (println "Day 11, part 2: " (day11/run :part2))
  (println "Day 12, part 1: " (day12/run :part1))
  (println "Day 12, part 2: " (day12/run :part2)))
