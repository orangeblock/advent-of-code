(ns aoc2017.p22.sol
  (:require [clojure.string :refer [split-lines]]))

(def grid (split-lines (slurp "src/aoc2017/p22/input.txt")))
(def data (atom {}))
(defn reset-data! []
  (->> (let [r (count grid)
             c (count (first grid))
             [cx cy] [(quot r 2) (quot c 2)]]
         (for [i (range r)
               j (range c)]
           [[(- j cx) (- cy i)] (get-in grid [i j])]))
       (into {})
       (reset! data)))
; N -> E -> S -> W
(def dirs [[0 1] [1 0] [0 -1] [-1 0]])

(defn part-one []
  (reset-data!)
  (last 
    (reduce
      (fn [[pos dir inf] i]
        (case (@data pos \.)
          \# (let [new-dir (mod (inc dir) 4)]
               (swap! data assoc pos \.)
               [(mapv + pos (dirs new-dir))  new-dir  inf])
          \. (let [new-dir (mod (dec dir) 4)]
               (swap! data assoc pos \#)
               [(mapv + pos (dirs new-dir))  new-dir  (inc inf)])))
      [[0 0] 0 0]
      (range 10000))))

(defn part-two []
  (reset-data!)
  (last 
    (reduce
      (fn [[pos dir inf] i]
        (case (@data pos \.)
          \# (let [new-dir (mod (inc dir) 4)]
               (swap! data assoc pos \F)
               [(mapv + pos (dirs new-dir))  new-dir  inf])
          \. (let [new-dir (mod (dec dir) 4)]
               (swap! data assoc pos \W)
               [(mapv + pos (dirs new-dir))  new-dir  inf])
          \W (do 
               (swap! data assoc pos \#)
               [(mapv + pos (dirs dir))  dir  (inc inf)])
          \F (let [new-dir (mod (+ 2 dir) 4)]
               (swap! data assoc pos \.)
               [(mapv + pos (dirs new-dir))  new-dir  inf])))
      [[0 0] 0 0]
      (range 10000000))))
