(ns aoc2017.p25.sol
  (:require [clojure.string :as s]))

(def data (->> (slurp "src/aoc2017/p25/input.txt")
               (re-seq #"\d+|[A-F][.|:]|left|right")
               (map #(s/replace % #"\.|:" ""))))

(def start-state (nth data 0))
(def num-steps (Integer/parseInt (nth data 1)))
(def states (into {} (map #(vector (first %)
                                   (into {} (vector (vector (nth (rest %) 0)
                                                            (take 3 (drop 1 (rest %))))
                                                    (vector (nth (rest %) 4)
                                                            (take 3 (drop 5 (rest %)))))))
                          (partition 9 (drop 2 data)))))
(def tape (atom {}))

(defn part-one []
  (loop [i 0  pos 0  state start-state]
    (if (= i num-steps)
      (count (filter #(= "1" %) (vals @tape)))
      (let [tasks (get-in states [state (@tape pos "0")])]
        (swap! tape assoc pos (nth tasks 0))
        (recur (inc i)
               (if (= "right" (nth tasks 1)) (inc pos) (dec pos))
               (nth tasks 2))))))

(defn part-two [] "Merry Christmas")
