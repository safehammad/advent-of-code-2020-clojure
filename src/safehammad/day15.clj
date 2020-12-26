(ns safehammad.day15)

(def input [13 0 10 12 1 5 8])
;(def input [0 3 6])

;; Part 1

(defn age
  [x xs]
  (inc (count (take-while (partial not= x) (rseq xs)))))

(defn turn
  [[numbers visited]]
  (let [x (peek numbers)]
    (if (contains? visited x)
      [(conj numbers (age x (pop numbers))) visited]
      [(conj numbers 0) (conj visited x)])))

(defn spoken
  [input n]
  (last (first (first (drop (- n (count input)) (iterate turn [input (set (pop input))]))))))

;; Part 2 - more performant!

(defn initial
  [input]
  [(count input) (zipmap (pop input) (iterate inc 1)) (peek input)])

(defn turn'
  [[index ages next-val]]
  [(inc index) (assoc ages next-val index) (- index (get ages next-val index))])

(defn spoken'
  [input n]
  (let [n (- n (count input))]
    (last (nth (iterate turn' (initial input)) n))))

(defn run
  [part]
  (case part
    :part1 (spoken' input 2020)
    :part2 (spoken' input 30000000)))
