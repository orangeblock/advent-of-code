(ns aoc2017.p10.sol
  (:require [clojure.string :refer [trim join]]))

(def data (trim (slurp "src/aoc2017/p10/input.txt")))
(def magic-bytes '(17 31 73 47 23))

;;
; I'm pretty sure there's a much simpler way but
; modulo math is hard, so I'll leave it at that.
;;
(defn knot-round 
  [[xs pos offset skip-size _] sublen]
  (let [new-pos    (mod (+ sublen skip-size) 256)
        new-offset (mod (+ offset sublen skip-size) 256)
        new-idx    (mod (- new-pos new-offset) 256)]
    [(cycle (concat (reverse (take sublen (drop pos xs)))
                    (drop (+ pos sublen) (take (+ pos 256) xs))))
     new-pos
     new-offset
     (inc skip-size)
     new-idx]))

(defn knot-hash [s]
  (let [bytes                (concat (map int s) magic-bytes)
        [xs _ _ _ start-idx] (reduce knot-round
                                     [(cycle (range 256)) 0 0 0 0]
                                     (mapcat identity (repeat 64 bytes)))]
    (->> (drop start-idx xs)
         (take 256)
         (partition 16)
         (map #(apply bit-xor %))
         (map #(format "%02x" %))
         (join ""))))

(defn part-one []
  (let [ [xs _ _ _ start-idx] 
          (reduce knot-round 
                  [(cycle (range 256)) 0 0 0] 
                  (map #(Integer/parseInt %) 
                       (re-seq #"\d+" data))) ]
    (* (nth xs start-idx) (nth xs (inc start-idx)))))

(defn part-two []
  (knot-hash data))
