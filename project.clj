(defproject ballin-ironman "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :jvm-opts ["-Djava.security.manager" "-Djava.security.policy=file:resources/policy"]
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories {"sonatype-oss-public" {:url "https://oss.sonatype.org/content/groups/public/"}}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.1"]
                 [leiningen-core "2.0.0-preview7"]
                 [clojail "0.6.2"]
                 [cheshire "4.0.0"]
                 [commons-codec "1.6"]
                 [clj-http "0.5.0"]])
