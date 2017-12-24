(ns aoc2017.p24.sol)

; Recursion + 100% immutable, so it's quite slow,
; plus part-two rebuilds all the bridges.

(def data (->> (slurp "src/aoc2017/p24/input.txt")
               (re-seq #"\d+")
               (map #(Integer/parseInt %))
               (partition 2)))

(defn build-bridges
  ([ports] (build-bridges nil ports 0))
  ([bridge ports open]
   (let [next-ports (filter #(some #{open} %) ports)]
     (if (empty? next-ports)
       (list bridge)
       (apply concat
              (map (fn [port]
                     (build-bridges (cons port bridge)
                                    (filter #(not= port %) ports)
                                    (first (or (seq (filter #(not= open %) port))
                                               [open]))))
                   next-ports))))))

(defn part-one []
  (apply max (map #(reduce + (flatten %)) (build-bridges data))))

(defn part-two []
  (let [bridges (build-bridges data)
        max-len (apply max (map count bridges))]
    (->> bridges
         (filter #(= max-len (count %)))
         (map #(reduce + (flatten %)))
         (apply max))))
