(ns safehammad.day11
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day11-input.txt"))))

(def neighbour-directions 
  (for [row [-1 0 1]
        col [-1 0 1]
        :when (not (= row col 0))]
    [row col]))

(defn visible-directions
  [[row-direction, col-direction]]
  (map #(vector (* row-direction %) (* col-direction %)) (iterate inc 1)))

(defn map-coords
  [[row col] mappings]
  (map (fn [[row' col']] [(+ row row') (+ col col')]) mappings))

(defn seat-at
  "Seat at coord [row col]. Return \\X if off edge of layout."
  [layout coord]
  (get-in layout coord \X))

(defn visible-seat
  [layout coord direction]
  (first
    (drop-while
      #{\.}
      (map (partial seat-at layout) (map-coords coord (visible-directions direction))))))

(defn neighbour-seats
  "Part 1 neighbour algorithm."
  [layout coord]
  (map (partial seat-at layout) (map-coords coord neighbour-directions)))

(defn neighbour-seats-visible
  "Part 2 neighbour algorithm."
  [layout coord]
  (map (partial visible-seat layout coord) neighbour-directions))

(def neighbour-thresholds {:part1 4, :part2 5})

(defn neighbour-seat-fn
  [part]
  (if (= part :part1) neighbour-seats neighbour-seats-visible))

(defn next-seat
  [part layout coord current-seat]
  (let [occupied-neighbours (count (filter #{\#} ((neighbour-seat-fn part) layout coord)))]
    (case current-seat
      \L (if (= occupied-neighbours 0) \# \L)
      \# (if (>= occupied-neighbours (get neighbour-thresholds part)) \L \#)
      current-seat)))

(defn transform-row
  [part layout row-index row]
  (vec (map-indexed #(next-seat part layout [row-index %1] %2) row)))

(defn next-layout
  [part layout]
  (vec (map-indexed (partial transform-row part layout) layout)))

(defn display
  "Utility to print layout."
  [layout]
  (doseq [line layout] (println (apply str line))))

(defn count-occupied
  [layout]
  (count (mapcat (partial filter #{\#}) layout)))

(defn stable-layout
  [part layout]
  (->> layout
       (iterate (partial next-layout part))
       (partition 2 1)
       (drop-while (partial apply not=))
       first
       first))

(defn run
  [part]
  (count-occupied (stable-layout part input)))
