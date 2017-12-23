(ns aoc2017.p23.sol
  (:require [clojure.string :refer [split-lines]]))

(def instructions (vec (map #(re-seq #"[-\w]+" %) 
                            (split-lines (slurp "src/aoc2017/p23/input.txt")))))
(def regs (atom {}))

(defn get-val [reg-or-val]
  (if (re-seq #"\d" reg-or-val)
    (Integer/parseInt reg-or-val)
    (@regs reg-or-val 0)))

(defn prime? [n]
  (cond
    (< n 2) false
    (= n 2) true
    (= 0 (mod n 2)) false
    :else (->> (range 3 (inc (Math/sqrt n)) 2)
               (filter #(zero? (mod n %)))
               (first)
               (nil?))))

(defn run [stop-after]
  (loop [ip 0]
    (if (= stop-after ip)
      nil
      (let [[cmd op1 op2] (nth instructions ip nil)]
        (case cmd
          "set" (do
                  (swap! regs assoc op1 (get-val op2))
                  (recur (inc ip)))
          "sub" (do
                  (swap! regs assoc op1 (- (get-val op1) (get-val op2)))
                  (recur (inc ip)))
          "mul" (do
                  (swap! regs assoc op1 (* (get-val op1) (get-val op2)))
                  (swap! regs assoc "z" (inc (get-val "z")))
                  (recur (inc ip)))
          "jnz" (if (zero? (get-val op1))
                  (recur (inc ip))
                  (recur (+ ip (get-val op2))))
          nil)))))

(defn part-one []
  ; I'm using the "z" register to hold the number of multiplications
  (run -1)
  (@regs "z"))

;;
; Deciphering the instructions tells us that the "h" register contains all the 
; non-prime numbers between the number in reg "b" and the number in reg "c",
; with a step of 17. These variables will probably be different for everyone.
;;
(defn part-two [] 
  (reset! regs {"a" 1})
  (run 8)
  (->> (range (@regs "b") (inc (@regs "c")) 17)
       (filter #(not (prime? %)))
       (count)))
