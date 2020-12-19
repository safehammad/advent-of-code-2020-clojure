;; Part 1 is a bit messy!
;; Part 2 is much more like idiomatic Clojure ;)
(ns safehammad.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day12-input.txt"))))

;; Example input
;(def input ["F10" "N3" "F7" "R90" "F11"])

(def compass {"N" 0
              "E" 90
              "S" 180
              "W" 270
              0   "N"
              90  "E"
              180 "S"
              270 "W"})

(defn rotate-direction
  "Return new direction."
  [action angle direction]
  (compass (mod (+ (compass direction) (* angle (case action "L" -1 "R" 1))) 360)))

(defn convert-instruction
  [[action amount] current-direction]
  (cond
    (= "F" action) [current-direction amount]
    (#{"R" "L"} action) nil
    :else [action amount]))

(defn new-direction
  [[action amount] current-direction]
  (if (#{"R" "L"} action)
    (rotate-direction action amount current-direction)
    current-direction))

(defn input->instructions
  "Parse to series of [action amount]."
  [input]
  (map #(vector (subs % 0 1) (Integer/parseInt (subs % 1))) input))

(defn translate-instructions
  "Translate and normalise instructions to [direction amount]."
  [instructions]
  (-> (reduce
        (fn [{:keys [direction acc]} instruction]
          (let [instruction' (convert-instruction instruction direction)
                direction' (new-direction instruction direction)
                acc' (if instruction' (conj acc instruction') acc)]
            {:direction direction' :acc acc'}))
        {:direction "E" :acc []}
        instructions)
      :acc))

(defn translate
  [[x y] [direction amount]]
  (case direction
    "N" [x (+ y amount)]
    "E" [(+ x amount) y]
    "S" [x (- y amount)]
    "W" [(- x amount) y]))

(defn rotate-point
  "Rotate point around origin."
  [[x y] [direction angle]]
  (let [angle (if (= direction "L") (- 360 angle) angle)]
    (case angle
      90  [y (- x)]
      180 [(- x) (- y)]
      270 [(- y) x])))

(defn move-point
  "Move point by a vector, optionally n times."
  ([[dx dy] [x y]] [(+ x dx) (+ y dy)])
  ([times diff point] (first (drop times (iterate (partial move-point diff) point)))))

(defn move-all
  [{:keys [ship waypoint]} [action amount]]
  (cond
    (#{"N" "E" "W" "S"} action) {:ship ship :waypoint (translate waypoint [action amount])}
    (#{"L" "R"} action) {:ship ship :waypoint (rotate-point waypoint [action amount])}
    (#{"F"} action) {:ship (move-point amount waypoint ship) :waypoint waypoint}
    :else {:ship ship :waypoint waypoint}))

(defn manhatten
  [pos]
  (apply + (map #(Math/abs %) pos)))

(defn run-input-part1
  [input]
  (->> input
       input->instructions
       translate-instructions
       (reduce translate [0 0])
       manhatten))

(defn run-input-part2
  [input]
  (->> input
       input->instructions
       (reduce move-all {:ship [0 0] :waypoint [10 1]})
       :ship
       manhatten))

(defn run
  [part]
  (case part
    :part1 (run-input-part1 input)
    :part2 (run-input-part2 input)))
