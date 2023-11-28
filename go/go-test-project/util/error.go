package util

import "log"

type HostError struct {
	log.Logger
	error
}
