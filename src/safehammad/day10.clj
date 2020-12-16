(ns safehammad.day10
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (map read-string
       (str/split-lines
         (slurp (io/resource "day10-input.txt")))))

;; Part 1

(defn jolt-stream
  "Ordered list of jolts starting with 0 and ending with final +3 jolt for device. "
  [input]
  (let [sorted-input (sort input)
        device-jolt (+ 3 (last sorted-input))]
    (concat [0] sorted-input [device-jolt])))

(defn jolt-distribution
  [input]
  (->> input
       jolt-stream
       (partition 2 1)
       (map reverse)
       (map (partial apply -))
       (group-by identity)
       (vals)
       (map count)
       (apply *)))

;; Part 2

(defn expand-options
  "Expand sequence of joltages to the possible joltages the first in the sequence can connect to."
  [[x & xs]]
  (map (partial vector x) (take-while #(<= (- % x) 3) xs)))  ; An adapter can connect to up to 3 more jolts

(defn adapter-options
  "Sequence of tuples linking one joltage to a possible next adapter."
  [input]
  (loop [stream (jolt-stream input) acc []]
    (if (empty? stream)
      acc
      (recur (rest stream) (into acc (expand-options stream))))))

(defn joltage-map
  "Map a given joltage to the possible next joltages."
  [input]
  (->> input
       adapter-options
       (group-by first)
       (reduce-kv (fn [m k v] (assoc m k (mapv second v))) {})))

(def adapter-permutations
  (memoize
    (fn [joltage-map joltage]
      (let [max-joltage (apply max (keys joltage-map))]
        (if (= joltage max-joltage)
          1
          (apply + (map (partial adapter-permutations joltage-map) (joltage-map joltage))))))))

(defn run
  [part]
  (case part
    :part1 (jolt-distribution input)
    :part2 (adapter-permutations (joltage-map input) 0)))
