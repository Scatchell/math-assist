(ns math-assist.core
  (:use [math-assist.handlers :only [ring-handler]]
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware reload params resource file-info json]
        )
  (:require [mount.core :as mount]
            ))

(def ^:private ring-app*
  (-> #'ring-handler
      (wrap-resource "web")
      (wrap-resource "externs")
      ;todo wrap-file-info deprecated
      (wrap-file-info)
      (wrap-json-body)
      (wrap-json-response)
      (wrap-params)))

(defn -main []
  (mount/start)
  (run-jetty #'ring-app* {:port 8080 :join? false}))
