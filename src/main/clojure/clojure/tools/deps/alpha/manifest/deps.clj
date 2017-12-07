;   Copyright (c) Rich Hickey. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file epl-v10.html at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.

(ns clojure.tools.deps.alpha.manifest.deps
  (:require
    [clojure.java.io :as jio]
    [clojure.tools.deps.alpha.providers :as providers]
    [clojure.tools.deps.alpha.reader :as reader]))

(defn- deps-map
  [config dir]
  (reader/merge-deps [config (reader/slurp-deps (jio/file dir "deps.edn"))]))

(defmethod providers/coord-deps :deps
  [_lib {:keys [deps/root] :as coord} _mf config]
  (seq (:deps (deps-map config root))))

(defmethod providers/coord-paths :deps
  [_lib {:keys [deps/root] :as coord} _mf config]
  (into []
    (map #(.getAbsolutePath (jio/file root %)))
    (:paths (deps-map config root))))

