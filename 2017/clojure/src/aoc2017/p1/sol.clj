(ns aoc2017.p1.sol
  (:require [clojure.string :refer [trim]]))

(def data (trim (slurp "src/aoc2017/p1/input.txt")))

(defn sum [lazy-seq] (reduce + lazy-seq))

(defn solve-captcha [s step]
  (sum
    (map
      (fn [[i c]] (Character/digit c 10))
      (filter
        (fn [[i c]]
          (= c (nth s (mod (+ i step) (count s)))))
        (map-indexed vector s)))))

(defn part-one []
  (solve-captcha data 1))

(defn part-two []
  (solve-captcha data (/ (count data) 2)))
