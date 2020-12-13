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
  (let [[op value] (get program pointer)
        value (Integer/parseInt value)]
    [(if (= op "jmp") (+ pointer value) (inc pointer))
     (if (= op "acc") (+ acc value) acc)]))

(defn run-program
  "Return the final value of the accumulator."
  [program]
  (loop [pointer 0 visited #{} acc 0]
    (cond
      (visited pointer) {:success false, :acc acc}
      (= pointer (count program)) {:success true, :acc acc}
      :else (let [[next-pointer new-acc] (execute program pointer acc)]
              (recur next-pointer (conj visited pointer) new-acc)))))

(defn op-indices
  "Indices of the given op in the program."
  [program op]
  (->> program
       (map-indexed vector)
       (filter #(= op (get-in % [1 0])))
       (map first)))

(defn fixes
  "Provide a series of fixes where from-op is changed to to-op once in each fix."
  [program from-op to-op]
  (map #(assoc-in program [% 0] to-op) (op-indices program from-op)))

(defn fix-program
  [program]
  (->> (concat (fixes program "nop" "jmp") (fixes program "jmp" "nop"))
       (map run-program)
       (filter #(= (:success %) true))
       first
       :acc))

(defn run
  "Run program (boot code)."
  [part]
  (cond
    (= :part1 part) (:acc (run-program (create-program input)))  ; Final value of accumulator
    (= :part2 part) (fix-program (create-program input))))       ; Final value of accumulator
