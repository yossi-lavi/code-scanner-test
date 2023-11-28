package main

import (
	"context"
	"log"
	"net/http"
	"os"

	"go-test-project/dal"
	"go-test-project/model"
	"go-test-project/routes"
)

type ContextInjector struct {
	ctx context.Context
	h   http.Handler
}

func (ci *ContextInjector) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	ci.h.ServeHTTP(w, r.WithContext(ci.ctx))
}

func main() {
	db := dal.NewStorage()
	model.CreateUserTable(db.DB, "users")
	model.CreateUserTable(db.DB, "address")

	host, ok := os.LookupEnv("APP_HOST")
	if !ok {
		host = "localhost"
		log.Print("using default host `localhost`")
	}
	port, ok := os.LookupEnv("APP_PORT")
	if !ok {
		port = "8080"
		log.Print("using default port `80`")
	}

	ctx := context.WithValue(context.Background(), "db", db.DB)

	http.Handle("/", &ContextInjector{ctx, http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("ok"))
	})})

	http.Handle("/user", http.Handler(&ContextInjector{ctx, http.HandlerFunc(routes.UserHandler)}))

	log.Fatal(http.ListenAndServe(host+":"+port, nil))
}
