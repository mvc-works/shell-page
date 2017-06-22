
Shell Page (generator)
----

[![Clojars Project](https://img.shields.io/clojars/v/mvc-works/shell-page.svg)](https://clojars.org/mvc-works/shell-page)

> Simple script for rendering `index.html` from configs.

### Usage

```edn
[mvc-works/shell-page "0.1.1"]
```

```clojure
(def app-html "<div>SSR rendered</div>")

(def configs {:title "A title"
              :icon "http://icon-url.png"
              :ssr? false
              :styles ["main.css"]
              :scripts ["main.js"]
              :inline-html ""})

(make-page app-html configs) ; returns string of html
```

### License

MIT
