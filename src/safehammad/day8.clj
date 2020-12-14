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
  "Execute program instruction at pointer returning [next pointer, new accumulator]."
  [program pointer acc]
  (let [[op value] (nth program pointer)
        value (Integer/parseInt value)]
    [(if (= op "jmp") (+ pointer value) (inc pointer))
     (if (= op "acc") (+ acc value) acc)]))

(defn run-program
  "Run program and return result."
  [program]
  (loop [pointer 0 acc 0 visited #{}]
    (cond
      (visited pointer) {:success false, :acc acc}
      (= pointer (count program)) {:success true, :acc acc}
      :else (let [[next-pointer new-acc] (execute program pointer acc)]
              (recur next-pointer new-acc (conj visited pointer))))))

(defn op-indices
  "Indices of the given op in the program."
  [program op]
  (keep-indexed (fn [i [op' _]] (when (= op' op) i)) program))

(defn fixes
  "Provide a series of fixes where from-op is changed to to-op once in each fix."
  [program from-op to-op]
  (map #(assoc-in program [% 0] to-op) (op-indices program from-op)))

(defn run-fixed-program
  "Run fixed programs until success and return result."
  [program]
  (->> (concat (fixes program "nop" "jmp") (fixes program "jmp" "nop"))
       (map run-program)
       (filter (partial :success))
       first))

(defn run
  "Run program (boot code)."
  [part]
  (cond
    (= :part1 part) (:acc (run-program (create-program input)))  ; Final value of accumulator
    (= :part2 part) (:acc (run-fixed-program (create-program input)))))       ; Final value of accumulator
