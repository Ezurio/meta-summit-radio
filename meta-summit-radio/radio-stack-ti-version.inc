BB_STRICT_CHECKSUM:summit-internal = "ignore"

PREMIRRORS:summit-internal = ""
MIRRORS:summit-internal = ""

SUMMIT_URI_BASE = "https://github.com/Ezurio/SonaTI-Release-Packages/releases/download/LRD-REL-${PV}"
SUMMIT_URI_BASE:summit-internal = "https://${RFPROS_FILESHARE_AUTH}files.devops.rfpros.com/builds/linux"

require ../radio-stack-ti-hashes.inc
