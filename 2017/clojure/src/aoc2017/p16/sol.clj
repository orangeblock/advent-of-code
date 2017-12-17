(ns aoc2017.p16.sol
  (:require [clojure.string :as s]))

(def operations (->> (s/split (s/trim (slurp "src/aoc2017/p16/input.txt")) #",")
                     (map #(re-seq #"(s|p|x)(\w+)/?(\w+)?" %))
                     (map #(rest (first %)))))
(def init-state ["a" "b" "c" "d" "e" "f" "g" "h" "i" "j" "k" "l" "m" "n" "o" "p"])

(defn- spin [xs n] (vec (concat (take-last n xs) (take (- (count xs) n) xs))))
(defn- exchange [xs i j] (assoc xs i (xs j) j (xs i)))
(defn- partner [xs a b] (exchange xs (.indexOf xs a) (.indexOf xs b)))

(defn- permute [xs]
  (reduce
    (fn [state [op x y]]
      (case op
        "s" (spin state (Integer/parseInt x))
        "x" (exchange state (Integer/parseInt x) (Integer/parseInt y))
        "p" (partner state x y)))
    xs
    operations))

(defn- permutation-cycle [xs]
  (drop-last 
    (reductions (fn [state _]
                  (let [new-state (permute state)]
                    (if (= new-state xs)
                      (reduced new-state)
                      new-state)))
                xs
                (repeat 1))))

(defn part-one [] 
  (s/join (permute init-state)))

(defn part-two []
  (let [c (permutation-cycle init-state)]
    (s/join (nth c (mod 1000000000 (count c))))))
