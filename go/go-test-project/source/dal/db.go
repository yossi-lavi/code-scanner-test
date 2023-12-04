package dal

import (
	"database/sql"
	"fmt"
	"log"
	"os"

	_ "github.com/lib/pq"

	"go-test-project/audit"
)

type Storage struct {
	DB      *sql.DB
	Name    *string
	auditor *audit.Auditor
}

type connData struct {
	Host
	user    string
	pass    string
	dbname  string
	sslMode string
}

func NewStorage() Storage {
	store := Storage{}
	store.connect()
	err := store.DB.Ping()
	if err != nil {
		log.Fatal(err)
	}

	return store
}

func (s *Storage) connect() {
	conn := getENV()
	psqlInfo := fmt.Sprintf("host=%s port=%s user=%s password=%s dbname=%s sslMode=%s",
		conn.host, conn.port, conn.user, conn.pass, conn.dbname, conn.sslMode)
	db, err := sql.Open("postgres", psqlInfo)
	if err != nil {
		log.Fatal(err)
	}
	s.DB = db
}

func getENV() connData {
	conn := connData{}
	var ok bool

	conn.host, ok = os.LookupEnv("DB_HOST")
	if !ok {
		log.Fatal("undefined `DB_HOST`")
	}
	conn.port, ok = os.LookupEnv("DB_PORT")
	if !ok {
		log.Fatal("undefined `DB_PORT`")
	}
	conn.user, ok = os.LookupEnv("DB_USER")
	if !ok {
		log.Fatal("undefined `DB_USER`")
	}
	conn.pass, ok = os.LookupEnv("DB_PASSWORD")
	if !ok {
		log.Fatal("undefined `DB_PASSWORD`")
	}
	conn.dbname, ok = os.LookupEnv("DB_NAME")
	if !ok {
		log.Fatal("undefined `DB_NAME")
	}
	conn.sslMode, ok = os.LookupEnv("DB_SSLMODE")
	if !ok {
		log.Fatal("undefined `DB_SSLMODE`")
	}

	return conn
}
