(ns aoc2017.p8.sol
  (:require [clojure.string :refer [split-lines]]))

(def instructions (map #(re-seq #"[\w<>=!-]+" %) 
                       (split-lines (slurp "src/aoc2017/p8/input.txt"))))

(defn to-fn [string]
  (cond
    (= string "!=") not=
    (= string "dec") -
    (= string "inc") +
    :else (resolve (symbol string))))

(defn execute-step 
  [[reg action-str action-val _ test-reg comp-str test-val] regs]
  (if ((to-fn comp-str) (regs test-reg 0) (Integer/parseInt test-val))
    (assoc regs reg ((to-fn action-str) (regs reg 0) (Integer/parseInt action-val)))
    regs))

(defn part-one []
  (loop [regs {}  steps instructions]
    (if-not (seq steps)
      (apply max (vals regs))
      (recur (execute-step (first steps) regs) (rest steps)))))

(defn part-two []
  (loop [regs {}  steps instructions  max-val 0]
    (if-not (seq steps)
      max-val
      (recur 
        (execute-step (first steps) regs) 
        (rest steps)
        (apply max (cons max-val (vals regs)))))))
