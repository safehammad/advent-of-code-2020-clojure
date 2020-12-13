(ns safehammad.aoc2020
  (:require [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]
            [safehammad.day3 :as day3]
            [safehammad.day4 :as day4]
            [safehammad.day5 :as day5]
            [safehammad.day6 :as day6]
            [safehammad.day7 :as day7])
  (:gen-class))

(defn -main
  [& args]
  (println "Day 1, part 1: " (day1/run :part1))   ; 2 numbers add up to 2020
  (println "Day 1, part 2: " (day1/run :part2))   ; 3 numbers add up to 2020
  (println "Day 2, part 1: " (day2/run :part1))   ; Number of valid passwords part 1
  (println "Day 2, part 2: " (day2/run :part2))   ; Number of valid passwords part 2
  (println "Day 3, part 1: " (day3/run :part1))   ; Number of trees part 1
  (println "Day 3, part 2: " (day3/run :part2))   ; Number of trees part 2
  (println "Day 4, part 1: " (day4/run :part1))   ; Number of passports with all required fields
  (println "Day 4, part 2: " (day4/run :part2))   ; Number of valid passports
  (println "Day 5, part 1: " (day5/run :part1))   ; Highest seat id
  (println "Day 5, part 2: " (day5/run :part2))   ; Missing seat id
  (println "Day 6, part 1: " (day6/run :part1))   ; Questions answered by any
  (println "Day 6, part 2: " (day6/run :part2))   ; Questions answered by all
  (println "Day 7, part 1: " (day7/run :part1))   ; Containers of shiny gold bags
  (println "Day 7, part 1: " (day7/run :part2)))  ; Contents of shiny gold bags
