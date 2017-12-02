(ns aoc2017.core
  (:require [aoc2017.p2.sol :as p2])
  (:gen-class))

(defn -main
  [& args]
  (do
    (println (p2/part-one))
    (println (p2/part-two))))
