(ns safehammad.aoc2020-test
  (:require [clojure.test :refer :all]
            [safehammad.day1 :as day1]
            [safehammad.day2 :as day2]))

(deftest day1-test
  (testing "Day 1, part 1."
    (is (= 181044 (day1/run 2))))
  (testing "Day 1, part 2."
    (is (= 82660352 (day1/run 3)))))

(deftest day2-test
  (testing "Day 2, part 1."
    (is (= 447 (day2/run 1))))
  (testing "Day 2, part 2."
    (is (= 249 (day2/run 2)))))
