(ns math-assist.conf
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(require '[mount.core :refer [defstate]])

(defn config-values [source]
  (with-open [r (io/reader (io/resource source))]
    (edn/read (java.io.PushbackReader. r))))

(defstate config :start (config-values "./config.edn"))
