(defproject math-assist "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/clj"]
  :plugins [[lein-cljsbuild "1.1.7"]]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.6.3"]
                 [hiccup "1.0.5"]
                 [clj-json "0.5.3"]
                 [ring/ring-json "0.4.0"]
                 [com.novemberain/monger "3.1.0" :exclusions [com.google.guava/guava]]
                 [mount "0.1.12"]
                 ; CLJS deps
                 [org.clojure/clojurescript "1.10.520"]
                 [crate "0.2.5"]
                 [jayq "2.5.0"]
                 [cljs-ajax "0.8.0"]]
  :cljsbuild {
              :builds [{
                        :source-paths ["src-cljs"]
                        :compiler     {:output-to     "resources/web/js/gen/dev/main.js"
                                       :output-dir    "resources/web/js/gen/dev"
                                       :optimizations :none
                                       :pretty-print  true}}]}
  :profiles {:dev  {:resource-paths ["resources/config/dev/"]}
             :test {:resource-paths ["resources/config/test/"]}
             :prod {:resource-paths ["resources/config/prod/"]}}
  :main math-assist.core)
