package audit

import (
	"log"
)

type Logger struct{}

func (l Logger) info(v ...any) {
	log.Default().Println(v...)
}

func (l Logger) error(v ...any) {
	log.Default().Fatal(v...)
}
