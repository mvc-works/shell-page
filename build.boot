
(set-env!
  :resource-paths #{"src"}
  :dependencies '[[respo "0.5.2"]])

(def +version+ "0.1.3")

(deftask build []
  (comp
    (pom :project     'mvc-works/shell-page
         :version     +version+
         :description "Genetate index.html with Respo"
         :url         "https://github.com/mvc-works/shell-page"
         :scm         {:url "https://github.com/mvc-works/shell-page"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))