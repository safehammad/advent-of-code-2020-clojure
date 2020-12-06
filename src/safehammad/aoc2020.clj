(ns safehammad.aoc2020
  (:require [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]
            [safehammad.day3 :as day3])
  (:gen-class))

(defn -main
  [& args]
  (println "Day 1, part 1: " (day1/run :part1))   ; 2 numbers add up to 2020
  (println "Day 1, part 2: " (day1/run :part2))   ; 3 numbers add up to 2020
  (println "Day 2, part 1: " (day2/run :part1))   ; Number of valid passwords part 1
  (println "Day 2, part 2: " (day2/run :part2))   ; Number of valid passwords part 2
  (println "Day 3, part 1: " (day3/run :part1))   ; Number of trees part 1
  (println "Day 3, part 2: " (day3/run :part2)))  ; Number of trees part 2
