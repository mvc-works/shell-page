
(ns shell-page.main (:require [shell-page.core :refer [make-page slurp spit]]))

(defn main! []
  (spit
   "target/index.html"
   (make-page
    ""
    {:title "Shell page",
     :icon "http://logo.mvc-works.org/mvc.png",
     :ssr "respo-ssr",
     :styles ["/main.css"],
     :scripts ["/main.js"],
     :inline-html "<script>console.log('nothing')</script>"})))
