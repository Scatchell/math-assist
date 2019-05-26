(ns math-assist.equation-statistics
  (:require [math-assist.equations :refer [config-from-equation]]))

(defn category-of-number [number]
  (let [difference-from-nearest-ten (- number (mod number 10))]
    {:start difference-from-nearest-ten
     :end   (+ 10 difference-from-nearest-ten)})
  )

(defn- group-to-number-categories [equation]
  (let [max-number-in-equation (:max (config-from-equation (:equation equation)))]
    (category-of-number max-number-in-equation)))

(defn- correctness-of-eqn-category [[number-category eqns-in-category]]
  (let [percentage-correct (/ (count (filter #(:correct %) eqns-in-category)) (count eqns-in-category))
        correctness (* 100 (with-precision 3 (bigdec percentage-correct)))]
    (merge number-category {:correctness correctness})))

(defn correctness [equations]
  (let [groups (group-by group-to-number-categories equations)]
    (map correctness-of-eqn-category groups)))



