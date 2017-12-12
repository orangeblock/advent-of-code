(ns aoc2017.p12.sol
  (:require [clojure.string :refer [split-lines]]
            [clojure.set :refer [difference union]]))

(def data (->> (slurp "src/aoc2017/p12/input.txt")
               (split-lines)
               (map #(re-seq #"\w+" %))
               (map #(hash-map (first %) (rest %)))
               (apply merge)))

(defn graph-group [graph start-node]
  (loop [seen #{start-node}  next-nodes (set (graph start-node))]
    (let [new-nodes (difference next-nodes seen)]
      (if (empty? new-nodes)
        seen
        (recur 
          (union seen new-nodes)
          (set (mapcat #(graph %) new-nodes)))))))

(defn graph-groups [graph]
  (loop [groups '()  [head & tail] (keys graph)]
    (if (nil? head)
      (vec groups)
      ; Skip nodes that already exist in another group
      (if (some true? (map #(contains? % head) groups))
        (recur groups tail)
        (recur (cons (graph-group graph head) groups) tail)))))

(defn part-one []
  (count (graph-group data "0")))

(defn part-two []
  (count (graph-groups data)))
