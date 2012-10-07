(ns lilface.lein
  "Safely loads a project.clj."
  (:require [clojail.core :refer
             (sandbox)]
            [clojail.testers :as testers])
  (:import [java.io PushbackReader StringReader]))


;; FIXME make sure sandboxes can't call outside code
;; For example, shutting down jetty
(defn project-sandbox 
  "Returns a clojail sandbox that auto-requires a sneaky defproject macro. Call the sandbox on quoted forms then discard."
  []
  (sandbox testers/secure-tester-without-def
           :init '(use (quote lilface.fakelein))))

;; need to run (load-string "projectcontents") in sandbox
;; then evaluate #'project, but load-string is protected
;; Instead, loop/recur with (read) outside of the sandbox

(defn eval-in-sandbox
  "Evals all forms inside sandbox. Evaluation can throw exceptions. It may be better to doseq over a list of forms"
  [sandbox forms-str]
  (let [stream (PushbackReader. (StringReader. forms-str))]
    (loop []
      (let [form (read stream false -1)]
        (when (not= -1 form)
          (sandbox form) ;; this can throw shit
          (recur)))))
  sandbox)

(defn read-project
  "Expects a string representation of the project.clj.
  Evaluates in a sandbox, teasing out the map from defproject."
  [project]
  (let [sandbox (project-sandbox)]
    (try
      (eval-in-sandbox sandbox project)
      (sandbox 'project)
      (catch Exception e
        nil))))
