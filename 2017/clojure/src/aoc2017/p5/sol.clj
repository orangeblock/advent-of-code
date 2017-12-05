(ns aoc2017.p5.sol
  (:require [clojure.string :refer [split-lines]]))

(def data (vec (map #(Integer/parseInt %)
               (split-lines (slurp "src/aoc2017/p5/input.txt")))))

;;
; Clean, but slow, since we are generating a new vector on each step.
;;
(defn part-one []
  (loop [idx 0  steps 0  jump-vec data]
    (if (< -1 idx (count jump-vec))
      (recur (+ idx (jump-vec idx))
             (inc steps)
             (update jump-vec idx inc))
      steps)))

;;
; Execution time is much slower here, so we use transients.
;;
(defn part-two []
  (loop [idx 0  steps 0  jump-vec (transient data)]
    (if (< -1 idx (count jump-vec))
      (let [offset     (jump-vec idx)
            new-offset (if (< offset 3) (inc offset) (dec offset))]
        (recur (+ idx offset)
               (inc steps)
               (assoc! jump-vec idx new-offset)))
      steps)))
