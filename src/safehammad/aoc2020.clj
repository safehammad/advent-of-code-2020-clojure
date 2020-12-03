(ns safehammad.aoc2020
  (:require [safehammad.day1 :as day1])
  (:gen-class))

(defn -main
  [& args]
  (println "Day 1, part 1: " (day1/run 2))   ; 2 numbers add up to 2020
  (println "Day 1, part 2: " (day1/run 3)))  ; 3 numbers add up to 2020
