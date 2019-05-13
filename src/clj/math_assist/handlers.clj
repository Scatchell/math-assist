(ns math-assist.handlers
  (:use [hiccup.page :only [include-js include-css html5]]
        [hiccup.element :only [javascript-tag]])
  (:require [math-assist.equations :as eqns]
            [math-assist.datastore.equations :as equations]))

(defmacro render-html5 [& elts]
  `{:status  200
    :headers {"Content-Type" "text/html; charset=UTF-8"}
    :body    (html5 ~@elts)})

(defn- generate-equation []
  (eqns/eqn-to-object
    (eqns/equation {:type    "*"
                    :numbers 2
                    :max     12
                    :min     5})))

(defn save-answers [req]
  (let [equations (get-in req [:params "equations"])]
    (prn "request: " req)
    (equations/save equations))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    {:status "Saved!"}})

(defn render-equations [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (repeatedly 1 generate-equation)})

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
     (include-js "/jquery-3.4.0.min.js"
                 ;"//cdnjs.cloudflare.com/ajax/libs/json2/20160511/json2.js"
                 "/js/gen/dev/goog/base.js"
                 "/js/gen/dev/main.js")
     (javascript-tag "goog.require('math_assist')")]))

(defn ring-handler [req]
  (let [hf (case (req :uri)
             "/" render-index
             "/equations" render-equations
             "/answers" save-answers
             render-notfound)]
    (hf req)))

