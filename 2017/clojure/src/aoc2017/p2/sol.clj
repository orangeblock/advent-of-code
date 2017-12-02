(ns aoc2017.p2.sol
  (:require [clojure.string :refer [split-lines]]
            [clojure.math.combinatorics :as combo]))

(defn- parse-ints [s]
  (map #(Integer/parseInt %) (re-seq #"\d+" s)))

(def data 
  (map parse-ints
       (split-lines (slurp "src/aoc2017/p2/input.txt"))))

(defn part-one []
  (->> data
       (map #(- (apply max %) (apply min %)))
       (reduce +)))

(defn sorted-mod [[a b]]
  (mod (max a b) (min a b)))

(defn find-first-divisible [coll]
  (->> (combo/combinations coll 2)
       (filter #(= 0 (sorted-mod %)))
       (first)))

(defn part-two []
  (->> data
       (map find-first-divisible)
       (map (fn [[a b]] (/ (max a b) (min a b))))
       (reduce +)))
