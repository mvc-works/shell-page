
(ns shell-page.core
  (:require (respo.macros
             :refer
             [html <> head title script style meta' div link body style list->])
            [respo.render.html :refer [make-string]]
            ["fs" :as fs]))

(defn make-page [html-content resources]
  (assert (string? html-content) "1st argument should be string")
  (assert (map? resources) "2nd argument should be hashmap")
  (make-string
   (html
    {}
    (head
     {}
     (<> title (:title resources) nil)
     (link {:rel "icon", :type "image/png", :href (:icon resources)})
     (link {:rel "manifest", :href "manifest.json"})
     (meta' {:charset "utf8"})
     (meta'
      {:name "viewport",
       :content (or (:viewport resources)
                    "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no")})
     (if (some? (:ssr resources)) (meta' {:class (:ssr resources)})))
    (body
     {}
     (list->
      :div
      {}
      (->> (:styles resources)
           (map-indexed
            (fn [idx path] [idx (link {:rel "stylesheet", :type "text/css", :href path})]))))
     (list->
      :div
      {}
      (->> (:inline-styles resources)
           (map-indexed (fn [idx content] [idx (style {:innerHTML content})]))))
     (div {:class-name "app", :innerHTML html-content})
     (if (some? (:inline-html resources)) (div {:innerHTML (:inline-html resources)}))
     (list->
      :div
      {}
      (->> (:scripts resources) (map-indexed (fn [idx path] [idx (script {:src path})]))))
     (if (some? (:append-html resources)) (div {:innerHTML (:append-html resources)}))))))

(defn slurp [x] (println "Reading from" x) (.readFileSync fs x "utf8"))

(defn spit [file-name content]
  (println "Writing to" file-name)
  (.writeFileSync fs file-name content))
