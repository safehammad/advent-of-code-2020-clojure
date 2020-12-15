(ns safehammad.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]]))

(def input
  "Series of entries from input file."
  (map #(Integer/parseInt %)
       (str/split-lines
         (slurp (io/resource "day9-input.txt")))))

(defn valid-entry?
  "Return true if the entry is the sum of two of the previous n entries."
  [[entry previous-n]]
  (contains? (set (map (partial apply +) (combinations previous-n 2))) entry))

(defn scan
  "Scan for invalid entries."
  [input preamble]
  (->> (partition (inc preamble) 1 input)
       (map #(vector (last %) (butlast %)))
       (remove valid-entry?)
       first
       first))

(defn run
  "Find incorrect number."
  [part]
  (cond
    (= :part1 part) (scan input 25)  ; Final value of accumulator
    (= :part2 part) (:acc (run-fixed-program (create-program input)))))       ; Final value of accumulator
