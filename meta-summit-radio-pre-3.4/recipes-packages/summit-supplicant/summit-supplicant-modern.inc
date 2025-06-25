
DEFCONFIG = "config_openssl"

require summit-supplicant.inc

DEPENDS += "dbus"

FILES_${PN} += "${datadir}/dbus-1/system-services/* ${sysconfdir}/dbus-1/system.d/*"
