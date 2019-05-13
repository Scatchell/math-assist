(ns math-assist.database
  (:require [math-assist.conf :refer [config]]
            [monger.core :as mg]))

(require '[mount.core :refer [defstate]])

(defstate conn
          :start (mg/connect)
          :stop (mg/disconnect conn))

(defstate db :start (mg/get-db conn (:database config)))
