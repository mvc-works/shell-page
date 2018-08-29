
(defn read-password [guide]
  (String/valueOf (.readPassword (System/console) guide nil)))

(set-env!
  :resource-paths #{"src"}
  :dependencies '[]
  :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"
                                     :username "jiyinyiyong"
                                     :password (read-password "Clojars password: ")}]))

(def +version+ "0.1.8")

(deftask deploy []
  (comp
    (pom :project     'mvc-works/shell-page
         :version     +version+
         :description "Genetate index.html with Respo"
         :url         "https://github.com/mvc-works/shell-page"
         :scm         {:url "https://github.com/mvc-works/shell-page"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (push :repo "clojars" :gpg-sign false)))
