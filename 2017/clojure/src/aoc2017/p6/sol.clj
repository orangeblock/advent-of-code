(ns aoc2017.p6.sol)

(def data 
  (vec (map #(Integer/parseInt %)
            (re-seq #"\d+" (slurp "src/aoc2017/p6/input.txt")))))

;;
; Spreads the blocks located on start-idx.
; Super efficient, since it pre-calculates the total amount
; to be added to each bank.
;;
(defn- spread-blocks [banks-vec start-idx]
  (let [len      (count banks-vec)
        amount   (banks-vec start-idx)
        cycles   (int (/ amount len))
        leftover (mod amount len)
        vec-pos  #(inc (mod (- % (inc start-idx)) len))]
    (vec (map-indexed
           (fn [i x] (+ x cycles (if (< leftover (vec-pos i)) 0 1)))
           (assoc banks-vec start-idx 0)))))

;;
; Returns the duplicate state, the step of its first occurrence,
; and the total amount of steps until the duplicate, which is all
; the data we need to solve both parts.
;;
(defn- find-loop [initial-state]
  (loop [state initial-state  steps 0  tm (transient {})]
    (let [k (keyword (str state))]
      (if (nil? (k tm))
        (recur 
          (spread-blocks state (.indexOf state (apply max state)))
          (inc steps)
          (assoc! tm k steps))
        [[k (k tm)] steps]))))

(defn part-one []
  (last (find-loop data)))

(defn part-two []
  (let [[[state step-found] total-steps] (find-loop data)]
    (- total-steps step-found)))
