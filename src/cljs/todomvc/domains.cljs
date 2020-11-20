(ns todomvc.domains
  (:require [clojure.spec.alpha :as s]))

(s/def ::id int?)
(s/def ::title string?)
(s/def ::name string?)
(s/def ::done boolean?)
(s/def ::todo (s/keys :req-un [::id ::title ::done]))
(s/def ::todos (s/and                                       ;; should use the :kind kw to s/map-of (not supported yet)
                (s/map-of ::id ::todo)                     ;; in this map, each todo is keyed by its :id
                #(instance? PersistentTreeMap %)           ;; is a sorted-map (not just a map)
                ))
(s/def ::showing                                            ;; what todos are shown to the user?
  #{:all                                                    ;; all todos are shown
    :active                                                 ;; only todos whose :done is false
    :done                                                   ;; only todos whose :done is true
    })
(s/def ::db (s/keys :req-un [::todos ::showing] :opt-un [::name]))
