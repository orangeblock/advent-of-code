(ns aoc2017.p9.sol
  (:require [clojure.string :refer [trim]]))

(def data (trim (slurp "src/aoc2017/p9/input.txt")))

(defn parse-group [group-str]
  (reduce
    (fn [[score gchars level in-garbage ignore] c]
      (cond
        ignore     [score gchars level in-garbage (not ignore)]
        (= c \!)   [score gchars level in-garbage true]
        in-garbage (if (= c \>)
                     [score gchars level (not in-garbage) ignore]
                     [score (inc gchars) level in-garbage ignore])
        (= c \<)   [score gchars level true ignore]
        (= c \{)   [score gchars (inc level) in-garbage ignore]
        (= c \})   [(+ score level) gchars (dec level) in-garbage ignore]
        :else      [score gchars level in-garbage ignore]))
    [0, 0, 0, false, false]
    group-str))

(defn part-one []
  (first (parse-group data)))

(defn part-two []
  (second (parse-group data)))
