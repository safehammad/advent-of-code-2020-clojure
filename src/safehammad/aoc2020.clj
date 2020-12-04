(ns safehammad.aoc2020
  (:require [safehammad.day1 :as day1]
            [safehammad.day2 :as day2])
  (:gen-class))

(defn -main
  [& args]
  (println "Day 1, part 1: " (day1/run 2))   ; 2 numbers add up to 2020
  (println "Day 1, part 2: " (day1/run 3))   ; 3 numbers add up to 2020
  (println "Day 2, part 1: " (day2/run 1))   ; Number of valid passwords part 1
  (println "Day 2, part 2: " (day2/run 2)))  ; Number of valid passwords part 2
