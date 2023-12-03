package audit

import (
	"time"
)

type Auditor struct {
	UserEmail string
	AddedTime time.Time
	logger    Logger
}

func (a Auditor) Audit() {
	a.logger.info("Auditor added %v", a)
}
