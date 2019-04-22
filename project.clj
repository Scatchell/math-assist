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
                 ; CLJS deps
                 [crate "0.2.5"]
                 [jayq "2.5.0"]
                 [cljs-ajax "0.8.0"]
                 [org.clojure/clojurescript "1.10.520"]]
  :cljsbuild {
              :builds [{
                        :source-paths ["src-cljs"]
                        :compiler     {:output-to     "resources/web/js/gen/dev/main.js"
                                       :output-dir    "resources/web/js/gen/dev"
                                       :optimizations :none
                                       :pretty-print  true}}]}
  :main math-assist.core)
