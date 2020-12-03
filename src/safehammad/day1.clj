(ns safehammad.day1
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:gen-class))

(def input
  "Seq of numbers from input file."
  (map #(Integer/parseInt %)
     (str/split-lines
       (slurp (io/resource "input.txt")))))
 
(defn triplets
  "Seq of triplets: (sum, first, xs1), (sum first xs2), etc."
  [[x & xs]]
  (map #(vector (+ x %) x %) xs))

(defn groups
  "Given (a b c d ...) return (a b c d ...), (b c d ...), up to final pair."
  [numbers]
  (take-while #(> (count %) 1) (iterate rest numbers)))

(defn answer
  "Return the final answer from the first triplet."
  [[[sum x y] & _]]
  (* x y))

(defn run
  []
  (answer (filter #(= (first %) 2020) (mapcat triplets (groups input)))))
