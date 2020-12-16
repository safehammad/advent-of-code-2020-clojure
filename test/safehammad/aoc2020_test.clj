(ns safehammad.aoc2020-test
  (:require [clojure.test :refer :all]
            [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]
            [safehammad.day3 :as day3]
            [safehammad.day4 :as day4]
            [safehammad.day5 :as day5]
            [safehammad.day6 :as day6]
            [safehammad.day7 :as day7]
            [safehammad.day8 :as day8]
            [safehammad.day9 :as day9]
            [safehammad.day10 :as day10]))

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

(deftest day5-test
  (testing "Day 5, part 1."
    (is (= 880 (day5/run :part1))))
  (testing "Day 5, part 2."
    (is (= 731 (day5/run :part2)))))

(deftest day6-test
  (testing "Day 6, part 1."
    (is (= 6782 (day6/run :part1))))
  (testing "Day 6, part 2."
    (is (= 3596 (day6/run :part2)))))

(deftest day7-test
  (testing "Day 7, part 1."
    (is (= 197 (day7/run :part1))))
  (testing "Day 7, part 2."
    (is (= 85324 (day7/run :part2)))))

(deftest day8-test
  (testing "Day 8, part 1."
    (is (= 1675 (day8/run :part1))))
  (testing "Day 8, part 2."
    (is (= 1532 (day8/run :part2)))))

(deftest day9-test
  (testing "Day 9, part 1."
    (is (= 144381670 (day9/run :part1))))
  (testing "Day 9, part 2."
    (is (= 20532569 (day9/run :part2)))))

(deftest day10-test
  (testing "Day 10, part 1."
    (is (= 2046 (day10/run :part1))))
  (testing "Day 10, part 2."
    (is (= 1157018619904 (day10/run :part2)))))
