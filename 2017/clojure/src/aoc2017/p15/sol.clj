(ns aoc2017.p15.sol
  (:require [clojure.string :as s]))

(def data (->> (slurp "src/aoc2017/p15/input.txt")
               (re-seq #"\d+")
               (map #(Integer/parseInt %))))

(defn- generator [seed factor]
  (drop 1 (reductions 
            (fn [acc _] (mod (* acc factor) 2147483647))
            seed 
            ; using dummy infinite seq to keep the reductions going
            (repeat 1))))

(defn gen-a [] (generator (first data) 16807))
(defn gen-b [] (generator (second data) 48271))

(defn- int2bin [i] 
  (s/replace (format "%32s" (str (Integer/toBinaryString i))) #" " "0"))

(defn- bin-eq? [a b]
  "Compare the 16 lowest bits in the 32-bit binary representation of ints a and b."
  (= (subs (int2bin a) 16) (subs (int2bin b) 16)))

(defn part-one []
  (->> (interleave (gen-a) (gen-b))
       (partition 2)
       (take 40000000)
       (filter #(apply bin-eq? %))
       (count)))

(defn part-two [] 
  (->> (interleave (filter #(zero? (mod % 4)) (gen-a))
                   (filter #(zero? (mod % 8)) (gen-b)))
       (partition 2)
       (take 5000000)
       (filter #(apply bin-eq? %))
       (count)))
