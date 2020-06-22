
(ns shell-page.core
  (:require (respo.core
             :refer
             [html <> head title script style meta' div link body style list->])
            [respo.render.html :refer [make-string]]
            [lilac.core
             :refer
             [dev-check record+ string+ vector+ optional+ or+ keyword+ boolean+]]
            ["fs" :as fs]))

(defn get-indexed [xs]
  (->> xs (map-indexed (fn [idx x] [idx x])) (filter (fn [[idx x]] (some? x)))))

(def lilac-resource
  (record+
   {:title (string+),
    :icon (string+),
    :ssr (string+),
    :styles (vector+ (string+)),
    :inline-styles (vector+ (string+)),
    :scripts (vector+
              (or+
               [(string+)
                (record+
                 {:type (keyword+), :src (string+), :defer? (boolean+)}
                 {:exact-keys? true})])),
    :inline-html (string+),
    :append-html (string+),
    :manifest (string+)}
   {:all-optional? true, :exact-keys? true}))

(defn make-page [html-content resources]
  (assert (string? html-content) "1st argument should be string")
  (assert (map? resources) "2nd argument should be hashmap")
  (dev-check resources lilac-resource)
  (make-string
   (html
    {}
    (list->
     :head
     {}
     (get-indexed
      (concat
       [(<> title (:title resources) nil)
        (link {:rel "icon", :type "image/png", :href (:icon resources)})
        (when-let [manifest (:manifest resources)] (link {:rel "manifest", :href manifest}))
        (meta' {:charset "utf8"})
        (meta'
         {:name "viewport",
          :content (or (:viewport resources)
                       "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no")})
        (if (some? (:ssr resources)) (meta' {:class (:ssr resources)}))]
       (->> (:styles resources)
            (map (fn [path] (link {:rel "stylesheet", :type "text/css", :href path}))))
       (->> (:inline-styles resources) (map (fn [content] (style {:innerHTML content}))))
       (->> (:scripts resources)
            (map
             (fn [path]
               (cond
                 (string? path) (script {:src path})
                 (and (map? path) (= :module (:type path)))
                   (script
                    {:type "module",
                     :src (:src path),
                     :defer (if (:defer? path) true false)})
                 (and (map? path) (= :script (:type path)))
                   (script {:src (:src path), :defer (if (:defer? path) true false)})
                 :else (println "[Shell Page]: unknown path" path))))))))
    (body
     {}
     (div {:class-name "app", :innerHTML html-content})
     (if (some? (:inline-html resources)) (div {:innerHTML (:inline-html resources)}))
     (if (some? (:append-html resources)) (div {:innerHTML (:append-html resources)}))))))

(defn slurp [x] (println "Reading from" x) (.readFileSync fs x "utf8"))

(defn spit [file-name content]
  (println "Writing to" file-name)
  (.writeFileSync fs file-name content))
