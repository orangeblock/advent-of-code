(ns aoc2017.p21.sol
  (:require [clojure.string :refer [split-lines split]]))

; No idea about actual efficient algorithm for this, so just a naive implementation.
; Takes a while on part two.

(defn flip-horizontal [grid] (mapv #(vec (reverse %)) grid))
(defn flip-vertical [grid] (vec (reverse grid)))
(defn flip-d1 [grid] (mapv vec (partition (count grid) (apply interleave grid))))
(defn flip-d2 [grid] (vec (reverse (flip-d1 (reverse grid)))))
(defn rotate-ccw [grid]
  (mapv vec (reverse (partition (count grid) (apply interleave grid)))))

(defn grid-variations [grid]
  (->> (take 4 (iterate rotate-ccw grid))
       (cons (flip-vertical grid))
       (cons (flip-horizontal grid))
       (cons (flip-d1 grid))
       (cons (flip-d2 grid))
       (distinct)))

; HOLY MOTHER OF FUNCTIONS
(def data (->> (slurp "src/aoc2017/p21/input.txt")
               (re-seq #"[/.#]+")
               (map #(mapv vec (split % #"/")))
               (partition 2)
               (map (fn [[in out]] (interleave (grid-variations in) (repeat out))))
               (apply concat)
               (partition 2)
               (mapv vec)
               (into {})))

(defn partition-grid [n grid]
  (let [partitions (partition n (flatten grid))
        sub-size (/ (count grid) n)]
    (->> (iterate next partitions)
         (take sub-size)
         (map #(take-nth sub-size %))
         (apply concat)
         (map vec)
         (partition n)
         (map vec))))

(defn split-grid [grid]
  (let [size (count grid)]
    (cond
      (or (= size 2) (= size 3))
        (list grid)
      (= 0 (mod size 2))
        (apply concat (map split-grid (partition-grid 2 grid)))
      :else
        (apply concat (map split-grid (partition-grid 3 grid))))))

(defn fuse-grid [sub-grids]
  (let [sub-size (count (ffirst sub-grids))
        row-size (int (* sub-size (Math/sqrt (count sub-grids))))
        partitions (partition sub-size (flatten sub-grids))]
    (->> (iterate next partitions)
         (take row-size)
         (map #(take-nth row-size %))
         (flatten)
         (partition row-size)
         (mapv vec))))

(defn enhance-grid
  "CSI grid that can always enhance more ;)"
  [n grid]
  (nth (iterate 
         (fn [grid] (->> (split-grid grid)
                         (map #(data %))
                         (fuse-grid)))
         grid)
       n))

(defn part-one []
  (count (filter #(= \# %) (flatten (enhance-grid 5 [[\. \# \.] [\. \. \#] [\# \# \#]])))))

(defn part-two []
  (count (filter #(= \# %) (flatten (enhance-grid 18 [[\. \# \.] [\. \. \#] [\# \# \#]])))))
