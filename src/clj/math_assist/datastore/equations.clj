(ns math-assist.datastore.equations
  (:require [monger.collection :as mc]
            [math-assist.database :refer [db]]
            ))

(def collection "equations")

(defn save [equations]
  (doseq [eqn equations] (mc/insert db collection eqn)))

(defn get-all []
  (mc/find-maps db collection))
