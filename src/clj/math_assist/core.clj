(ns math-assist.core
  (:use [math-assist.handlers :only [math-assist-handlers]]
        [ring.adapter.jetty :only [run-jetty]]
        [ring.middleware reload params resource file-info json]
        )
  (:require [mount.core :as mount]
            ))

(def ^:private ring-app*
  (-> #'math-assist-handlers
      (wrap-resource "web")
      (wrap-resource "externs")
      ;todo wrap-file-info deprecated
      (wrap-file-info)
      (wrap-json-params)
      (wrap-json-response)
      (wrap-params)))

(defn -main []
  (mount/start)
  (run-jetty #'ring-app* {:port 8080 :join? false}))
