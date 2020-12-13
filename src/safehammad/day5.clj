(ns safehammad.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of boarding passes from input file."
  (str/split-lines
    (slurp (io/resource "day5-input.txt"))))

(defn translator
  "Translate boarding pass notation to number."
  [start end char-0 char-1 boarding-pass]
  (-> boarding-pass
      (subs start end)
      (str/replace char-0 "0")
      (str/replace char-1 "1")
      (Integer/parseInt 2)))

(def row-fn (partial translator 0 7 "F" "B"))
(def column-fn (partial translator 7 10 "L" "R"))

(defn seat-id
  "Return a seat id given a boarding pass."
  [boarding-pass]
  (let [row (row-fn boarding-pass)
        column (column-fn boarding-pass)]
  (+ column (* row 8))))

(defn part1
  "Find highest seat id on a boarding pass."
  [input]
  (apply max (map seat-id input)))

(defn part2
  "Find missing seat id."
  [input]
  (->> input
       (map seat-id)
       sort
       (partition 2 1)
       (filter (fn [[a b]] (not= b (inc a))))
       (map #(/ (apply + %) 2))
       first))

(defn run
  "Count number of trees passed."
  [part]
  (cond
    (= :part1 part) (part1 input)
    (= :part2 part) (part2 input)))
