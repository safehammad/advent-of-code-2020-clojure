(ns safehammad.day13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day13-input.txt"))))

;; Examples
;(def input ["939" "7,13,x,x,59,x,31,19"])
;(def input ["N/A" "17,x,13,19"])
;(def input ["N/A" "67,7,59,61"])
;(def input ["N/A" "67,x,7,59,61"])
;(def input ["N/A" "67,7,x,59,61"])
;(def input ["N/A" "1789,37,47,1889"])

;; Part 1

(defn parse-input
  [[line1 line2]]
  {:timestamp (Integer/parseInt line1)
   :buses (mapv read-string (remove #{"x"} (str/split line2 #",")))})

(defn first-above-timestamp
  [timestamp bus]
  (->> (iterate (partial + bus) 0)
       (drop-while #(< % timestamp))
       first))

(defn best-bus
  "Return [timestamp bus] for bus closest above timestamp."
  [timestamp buses]
  (first (sort (map vector (map (partial first-above-timestamp timestamp) buses) buses))))

(defn calculate-part1
  [input]
  (let [{:keys [timestamp buses]} (parse-input input)
        [timestamp' bus] (best-bus timestamp buses)]
    (* bus (- timestamp' timestamp))))

;; Part 2

(defn parse-buses
  [[_ line2]]
  (remove
    #(= 'x (second %))
    (map-indexed vector (map read-string (str/split line2 #",")))))

(defn next-timestamp
  [{:keys [multiplier timestamp]} [offset bus]]
  {:multiplier (math/lcm multiplier bus)
   :timestamp (first
                (filter
                  #(zero? (mod (+ % offset) bus))
                  (iterate (partial + multiplier) timestamp)))})

(defn calculate-part2
  [input]
  (let [buses (parse-buses input)]
    (->> buses
        (reduce next-timestamp {:multiplier 1 :timestamp 1})
        :timestamp)))

(defn run
  [part]
  (case part
    :part1 (calculate-part1 input)
    :part2 (calculate-part2 input)))
