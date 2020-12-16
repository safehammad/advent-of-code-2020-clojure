(ns safehammad.day6
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]))

(def input
  "Series of entries from input file. Entries split on blank line."
  (str/split
    (slurp (io/resource "day6-input.txt")) #"\n\n"))

(defn question-count
  "Find number of questions answered yes."
  [input question-resolution-fn]
  (->> input
       (map str/split-lines)
       (map (partial map set))
       (map (partial apply question-resolution-fn))
       (map count)
       (apply +)))

(defn run
  "Count number of questions"
  [part]
  (case part
    :part1 (question-count input set/union)           ; Questions answered by *any*
    :part2 (question-count input set/intersection)))  ; Questions answered by *all*
