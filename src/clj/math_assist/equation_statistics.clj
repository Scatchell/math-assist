(ns math-assist.equation-statistics
  (:require [math-assist.equations :refer [config-from-equation]]))

(defn- category-of-number [number range]
  (let [difference-from-nearest-ten (- number (mod number range))]
    {:start difference-from-nearest-ten
     :end   (+ range difference-from-nearest-ten)})
  )

(defn- group-to-number-categories [range equation]
  (let [max-number-in-equation (:max (config-from-equation (:equation equation)))]
    (category-of-number max-number-in-equation range)))

(defn- correctness-of-eqns [equations]
  (let [percentage-correct (/ (count (filter #(:correct %) equations)) (count equations))
        correctness (* 100 (with-precision 3 (bigdec percentage-correct)))]
    correctness))

(defn- correctness-of-eqn-category [[number-category eqns-in-category]]
  (let [correctness (correctness-of-eqns eqns-in-category)]
    (merge number-category {:correctness correctness})))

(defn correctness [{:keys [equations range] :or {range 10}}]
  (let [groups (group-by (partial group-to-number-categories range) equations)]
    (map correctness-of-eqn-category groups)))

(defn difficulty-assessment [{:keys [equations split-point alteration-range] :or {split-point 80M alteration-range 5}}]
  (let [correctness (correctness-of-eqns equations)]
    (if (> correctness (+ split-point alteration-range))
      :increase
      (if (< correctness (- split-point alteration-range))
        :decrease
        :no-change)))

  )


