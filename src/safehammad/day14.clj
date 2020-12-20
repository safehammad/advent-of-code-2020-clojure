(ns safehammad.day14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day14-input.txt"))))

;; Part 1

(defn parse-input
  [input]
  (partition 2 (partition-by #(str/starts-with? % "mask") input)))

(defn parse-instruction
  [[[mask & _] mems]]
  [mask mems])

(defn parse-mems
  [mems]
  (mapv rest (map (partial re-find #"mem\[(\d+)\] = (\d+)") mems)))

(defn parse-mask
  [mask]
  (second (re-find #"mask = (\w+)" mask)))

(defn mask->int
  [mask replacement]
  (read-string (str "2r" (str/replace mask #"X" replacement))))

(defn apply-mask
  [mask [loc value]]
  (let [or-mask  (mask->int mask "0")
        and-mask (mask->int mask "1")]
    [loc (bit-or (bit-and (read-string value) and-mask) or-mask)]))

(defn execute
  [[mask mems]]
  (map (partial apply-mask (parse-mask mask)) (parse-mems mems)))

(defn calculate-part1
  [input]
  (->> input
       (parse-input)
       (map parse-instruction)
       (mapcat execute)
       (reduce conj {})
       vals
       (apply +)))

(defn calculate-part2
  [input]
  nil)

(defn run
  [part]
  (case part
    :part1 (calculate-part1 input)
    :part2 (calculate-part2 input)))
