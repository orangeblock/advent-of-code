(ns aoc2017.p17.sol)

(def input 371)

(defn insert-after [xs i x]
  "Retuns a seq with x inserted after index i in xs"
  (concat (take (inc i) xs) (cons x (drop (inc i) xs))))

(defn part-one []
  (loop [buf [0]  pos 0  v 1]
    (if (> v 2017)
      (nth buf (inc pos))
      (let [new-idx (mod (+ pos input) v)]
        (recur (vec (insert-after buf new-idx v))
               (inc new-idx)
               (inc v))))))

(defn part-two []
  ; only keeps the value of the second element
  (loop [pos 0  v 1  after nil]
    (if (> v 50000000)
      after
      (let [new-idx (mod (+ pos input) v)]
        (if (zero? new-idx)
          (recur (inc new-idx) (inc v) v)
          (recur (inc new-idx) (inc v) after))))))
