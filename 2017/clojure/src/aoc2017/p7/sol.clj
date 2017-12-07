(ns aoc2017.p7.sol
  (:require [clojure.string :refer [split-lines]]))

(def nodes
  (->> (slurp "src/aoc2017/p7/input.txt")
       (split-lines)
       (map #(re-seq #"\w+" %))
       (map #(vector (first %) (rest %)))
       (into {})))

(defn node-children [n]
  (rest (nodes n)))

(defn node-weight [n]
  (Integer/parseInt (first (nodes n))))

; This assumes there is a single error!!1!
(defn find-error [coll]
  (if (= 2 (count (distinct coll)))
    (let [occurrences (map #(count (filter #{%} coll)) coll)
          error-idx   (.indexOf occurrences 1)
          correct-idx (.indexOf occurrences (apply max occurrences))]
      [error-idx (- (nth coll correct-idx) (nth coll error-idx))])
    nil))

;;
; Recursive function that traverses the tree starting at `parent`
; and finds "fixes" for the unbalanced subtrees.
;
; Returns a vec of the total tree weight (without the fixes) and a list of
; the fixes found (last to first). In reality and for this particular case
; only the first fix matters since the rest are just propagation of the first
; one. 
; 
; I'm really not happy with this function and I feel I'm missing some easy
; way to traverse the tree and apply a predicate that checks for errors.
; Also I couldn't find a way to "exit early", in this case when I find the first
; error. If anyone is reading this and has a clue, hit me up with a PR ;)
;;
(defn traverse-n-fix [parent]
  (let [ child-results (map #(traverse-n-fix %) (node-children parent))
         child-weights (map first child-results)
         child-fixes (apply concat (map second child-results))
         subtree-weight (reduce + (node-weight parent) child-weights)
         [err-idx correction] (find-error child-weights)
         fix (if (some? correction) 
               (+ correction (node-weight (nth (node-children parent) err-idx)))) ]
    (cond
      (empty? child-weights) 
        [subtree-weight '()]
      (some? fix) 
        [subtree-weight (cons fix child-fixes)]
      :else 
        [subtree-weight child-fixes])))

(defn part-one []
  (let [val-set (->> (vals nodes)
                     (map rest)
                     (filter seq)
                     (mapcat identity)
                     (set))]
    (first (filter #(nil? (val-set %)) (keys nodes)))))

(defn part-two []
  (last (second (traverse-n-fix (part-one)))))
