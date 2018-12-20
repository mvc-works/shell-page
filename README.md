
Shell Page (generator)
----

[![Clojars Project](https://img.shields.io/clojars/v/mvc-works/shell-page.svg)](https://clojars.org/mvc-works/shell-page)

> Simple script for rendering `index.html` from configs.

### Usage

```edn
[mvc-works/shell-page "0.1.9"]
```

```clojure
(def app-html "<div>SSR rendered</div>")

(def configs {:title "A title"
              :icon "http://icon-url.png"
              :ssr "respo-ssr"
              :styles ["main.css"]
              :inline-styles [".app{color: #ddd;}"]
              :scripts ["main.js"]
              :inline-html ""
              :append-html ""
              :manifest "manifest.json"
              :viewport "width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no"})

(shell-page.core/make-page app-html configs) ; returns string of html
```

### License

MIT
