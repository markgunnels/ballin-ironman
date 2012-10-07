(ns lilface.github
  (:require [clj-http.client :as http]
            [cheshire.core :as json])
  (:import [org.apache.commons.codec.binary Base64]))

(defn base64-decode [s]
  (String.
    (Base64/decodeBase64 s)))

(defn to-clj [json]
  (json/parse-string json true))

(def github-url "https://api.github.com")

;; :repositories
(defn api-search-repo [kw]
  (http/get (format "%s/legacy/repos/search/%s"
                   github-url kw)
            {:query-params
             {"language" "Clojure"}}))

;; nathanmarz/carbonite
(defn api-get-repo [{:keys [user repo]}]
  (http/get (format "%s/repos/%s/%s" github-url user repo)))

(defn api-get-path [{:keys [user repo path]}]
  (http/get
    (format "%s/repos/%s/%s/contents/%s"
            github-url
            user
            repo
            path)))
