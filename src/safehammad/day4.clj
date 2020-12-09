(ns safehammad.day4
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.set :as set])
  (:gen-class))

(def input
  "Series of entries from input file. Entries split on blank line."
  (str/split
    (slurp (io/resource "day4-input.txt")) #"\n\n"))  ; day4-input-example.txt: 4 valid, 4 invalid

(def required-fields #{"hgt" "pid" "byr" "eyr" "iyr" "ecl" "hcl"})  ; exclude "cid"

(defn valid-height?
  [height]
  (let [[_ value unit] (re-find #"(\d{2,3})(cm|in)" height)]
    (cond
      (= "in" unit) (<= 59 (Integer/parseInt value) 76)
      (= "cm" unit) (<= 150 (Integer/parseInt value) 193))))

(def validators {"byr" (fn [value] (<= 1920 (Integer/parseInt value) 2002))
                 "iyr" (fn [value] (<= 2010 (Integer/parseInt value) 2020))
                 "eyr" (fn [value] (<= 2020 (Integer/parseInt value) 2030))
                 "hgt" valid-height?
                 "hcl" (fn [value] (re-matches #"#[0-9a-f]{6}" value))
                 "ecl" (fn [value] (contains? #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"} value))
                 "pid" (fn [value] (re-matches #"\d{9}" value))
                 "cid" (constantly true)})

(defn ->fields
  "Split a passport line into fields e.g. [\"iyr:2013\" \"ecl:amb\" ,,,]."
  [line]
  (str/split line #"\s"))

(defn ->field-pairs
  "Given fields, return field pairs e.g. [[\"iyr\" \"2019\"] [\"ecl\" \"brn\"] ,,,]."
  [fields]
  (vec (map #(str/split % #":") fields)))

(defn fields-present?
  "Return true if all required fields are present."
  [field-pairs]
  (set/subset? required-fields (set (map first field-pairs))))

(defn field-valid?
  "Given a field, validate the value accordingly."
  [[field value]]
  (let [validator (get validators field (constantly false))]
    (validator value)))

(defn passport-valid?
  "Passport validation for part 2."
  [field-pairs]
  (->> field-pairs
       (map field-valid?)
       (every? identity)))

(defn count-passports
  "Return number of valid passports."
  [input validate-fn]
  (->> input
       (map ->fields)
       (map ->field-pairs)
       (filter fields-present?)
       (filter validate-fn)
       (count)))

(defn run
  "Count valid passports."
  [part]
  (cond
    (= :part1 part) (count-passports input identity)
    (= :part2 part) (count-passports input passport-valid?)))
