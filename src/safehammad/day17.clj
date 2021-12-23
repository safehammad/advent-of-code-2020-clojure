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
  (map
    (partial apply range)
    (map (juxt
           (comp dec (partial apply min))
           (comp inc inc (partial apply max)))
         (apply map vector (keys cubes)))))

(defn search-coords
  "All coordinates to be calculated in the next step."
  [cubes]
  (apply combo/cartesian-product (search-limits cubes)))

(defn neighbour-offsets [dimensions]
  (->> (repeat dimensions [-1 0 1])
       (apply combo/cartesian-product)
       (remove #{(repeat dimensions 0)})))

(defn cube-at [cubes coords]
  (get cubes coords \.))

(defn neighbour-coords
  "Return the coords of the neighbouring cubes."
  [coords]
  (let [dimensions (count coords)]
    (map (partial mapv + coords) (neighbour-offsets dimensions))))

(defn neighbour-cubes
  "Return the neighbouring cubes."
  [cubes coords]
  (map (partial cube-at cubes) (neighbour-coords coords)))

(defn count-cubes [cube-list]
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
  "Next generation of cubes."
  [cubes]
  (->> (search-coords cubes)
       (map #(vector % (run-rules cubes %)))
       (into {})))

(defn conway
  "Build map of coords ([x y ...]) to active (\\#) or inactive (\\.) cubes."
  [input dimensions]
  (into {} (let [zeros (repeat (- dimensions 2) 0)]
             (for [[x line] (map-indexed vector input)
                   [y cube] (map-indexed vector line)]
               {(into [x y] zeros) cube}))))

(defn calculate [input dimensions]
  (count-cubes (vals (first (drop 6 (iterate step (conway input dimensions)))))))

(defn run [part]
  (case part
    :part1 (calculate input 3)
    :part2 (calculate input 4)))
