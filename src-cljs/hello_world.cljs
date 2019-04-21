(ns hello-world
  (:use [jayq.core :only [$ css html document-ready]])
  (:require [crate.core :as crate]
            [jayq.util :as util]))

(defn- main []
  (util/log "main")

  (html ($ "#main")
        (crate/html
          [:div
           [:div {:id "test"}]
           [:input {:style "display: block; border: 2px solid #66cc33;"
                    :id    "test-input"
                    :type  "text"}]])))

(document-ready
  (fn []
    (util/log "ready...")
    (main)))

