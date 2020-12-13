(ns safehammad.day3
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day3-input.txt"))))  ; example: part1=237, part2=336

(def part2-slopes
  "Slopes for part 2."
  [[1 1]
   [3 1]
   [5 1]
   [7 1]
   [1 2]])
 
(defn number-lines
  "Return lines as [line-no line]."
  [lines]
  (map vector (iterate inc 0) lines))

(defn char-at-line
  "Return character in line based on line number."
  [right [line-no line]]
  (get line (mod (* line-no right) (count line))))

(defn tree-count
  "Count number of trees passed."
  [input right down]
  (->> input
       (partition down down nil)  ; pad to ensure last "odd" line is included
       (map first)
       (number-lines)
       (map (partial char-at-line right))
       (filter #{\#})
       count))

(defn part1
  "Count trees for slope 3 right 1 down."
  [input]
  (tree-count input 3 1))

(defn part2
  "Count trees for a set of slopes and * the result."
  [input slopes]
  (apply * (map (partial apply (partial tree-count input)) slopes)))

(defn run
  "Count number of trees passed."
  [part]
  (cond
    (= :part1 part) (part1 input)
    (= :part2 part) (part2 input part2-slopes)))
