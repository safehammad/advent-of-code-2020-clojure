(ns safehammad.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day11-input.txt"))))

;; Part 1

(defn seat-at
  "Seat at coord [row col]."
  [layout coord]
  (get-in layout coord \.))

(defn neighbours
  [[row col]]
  (for [row' [-1 0 1]
        col' [-1 0 1]
        :when (not (and (= row' col' 0)))]
    [(+ row row') (+ col col')]))

(defn next-seat
  [layout coord current-seat]
  (let [neighbour-seats (map (partial seat-at layout) (neighbours coord))
        occupied-neighbours (count (filter #{\#} neighbour-seats))]
    (case current-seat
      \L (if (= occupied-neighbours 0) \# \L)
      \# (if (>= occupied-neighbours 4) \L \#)
      current-seat)))

(defn transform-row
  [layout row-index row]
  (vec (map-indexed #(next-seat layout [row-index %1] %2) row)))

(defn next-layout
  [layout]
  (vec (map-indexed (partial transform-row layout) layout)))

(defn display
  "Utility to print layout."
  [layout]
  (doseq [line layout] (println (apply str line))))

(defn count-occupied
  [layout]
  (count (mapcat (partial filter #{\#}) layout)))

(defn stable-layout
  [layout]
  (->> layout
       (iterate next-layout)
       (partition 2 1)
       (drop-while (partial apply not=))
       first
       first))

(defn run
  [part]
  (case part
    :part1 (count-occupied (stable-layout input))
    :part2 (count-occupied (stable-layout input))))
