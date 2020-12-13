(ns safehammad.day7
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day7-input.txt"))))

;;; Part 1

(defn parse-mapping1
  [input]
  (->> input
       (map (partial re-seq #"(\w*\s\w*)\sbag"))
       (map (partial map second))
       (mapcat (fn [[x & xs]] (map (partial vector x) xs)))
       set))

(defn find-containers
  [mapping]
  (loop [bags #{"shiny gold"} acc #{}]
    (if-let [new-keys (seq (map first (filter #(contains? bags (second %)) mapping)))]
      (recur (set new-keys) (into acc new-keys))
      acc)))

(defn calculate1
  [input]
  (count (find-containers (parse-mapping1 input))))

;;; Part 2

(defn containers
  "Return a seq of containing bags in input order"
  [input]
  (map (comp second (partial re-find #"^(\w*\s\w*)\sbag")) input))

(defn contents
  "Return a seq of contents in input order."
  [input]
  (->> input
       (map (partial re-seq #"(\d)\s(\w*\s\w*)\sbag"))
       (map (partial map rest))
       (map (partial map (fn [[amount bag]] [(Integer/parseInt amount) bag])))))

(defn parse-mapping2
  "Return a map of container to contents."
  [input]
  (zipmap (containers input) (contents input)))

;; This is not tail recursive!
(defn bag-count
  "Return number of bags."
  ([mapping]
   (bag-count mapping "shiny gold"))
  ([mapping bag]
   (if-let [contents (seq (get mapping bag))]
     (apply + (map
                (fn [[amount sub-bag]] (+ amount (* amount (bag-count mapping sub-bag))))
                contents))
     0)))

(defn calculate2
  [input]
  (bag-count (parse-mapping2 input)))

(defn run
  "Count number of questions"
  [part]
  (cond
    (= :part1 part) (calculate1 input)    ; Number of bags containing shiny gold bag
    (= :part2 part) (calculate2 input)))  ; Number of bags recursively within shiny gold bag
