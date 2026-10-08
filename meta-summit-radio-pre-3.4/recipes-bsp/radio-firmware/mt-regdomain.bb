SUMMARY = "MediaTek regulatory domain setting"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

MT_REGDOMAIN ?= ""

do_install() {
	if [ -n "${MT_REGDOMAIN}" ]; then
		install -d "${D}${sysconfdir}/modprobe.d"
		echo "options wlan_mt7961_sdio regdomain=\"${MT_REGDOMAIN}\"" \
			> "${D}${sysconfdir}/modprobe.d/wlan_mt7961_sdio.conf"
	fi
}
