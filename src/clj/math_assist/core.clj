(ns math-assist.core
  (:use [ring.middleware reload params resource file-info]
        [ring.adapter.jetty :only [run-jetty]]
        [hiccup.page :only [include-js include-css html5]]
        [hiccup.element :only [javascript-tag]])
  (:require [clj-json.core :as json]))

(defmacro render-html5 [& elts]
  `{:status  200
    :headers {"Content-Type" "text/html; charset=UTF-8"}
    :body    (html5 ~@elts)})

(defn render-notfound [req]
  {:status  404
   :headers {"Content-Type" "text/plain"}
   :body    (str "path not found: " (:uri req))})

(defn- render-index [req]
  (render-html5
    [:head
     [:title "test!"]
     (include-css "/css/styles.css")]
    [:body
     [:div#main "Welcome!"]
     (include-js "//cdnjs.cloudflare.com/ajax/libs/jquery/3.4.0/jquery.js"
                 "//cdnjs.cloudflare.com/ajax/libs/json2/20160511/json2.js"
                 "/js/gen/dev/goog/base.js"
                 "/js/gen/dev/main.js")
     (javascript-tag "goog.require('hello_world')")]))

(defn- ring-handler [req]
  (let [hf (case (req :uri)
             "/" render-index
             render-notfound)]
    (hf req)))


(def ^:private ring-app*
  (-> #'ring-handler
      (wrap-resource "web")
      ;todo wrap-file-info deprecated
      (wrap-file-info)
      (wrap-params)))

(defn -main []
  (run-jetty #'ring-app* {:port 8080 :join? false}))
