package routes

import (
	"database/sql"
	"encoding/json"
	"io/ioutil"
	"net/http"
	"time"

	"go-test-project/audit"
	"go-test-project/model"
	"go-test-project/util"
)

func UserHandler(w http.ResponseWriter, r *http.Request) {
	switch r.Method {
	case "GET":
		UserList(w, r)
	case "POST":
		UserAdd(w, r)
	}
}

func UserList(w http.ResponseWriter, r *http.Request) {
	db := r.Context().Value("db").(*sql.DB)
	model.UserObj{}.ReadAll(db)
	w.Write([]byte("ok\n"))
}

func UserAdd(w http.ResponseWriter, r *http.Request) {
	db := r.Context().Value("db").(*sql.DB)

	body, err := ioutil.ReadAll(r.Body)
	if err != nil {
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	var user model.User
	if err := json.Unmarshal(body, &user); err != nil {
		http.Error(w, "Invalid JSON in request body", http.StatusBadRequest)
		return
	}
	if util.StringLength(user.Password) < 8 {
		http.Error(w, "Password must be at least 8 characters", http.StatusBadRequest)
		return
	}

	model.UserObj{}.Add(db, user)
	w.Write([]byte("ok\n"))

	auditor := audit.Auditor{
		UserEmail: user.Email,
		AddedTime: time.Now(),
	}
	auditor.Audit()
}
