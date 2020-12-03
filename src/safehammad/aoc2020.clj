(ns safehammad.aoc2020
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def input
  "Seq of numbers from input file."
  (map #(Integer/parseInt %)
     (str/split-lines
       (slurp (io/resource "input.txt")))))

(defn sums
  "Seq of sums of first and rest given a seq of numbers."
  [[x & xs]]
  (map #(vector (+ x %) x %) xs))

(defn filter-first
  "Return a filter function where the first number is the given number."
  [number]
  (fn [x] (= number (first x))))

(defn run
  [numbers]
  (loop [acc [] numbers numbers]
    (if (< (count numbers) 2)
      acc
      (recur (concat acc (sums numbers)) (rest numbers)))))

(defn -main
  [& args]
  (apply * (rest (first (filter (filter-first 2020) (run input))))))
