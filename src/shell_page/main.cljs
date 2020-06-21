
(ns shell-page.main (:require [shell-page.core :refer [make-page slurp spit]]))

(defn main! []
  (spit
   "target/index.html"
   (make-page
    ""
    {:title "Shell Page Demo",
     :icon "http://logo.mvc-works.org/mvc.png",
     :ssr "respo-ssr",
     :styles ["/main.css"],
     :inline-styles [".app{color:#aaa;}"],
     :scripts ["/main.js" {:type :module, :src "main.js"}],
     :inline-html "<script>console.log('nothing')</script>",
     :append-html "<script>console.log('appended');</script>",
     :manifest "manifest.json"})))
