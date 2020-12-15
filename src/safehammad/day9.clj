(ns safehammad.day9
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]]))

(def input
  "Series of entries from input file."
  (map read-string
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

(defn sum-max-min
  "Sum smallest and largest value in coll."
  [coll]
  (+ (apply max coll) (apply min coll)))

(defn find-contiguous
  "Find contiguous set of n entries which sum to number then sum smallest and largest."
  [input number]
  (->> (iterate inc 2)
       (mapcat #(partition % 1 input))
       (drop-while #(not= number (apply + %)))
       first
       sum-max-min))

(defn run
  [part]
  (cond
    (= :part1 part) (scan input 25)
    (= :part2 part) (find-contiguous input 144381670)))
