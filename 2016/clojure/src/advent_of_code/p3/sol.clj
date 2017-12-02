(ns advent-of-code.p3.sol
  (:require [clojure.string :refer [split-lines]]))

(def data 
  (map 
    (fn [s] (map #(Integer/parseInt %) (re-seq #"\d+" s)))
    (split-lines (slurp "src/advent_of_code/p3/input.txt"))))

(defn valid-triangle?
  [[a b c]]
  (and
    (> (+ a b) c)
    (> (+ b c) a)
    (> (+ a c) b)))

(defn part-one []
  (count (filter valid-triangle? data)))

(defn part-two []
  (->> data
       (apply interleave)
       (partition 3)
       (filter valid-triangle?)
       (count)))
