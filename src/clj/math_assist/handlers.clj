(ns math-assist.handlers
  (:use [hiccup.page :only [include-js include-css html5]]
        [hiccup.element :only [javascript-tag]])
  (:require [math-assist.equations :as eqns]
            [math-assist.datastore.equations :as equations]
            [math-assist.equation-statistics :as eqn-stats]))

;todo automatically changing difficulty level based on player performance
;todo add timer to game! I have some thoughts about this: Possibly simply consider an answer wrong if it surpasses a certain time limit as a simple start? More complex would be to look at a deviation from the mean of time to answer questions, and the more the deviation the lower a "correctness" score until it reaches the point of 0, meaning essentially incorrect. The timer should not be something that makes people nervous while answering questions.
;todo Page to display statistics? (maybe for later after more statistics are added)

(defmacro render-html5 [& elts]
  `{:status  200
    :headers {"Content-Type" "text/html; charset=UTF-8"}
    :body    (html5 ~@elts)})

(defn- generate-equation []
  (eqns/eqn-to-object
    (eqns/equation {:type    "*"
                    :numbers 2
                    :max     16
                    :min     5})))

(defn save-answers [req]
  (let [equations (get-in req [:params "equations"])
        user-id (get-in req [:params "user-id"])
        eqns-with-user (map #(conj % {:user-id user-id}) equations)]
    (equations/save eqns-with-user))
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    {:status "Saved!"}})

(defn- render-equations [req]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (repeatedly 1 generate-equation)})

(defn- render-notfound [req]
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

(defn- get-statistics [req]
  (let [user-id (get-in req [:params "user-id"])
        range (get-in req [:params "range"])
        eqns-for-stats {:equations (equations/get-by-user user-id)}]
    {:status  200
     :headers {"Content-Type" "application/json"}
     :body    (eqn-stats/correctness (if range
                                       (assoc eqns-for-stats :range range)
                                       eqns-for-stats))}))

(defn math-assist-handlers [req]
  (let [hf (case (req :uri)
             "/" render-index
             "/equations" render-equations
             "/answers" save-answers
             "/statistics" get-statistics
             render-notfound)]
    (hf req)))

