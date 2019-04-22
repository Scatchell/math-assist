(ns hello-world
  (:use [jayq.core :only [$ css html document-ready append]])
  (:require [crate.core :as crate]
            [jayq.util :as util]
            [ajax.core :refer [GET POST]]))

(defn- main []
  (util/log "main")

  (html ($ "#main")
        (crate/html
          [:div
           [:div {:id "equations"}
            [:p "equations:"]
            [:ul {:id "equations-list"}]]
           [:input {:style "display: block; border: 2px solid #66cc33;"
                    :id    "test-input"
                    :type  "text"}]])))

(defn- eqn-handler [response]
  (doseq [item response]
    (append ($ "#equations-list")
            (crate/html
              [:li (get item "equation")]))))

(document-ready
  (fn []
    (GET "/equations" {:handler         eqn-handler
                       :response-format :json})
    (util/log "ready...")
    (main)))

