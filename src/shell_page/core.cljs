
(ns shell-page.core
  (:require-macros (respo.macros :refer [html <> head title script style meta' div link body]))
  (:require [respo.core :refer [create-element]]
            [respo.render.html :refer [make-html]]
            ["fs" :as fs]))

(defn make-page [html-content resources]
  (assert (string? html-content) "1st argument should be string")
  (assert (map? resources) "2nd argument should be hashmap")
  (make-html
   (html
    {}
    (head
     {}
     (<> title (:title resources) nil)
     (link {:rel "icon", :type "image/png", :href (:icon resources)})
     (link {:rel "manifest", :href "manifest.json"})
     (meta' {:charset "utf8"})
     (meta' {:name "viewport", :content "width=device-width, initial-scale=1"})
     (if (some? (:ssr resources)) (meta' {:class (:ssr resources)})))
    (body
     {}
     (div
      {}
      (->> (:styles resources)
           (map-indexed
            (fn [idx path] [idx (link {:rel "stylesheet", :type "text/css", :href path})]))))
     (div {:class-name "app", :innerHTML html-content})
     (if (some? (:inline-html resources)) (div {:innerHTML (:inline-html resources)}))
     (div
      {}
      (->> (:scripts resources) (map-indexed (fn [idx path] [idx (script {:src path})]))))
     (if (some? (:append-html resources)) (div {:innerHTML (:append-html resources)}))))))

(defn slurp [x] (println "Reading from" x) (.readFileSync fs x "utf8"))

(defn spit [file-name content]
  (println "Writing to" file-name)
  (.writeFileSync fs file-name content))
