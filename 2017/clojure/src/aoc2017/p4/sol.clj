(ns aoc2017.p4.sol
  (:require [clojure.string :refer [split-lines]]))

(def data 
  (map #(re-seq #"\w+" %) 
       (split-lines (slurp "src/aoc2017/p4/input.txt"))))

(defn part-one []
  (count (filter #(apply distinct? %) data)))

(defn part-two []
  (->> data
       (map #(map sort %))
       (filter #(apply distinct? %))
       (count)))
