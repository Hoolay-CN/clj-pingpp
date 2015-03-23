(ns clj-pingpp.http
  (:require [cheshire.core :refer [parse-string]]
            [org.httpkit.client :as client]
            [environ.core :refer [env]]))

(def ^:dynamic url "https://api.pingxx.com/v1/")
(def ^:dynamic *token* nil)

(defn api-token
  []
  (or *token* (:pingpp-secret env)))

(defn- collectify [x]
  (cond (nil? x) []
        (or (sequential? x) (instance? java.util.List x) (set? x)) x
        :else [x]))

(defn prepare-expansion
  "Turns the expansion's keywords into strings. In the future, this
  might validate that all expansions are reliable jumps based on the
  ping++ API's allowed expansion fields for each object."
  [expand]
  (map name (collectify expand)))

(defn prepare-params
  "Returns a parameter map suitable for feeding in to a request to Stripe.

  `params` is the parameters for the ping++ API calls."
  [token params]
  (let [params (if-let [expand (:expand params)]
                 (-> (dissoc params :expand)
                     (assoc "expand[]" (prepare-expansion expand)))
                 params)]
    {:headers {"Authorization" (str "Bearer " token)}
     :query-params params
     :throw-exceptions false}))

(defn api-call
  "Call an API method on ping++."
  [token method endpoint params]
  (assert token "API Token must not be nil.")
  (let [url (str url endpoint)
        params (prepare-params token params)
        process (fn [ret]
                  (or (parse-string (:body ret) keyword)
                      {:error (:error ret)}))]
    (process @(method url params))))

(defmacro defapi
  "Generates a synchronous and async version of the same function."
  [sym method]
  `(defn ~sym
     [endpoint# params#]
     (api-call (api-token) ~method endpoint# params#)))

(defapi post-req client/post)
(defapi get-req client/get)
(defapi delete-req client/delete)
