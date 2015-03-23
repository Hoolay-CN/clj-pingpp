(ns clj-pingpp.charge
  (:require [clj-pingpp.http :as h]))

(defn create-charge
  "Creates a new charge operation."
  [opts]
  (h/post-req "charges" (assoc opts :currency "cny")))

(defn get-charge
  "Get charge information,
  Requires a charge id as a string."
  [charge-id]
  (h/get-req (str "charges/" charge-id) nil))

(defn get-charges
  "Get all charges."
  ([]
   (get-charges nil))
  ([opts]
   (h/get-req "charges" opts)))

(defn create-refund
  "Creates a create-refund operation.
  Requires the charge id (as a string) and description.
  Note that you can't try to send a refund greater than the current
  amount on the charge. You can keep refunding until a charge is
  empty, however."
  [req]
  (h/post-req (format "charges/%s/refunds" (:id req))
              (dissoc req :id)))

(defn get-refund
  "Get refund information."
  [charge-id refund-id]
  (h/get-req (format "charges/%s/refunds/%s" charge-id refund-id)))

(defn get-refunds
  "Get all refunds."
  ([charge-id]
   (get-refunds charge-id nil))
  ([charge-id opts]
   (h/get-req (format "charges/%s/refunds" charge-id) opts)))
