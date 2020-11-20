(ns todomvc.db
  (:require [cljs.reader]
            [re-frame.core :as rf]))

(def default-db
  {:name "re-frame"
   :todos (sorted-map)
   :showing :all})
