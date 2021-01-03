(ns safehammad.day17
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day17-input.txt"))))

(defn search-limits
  "The range to be searched for changes in the next step"
  [cubes]
  (letfn [(ranges [f] (map f (keys cubes)))]
    (map
      (partial apply range)
      (map (juxt
             (comp dec (partial apply min))
             (comp inc inc (partial apply max)))
           (map ranges [first second last])))))

(defn search-coords
  [cubes]
  (apply combo/cartesian-product (search-limits cubes)))

(def neighbour-offsets 
  (for [x [-1 0 1]
        y [-1 0 1]
        z [-1 0 1]
        :when (not (= x y z 0))]
    [x y z]))

(defn cube-at
  [cubes coords]
  (get cubes coords \.))

(defn neighbour-coords
  "Return the coords of the 26 neighbouring cubes."
  [coords]
  (map (partial mapv + coords) neighbour-offsets))

(defn neighbour-cubes
  "Return the 26 neighbouring cubes."
  [cubes coords]
  (map (partial cube-at cubes) (neighbour-coords coords)))

(defn count-cubes
  [cube-list]
  (count (filter #{\#} cube-list)))

(defn run-rules
  "Return active/inactive for the next generation."
  [cubes coord]
  (case [(cube-at cubes coord) (count-cubes (neighbour-cubes cubes coord))]
    [\# 2] \#
    [\# 3] \#
    [\. 3] \#
    \.))

(defn step
  [cubes]
  (->> (search-coords cubes)
       (map #(vector % (run-rules cubes %)))
       (into {})))

(defn conway
  [input]
  (into
    {}
    (let [z 0]
      (for [[x line] (map-indexed vector input)
            [y cube] (map-indexed vector line)]
        {[x y z] cube}))))

(defn calculate
  [input]
  (count-cubes (vals (first (drop 6 (iterate step (conway input)))))))

(defn run [part]
  (case part
    :part1 (calculate input)
    :part2 (calculate-part2 input)))
