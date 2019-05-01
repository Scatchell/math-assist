(ns math-assist.datastore.equations
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [math-assist.conf :refer [config]]
            ))

(def collection "equations")

(defn save [equations]
  (let [conn (mg/connect)
        db (mg/get-db conn (:database config))
        coll collection]
    (doseq [eqn equations] (mc/insert db coll eqn))))

(defn get-all []
  (let [conn (mg/connect)
        db (mg/get-db conn (:database config))
        coll collection]

    (mc/find-maps db coll)))
