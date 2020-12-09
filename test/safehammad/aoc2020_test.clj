(ns safehammad.aoc2020-test
  (:require [clojure.test :refer :all]
            [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]
            [safehammad.day3 :as day3]
            [safehammad.day4 :as day4]))

(deftest day1-test
  (testing "Day 1, part 1."
    (is (= 181044 (day1/run :part1))))
  (testing "Day 1, part 2."
    (is (= 82660352 (day1/run :part2)))))

(deftest day2-test
  (testing "Day 2, part 1."
    (is (= 447 (day2/run :part1))))
  (testing "Day 2, part 2."
    (is (= 249 (day2/run :part2)))))

(deftest day3-test
  (testing "Day 3, part 1."
    (is (= 237 (day3/run :part1))))
  (testing "Day 3, part 2."
    (is (= 2106818610 (day3/run :part2)))))

(deftest day4-test
  (testing "Day 4, part 1."
    (is (= 242 (day4/run :part1))))
  (testing "Day 4, part 2."
    (is (= 186 (day4/run :part2)))))
