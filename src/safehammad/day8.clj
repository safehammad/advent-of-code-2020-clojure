(ns safehammad.day8
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day8-input.txt"))))

(defn create-program
  [input]
  (mapv #(str/split % #" ") input))

(defn execute
  "Execute program instruction at given pointer."
  [program pointer acc]
  (let [[op value] (get program pointer)
        value (Integer/parseInt value)]
    [(if (= op "jmp") (+ pointer value) (inc pointer))
     (if (= op "acc") (+ acc value) acc)]))

(defn run-program
  "Return the final value of the accumulator."
  [program]
  (loop [pointer 0 visited #{} acc 0]
    (if (visited pointer)
      acc
      (let [[next-pointer new-acc] (execute program pointer acc)]
        (recur next-pointer (conj visited pointer) new-acc)))))

(defn run
  "Run program (boot code)."
  [part]
  (cond
    (= :part1 part) (run-program (create-program input))    ; Final value of accumulator
    (= :part2 part) (run-program (create-program input))))  ; Final value of accumulator
