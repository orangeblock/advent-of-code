(ns aoc2017.p13.sol)

(def data (->> (slurp "src/aoc2017/p13/input.txt")
               (re-seq #"\d+")
               (map #(Integer/parseInt %))
               (partition 2)
               (mapv #(apply vector %))
               (into {})))

(defn- find-detections
  ([wait] (find-detections wait false))
  ([wait first-only]
   (reduce
    (fn [acc k]
      (let [r (data k)
            scanner-pos (mod (+ wait k) (* 2 (dec r)))]
        (if (zero? scanner-pos)
          (if first-only
            (reduced [* k r])
            (conj acc (* k r)))
          acc)))
    []
    (keys data))))

(defn part-one []
  (reduce + (find-detections 0)))
  
(defn part-two []
  (loop [ps 0]
    (if (empty? (find-detections ps true))
      ps
      (recur (inc ps)))))
