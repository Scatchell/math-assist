(ns math-assist.equations
  (:require [math-assist.random :as random]
            [clojure.string :as str]))

(def types {"*" *})

(defn equation
  "Generate an equation given a specific configuration, including:
   :type - which math operation to perform
   :numbers - how many numbers to generate in the equation
   :max - max size of each randomly generated number"
  [conf]
  (let [num-of-numbers (:numbers conf)
        action (types (:type conf))
        numbers (repeatedly num-of-numbers #(random/number conf))
        result (reduce action numbers)
        ]
    {:type    "*"
     :numbers numbers
     :answer  result})
  )

(defn- remove-last-char [string]
  (apply str (drop-last string)))

(defn eqn-to-object
  "convert equation to clojure edn representation"
  [eqn]
  (let [numbers (reverse (:numbers eqn))
        result (reduce (types (:type eqn)) numbers)
        eqn-string (reduce (fn [eqn-string num]
                             (str num (:type eqn) eqn-string))
                           ""
                           numbers)]
    {:equation (remove-last-char eqn-string)
     :answer   result}
    )
  )

;todo currently only works for multiplication
(defn config-from-equation [equation]
  "convert equation string to configuration
  which represents equation (for statistical analysis)"
  (let [type "*"
        numbers (+ 1 (count (re-seq #"\*" equation)))
        max (apply max (map #(Integer/parseInt %) (str/split equation #"\*")))]
    {:type    type
     :numbers numbers
     :max     max}))

(defn eqn-to-string
  "convert equation to string representation
  given a specific configuration, including:
  :show-result - whether or not to show the answer in the string"
  [conf eqn]
  (let [numbers (reverse (:numbers eqn))
        result (reduce (types (:type eqn)) numbers)
        eqn-string (reduce (fn [eqn-string num]
                             (str num (:type eqn) eqn-string))
                           ""
                           numbers)]
    (let [equation (remove-last-char eqn-string)]
      (if (:show-result conf)
        (str equation "=" result)
        equation)))
  )
