(ns safehammad.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]])
  (:gen-class))

(def input
  "Seq of numbers from input file."
  (map #(Integer/parseInt %)
     (str/split-lines
       (slurp (io/resource "day1-input.txt")))))
 
(defn sum-group
  "Prefix group with sum of group. For example (1 2) -> (3 1 2)."
  [group]
  (conj (apply list group) (apply + group)))

(defn answer
  "Calculate final answer from first sum-group. For example ((9 5 4) ...) -> 5*4 -> 20."
  [[x & xs] & _]
  (apply * xs))

(defn run
  "Run calculation providing number of numbers to add to 2020."
  [cnt]
  (answer (first (filter #(= (first %) 2020) (map sum-group (combinations input cnt))))))
