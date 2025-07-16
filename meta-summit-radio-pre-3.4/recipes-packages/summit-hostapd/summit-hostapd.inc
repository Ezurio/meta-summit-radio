SECTION = "kernel/userland"

LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://hostapd/README;md5=0e430ef1be3d6eebf257cf493fc7661d"

PV = "${RADIO_VERSION}"   

DEPENDS = "libnl openssl"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/summit_supplicant/laird/${PV}"

SRC_URI = " \
    ${SUMMIT_URI}/summit_supplicant-src-${PV}.tar.gz;name=summit-supplicant \
    file://init \
    file://hostapd.service \
"

S = "${WORKDIR}/summit_supplicant-${PV}"
B = "${S}/hostapd"

inherit update-rc.d systemd pkgconfig

INITSCRIPT_NAME = "hostapd"

SYSTEMD_SERVICE_${PN} = "hostapd.service"
SYSTEMD_AUTO_ENABLE_${PN} = "disable"

do_configure() {
    install -m 0644 "${B}/config_openssl" "${B}/.config"
}

do_install() {
    install -m 0644 -D -t "${D}${sysconfdir}" "${B}/hostapd.conf"
    install -m 0755 -D -t "${D}${sbindir}" "${B}/hostapd" "${B}/hostapd_cli"
    install -m 0755 -D "${WORKDIR}/init" "${D}${sysconfdir}/init.d/hostapd"
 
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 0644 -D -t "${D}${systemd_unitdir}/system/" "${WORKDIR}/hostapd.service"
        sed -i -e "s,@SBINDIR@,${sbindir},g" -e "s,@SYSCONFDIR@,${sysconfdir},g" "${D}${systemd_unitdir}/system/hostapd.service"
    fi
}

CONFFILES_${PN} += "${sysconfdir}/hostapd.conf"

PROVIDES += "hostapd"

RPROVIDES_${PN} += "hostapd"
RREPLACES_${PN} += "hostapd"
RCONFLICTS_${PN} += "hostapd"
