(ns safehammad.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day12-input.txt"))))

;; Example input
; (def input ["F10" "N3" "F7" "R90" "F11"])

(def compass {"N" 0
              "E" 90
              "S" 180
              "W" 270})

(defn rotate
  "Return new angle."
  [action amount angle]
  (mod (+ angle (* amount (case action "L" -1 "R" 1))) 360))

(defn convert-instruction
  [[action amount] current-angle]
  (cond
    (= "F" action) [current-angle amount]
    (#{"R" "L"} action) nil
    :else [(compass action) amount]))

(defn new-angle
  [[action amount] current-angle]
  (if (#{"R" "L"} action)
    (rotate action amount current-angle)
    current-angle))

(defn input->instructions
  "Parse to series of [action amount]."
  [input]
  (map #(vector (subs % 0 1) (Integer/parseInt (subs % 1))) input))

(defn translate-instructions
  "Translate and normalise instructions to [angle amount]."
  [instructions]
  (-> (reduce
        (fn [{:keys [angle acc]} instruction]
          (let [instruction' (convert-instruction instruction angle)
                angle' (new-angle instruction angle)
                acc' (if instruction' (conj acc instruction') acc)]
            {:angle angle' :acc acc'}))
        {:angle (compass "E") :acc []}
        instructions)
      :acc))

(defn move-ship
  [[x y] [angle amount]]
  (case angle
    0   [x (- y amount)]
    90  [(+ x amount) y]
    180 [x (+ y amount)]
    270 [(- x amount) y]))

(defn manhatten
  [[x y]]
  (+ x y))

(defn run-input
  [input]
  (->> input
       input->instructions
       translate-instructions
       (reduce move-ship [0 0])
       manhatten))

(defn run
  [part]
  (case part
    :part1 (run-input input)
    :part2 (run-input input)))
