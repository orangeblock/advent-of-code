(ns aoc2017.p11.sol
  (:require [clojure.string :refer [trim split]]))

; http://devmag.org.za/2013/08/31/geometry-with-hex-coordinates/

(def data (split (trim (slurp "src/aoc2017/p11/input.txt")) #","))
(def hex-vectors {"n"  [1 0 -1]
                  "nw" [0 1 -1]
                  "sw" [-1 1 0]
                  "s"  [-1 0 1]
                  "se" [0 -1 1]
                  "ne" [1 -1 0]})

(defn hex-distance 
  [[x1 y1 z1] [x2 y2 z2]]
  (/ (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2)) (Math/abs (- z1 z2))) 2))

(defn hex-move [loc direction]
  (mapv + loc (hex-vectors direction)))

(defn part-one []
  (hex-distance [0 0 0] (reduce hex-move [0 0 0] data)))

(defn part-two []
  (->> data
       (reduce 
         (fn [moves direction] (vec (cons (hex-move (first moves) direction) moves)))
         [[0 0 0]])
       (map (partial hex-distance [0 0 0]))
       (apply max)))
