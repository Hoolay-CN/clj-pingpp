# clj-pingpp
A Clojure wrapper of the [ping++ API](https://pingxx.com/document/api).

## Usage
Here's the latest Leiningen version info:
[![Clojars Project](http://clojars.org/clj-pingpp/latest-version.svg)](http://clojars.org/clj-pingpp)

You'll need this in your environment:

```sh
export PINGPP_SECRET="pingpp_secret_token"
```

```clj

(def charge {:order_no "1234567890"
             "app[id]" "your app id"
             :amount "100"
             :channel "alipay"
             :client_ip "127.0.0.1"
             :subject "苹果2"
             :body "一个大苹果"})

;; create charge
(create-charge charge)

;; get charge by id
(get-charge charge-id)

;; get all charges
(get-charges)

;; create refund
(create-refund {:id charge-id
                :description description})

;; get refund by charge id and refund id
(get-refund charge-id refund-id)

;; get all refunds
(get-refunds)
```

## License

Copyright © 2015 Hoolay.CN

Distributed under the Eclipse Public License either version 1.0.
