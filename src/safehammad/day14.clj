(ns safehammad.day14
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set]
            [clojure.math.combinatorics :as combo]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day14-input.txt"))))

(defn parse-input
  [input]
  (partition 2 (partition-by #(str/starts-with? % "mask") input)))

(defn parse-instruction
  [[[mask & _] mems]]
  [mask mems])

(defn parse-mems
  [mems]
  (map
    #(map read-string %1)
    (map rest (map (partial re-find #"mem\[(\d+)\] = (\d+)") mems))))

(defn parse-mask
  [mask]
  (second (re-find #"mask = (\w+)" mask)))

(defn mask->int
  ([mask] (read-string (str "2r" mask)))
  ([mask replacement]
   (mask->int (str/replace mask #"X" replacement))))

(defn modify
  [modify-fn [mask mems]]
  (mapcat (partial modify-fn mask) mems))

;; Part 1

(defn apply-value-mask
  [mask [loc value]]
  (let [or-mask  (mask->int mask "0")
        and-mask (mask->int mask "1")]
    [[loc (bit-or (bit-and value and-mask) or-mask)]]))

;; Part 2

(defn x-indices
  "Indices of bits from right to left of X in mask."
  [mask]
  (remove nil? (map-indexed #(when (= %2 \X) %1) (reverse mask))))

(defn change-bits
  "Given a value, either set or unset bits for the given indices."
  [value set-bits unset-bits]
  (reduce bit-clear (reduce bit-set value set-bits) unset-bits))

(defn apply-memory-mask
  [mask [mem value]]
  (let [x-bits (x-indices mask)
        subsets (map set (combo/subsets x-bits))
        inv-subsets (map (partial set/difference (set x-bits)) subsets)
        or-mask (mask->int mask "0")
        or-value (bit-or mem or-mask)]
    (map
      #(vector % value)
      (map (partial change-bits or-value) subsets inv-subsets))))

(defn calculate
  [input modify-fn]
  (->> input
       (parse-input)
       (map parse-instruction)
       (map (fn [[mask mems]] [(parse-mask mask) (parse-mems mems)]))
       (mapcat (partial modify modify-fn))
       (reduce conj {})
       vals
       (apply +)))

(defn run
  [part]
  (case part
    :part1 (calculate input apply-value-mask)
    :part2 (calculate input apply-memory-mask)))
