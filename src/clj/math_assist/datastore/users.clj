(ns math-assist.datastore.users
  (:require [monger.collection :as mc]
            [math-assist.database :refer [db]]
            ))

(def collection "users")

(defn save-user [user]
  (mc/upsert db collection {:user-id (:user-id user)} user))

(defn get-user [user-id]
  (let [users (mc/find-maps db collection {:user-id user-id})]
    (if (<= (count users) 1)
      (first users)
      nil)))
