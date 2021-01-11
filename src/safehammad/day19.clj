(ns safehammad.day19
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

(def input
  "Return [rules messages]."
  (map str/split-lines
       (str/split
         (slurp (io/resource "day19-input.txt")) #"\n\n")))

;; Parse rules

(defn parse-rule [rule-str]
  (let [[_ rule-no rule1 rule2] (re-find #"(\d+): ([^\|]*)\|?\s?(.*)?" rule-str)]
    (if (some #{rule1} ["\"a\"" "\"b\""])
      (hash-map rule-no (read-string rule1))
      (->> (remove str/blank? [rule1 rule2])
           (map #(str/split % #"\s"))
           (hash-map rule-no)))))

(defn parse-rules [rules-input]
  (map parse-rule rules-input))

;; Run rules

(declare expand-rule)

(defn expand-rule-no
  "Expand and combine a rule no, for example, 1."
  [rules rule-no]
  (let [rule (get rules rule-no)]
    (if (some #{rule} ["a" "b"])
      [rule]
      (expand-rule rules rule))))

(defn expand-sub-rule
  "Expand and combine a sub-rule e.g. [4 4], which may be part of a full rule e.g. [[4 4] [5 5]]."
  [rules sub-rule]
  (map (partial apply str)
       (apply combo/cartesian-product
              (map (partial expand-rule-no rules) sub-rule))))

(defn expand-rule
  "Given a full rule e.g. [[4 4] [5 5]] expand and combine each sub-rule i.e. [4 4] and [5 5]."
  [rules rule]
  (mapcat (partial expand-sub-rule rules) rule))

(defn calculate [[rules-input messages-input]]
  (let [rules (apply merge (parse-rules rules-input))
        allowed-messages (expand-rule-no rules "0")]
    (count (filter (set allowed-messages) messages-input))))

(defn run [part]
  (case part
    :part1 (calculate input)
    :part2 (calculate input)))
