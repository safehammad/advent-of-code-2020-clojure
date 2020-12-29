(ns safehammad.day16
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def input
  "Series of entries from input file."
  (map
    str/split-lines
    (str/split
      (slurp (io/resource "day16-input.txt")) #"\n\n")))

(defn parse-field [field]
  (let [[field-name values] (str/split field #": ")]
    {:field field-name
     :ranges (->> values
                  (re-seq #"(\d+)-(\d+)")
                  (map rest)
                  (map (partial map read-string)))}))

(defn parse-fields [[fields your-ticket nearby-tickets]]
  (map parse-field fields))

(defn parse-fields-simple
  "Return a simple list of ranges without regard for field."
  [[fields your-ticket nearby-tickets]]
  (->> fields
       (mapcat (partial re-seq #"(\d+)-(\d+)"))
       (map rest)
       (map (partial map read-string))))

(defn parse-ticket
  [ticket]
  (map read-string (str/split ticket #",")))

(defn parse-tickets
  [tickets]
  (map parse-ticket (rest tickets)))

(defn parse-nearby-tickets
  [[fields your-ticket nearby-tickets]]
  (parse-tickets nearby-tickets))

(defn parse-your-ticket
  [[fields your-ticket nearby-tickets]]
  (first (parse-tickets your-ticket)))

(defn valid-value [fields value]
  (some (fn [[low high]] (<= low value high)) fields))

(defn valid-ticket [fields ticket]
  (every? (partial valid-value fields) ticket))

(defn possible-fields [fields values]
  (keep #(when (valid-ticket (:ranges %) values) (:field %)) fields))

(defn singleton-field [field-orders]
  (first (filter #(= 1 (count (second %))) field-orders)))

(defn possible-field-positions [input]
  (let [all-fields (parse-fields-simple input)
        your-tickets (parse-your-ticket input)
        fields (parse-fields input)
        nearby-tickets (parse-nearby-tickets input)
        valid-nearby-tickets (filter (partial valid-ticket all-fields) nearby-tickets)]
    (map
      (partial possible-fields fields)
      (apply map vector (conj valid-nearby-tickets your-tickets)))))

(defn field-order [input]
  (loop [field-orders (map-indexed vector (possible-field-positions input)) acc []]
    (if-not (seq field-orders)
      acc
      (let [next-field (singleton-field field-orders)
            [index [field]] next-field]
        (recur (map
                 (fn [[i fields]] [i (remove #{field} fields)])
                 (remove #{next-field} field-orders))
               (conj acc [index field]))))))

(defn field-indices [input]
  (->> input
       field-order
       (filter #(str/starts-with? (second %) "departure"))
       (map first)))

(defn calculate-part1 [input]
  (let [fields (parse-fields-simple input)
        nearby-tickets (flatten (parse-nearby-tickets input))]
    (apply + (remove (partial valid-value fields) nearby-tickets))))

(defn calculate-part2 [input]
  (let [your-tickets (parse-your-ticket input)]
    (apply * (map #(nth your-tickets %) (field-indices input)))))

(defn run [part]
  (case part
    :part1 (calculate-part1 input)
    :part2 (calculate-part2 input)))
