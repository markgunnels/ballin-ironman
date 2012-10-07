(ns lilface.fakelein
  "In order to safely execute and intercept defproject calls, this ns gets required by the clojail sandboxes by the sandbox init function."
  (:require [clojure.walk :refer (walk)]))

(defn unquote-project
  "Ripped from leiningen. Inside defproject forms, unquoting (~) allows for arbitrary evaluation."
  [args]
  (walk (fn [item]
               (cond (and (seq? item) (= `unquote (first item))) (second item)
                     ;; needed if we want fn literals preserved
                     (or (seq? item) (symbol? item)) (list 'quote item)
                     :else (unquote-project item)))
             identity
             args))

(defmacro defproject
  "This puts a map of the project structure into #'sandbox/project."
  [project-name ver & {:as args}]
  `(let [args# ~(unquote-project args)]
     (def ~'project (merge args#
                           {:name ~project-name
                            :version ~ver}))))
