(ns aoc2017.p19.sol
  (:require [clojure.string :refer [split-lines join]]))

(def data (mapv vec (split-lines (slurp "src/aoc2017/p19/input.txt"))))

(defn- get-root []
  (->> (range (count (data 0)))
       (interleave (repeat 0))
       (partition 2)
       (mapv #(apply vector %))
       (filter #(not= \space (get-in data %)))
       (first)))

(defn- vec- [[x1 y1] [x2 y2]] [(- x1 x2) (- y1 y2)])
(defn- vec+ [[x1 y1] [x2 y2]] [(+ x1 x2) (+ y1 y2)])

(defn- neighbors [[x y]]
  [[(inc x) y] [(dec x) y] [x (inc y)] [x (dec y)]])

(defn- path-neighbors [pos]
  "Returns the neighbors that contain a path"
  (filter #(not= \space (get-in data %)) (neighbors pos)))

(defn walk-maze []
  (loop [cpos    (get-root)  
         ppos    (update cpos 0 dec)
         letters []  
         moves   1]
    (let [valid-moves (filter #(not= ppos %) (path-neighbors cpos))
          cval        (get-in data cpos)
          new-letters (if (Character/isUpperCase cval)
                        (conj letters cval)
                        letters)]
      (cond
        (empty? valid-moves)
          ; reached the end of the maze
          [(join new-letters) moves]
        (= \+ (get-in data cpos))
          ; at a junction - there is only one valid move
          (recur (first valid-moves) cpos new-letters (inc moves))
        :else
          ; continue in the same direction
          (recur (vec+ cpos (vec- cpos ppos)) cpos new-letters (inc moves))))))

(defn part-one []
  (first (walk-maze)))

(defn part-two []
  (second (walk-maze)))
