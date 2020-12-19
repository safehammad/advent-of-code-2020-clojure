(ns safehammad.day13
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (str/split-lines
    (slurp (io/resource "day13-input.txt"))))

;(def input ["939" "7,13,x,x,59,x,31,19"])

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

(defn calculate
  [input]
  (let [{:keys [timestamp buses]} (parse-input input)
        [timestamp' bus] (best-bus timestamp buses)]
    (* bus (- timestamp' timestamp))))

(defn run
  [part]
  (case part
    :part1 (calculate input)
    :part2 (calculate input)))
