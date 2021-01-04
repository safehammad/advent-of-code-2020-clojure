(ns safehammad.day18
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day18-input.txt"))))

(def p1-example1 "2 * 3 + (4 * 5)")
(def p1-example2 "5 + (8 * 3 + 9 + 3 * 4 * 3)")
(def p1-example3 "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
(def p1-example4 "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

(def p2-example1 "1 + (2 * 3) + (4 * (5 + 6))")
(def p2-example2 "2 * 3 + (4 * 5)")
(def p2-example3 "5 + (8 * 3 + 9 + 3 * 4 * 3)")
(def p2-example4 "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
(def p2-example5 "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

(defn evaluate [op value1 value2]
  (eval (list op value1 value2)))

(defn calc-step [{:keys [stack op value] :as state} token]
  (condp re-find token
    #"\("     {:stack (conj stack {:op op :value value}) :op '+ :value 0}
    #"\)"     {:stack (pop stack)
               :op nil
               :value (let [{stack-op :op stack-value :value} (peek stack)] (evaluate stack-op stack-value value))}
    #"[\*\+]" {:stack stack :op (read-string token) :value value}
    #"\d+"    {:stack stack :op op :value (evaluate op value (read-string token))}))

(defn calc-expression
  "Calculate expression."
  [tokens]
  (:value (reduce calc-step {:stack [] :op '+ :value 0} tokens)))

(defn parens-step [{:keys [stack acc parens] :as state} token]
  (case token
    "*" {:stack stack :acc (conj acc "*" "(") :parens (inc parens)}
    "(" {:stack (conj stack parens) :acc (conj acc "(") :parens 0}
    ")" {:stack (pop stack) :acc (into acc (repeat (inc parens) ")")) :parens (peek stack)}
    {:stack stack :acc (conj acc token) :parens parens}))

(defn alter-precedence
  "Introduce open parens after * to alter precedence."
  [tokens]
  (:acc (reduce parens-step {:stack [] :acc [] :parens 0} (concat ["("] tokens [")"]))))

(defn tokens [input]
  (re-seq #"\d+|[\+\*\(\)]" input))

(defn calculate
  ([input]
   (calculate input identity))
  ([input token-processor-fn]
   (apply + (map (comp calc-expression token-processor-fn tokens) input))))

(defn run [part]
  (case part
    :part1 (calculate input)
    :part2 (calculate input alter-precedence)))
