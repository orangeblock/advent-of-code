(ns aoc2017.core
  (:require [aoc2017.p24.sol :as p])
  (:gen-class))

(defn -main
  [& args]
  (do
    (println (p/part-one))
    (println (p/part-two))))
