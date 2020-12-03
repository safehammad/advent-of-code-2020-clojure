(ns safehammad.day2
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :refer [combinations]])
  (:gen-class))

(def input
  "Seq of password entries from input file."
  (str/split-lines
    (slurp (io/resource "day2-input.txt"))))
 
(defn parse-line
  "Parse line in file to seq of (limits letter password)."
  [line]
  (let [[limits letter password] (str/split line #" ")]
    [(map #(Integer/parseInt %) (str/split limits #"-"))
     (str/replace letter #":" "")
     password]))

(defn valid
  "Given (limits letter password) return true if password valid."
  [[limits letter password]]
  (let [[min-count max-count] limits
        letter-count (count (re-seq (re-pattern letter) password))]
  (<= min-count letter-count max-count)))

(defn run
  "Calculate number of valid passwords."
  []
  (count (filter valid (map parse-line input))))
