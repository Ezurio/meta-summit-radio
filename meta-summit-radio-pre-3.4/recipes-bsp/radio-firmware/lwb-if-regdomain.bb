SUMMARY = "LWB/IF Regulatory Domain Setting"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

LWB_REGDOMAIN ?= ""

do_install() {
    if [ -n "${LWB_REGDOMAIN}" ]; then
        install -d  "${D}${sysconfdir}/modprobe.d"
        echo "options brcmfmac regdomain=\"${LWB_REGDOMAIN}\"" > "${D}${sysconfdir}/modprobe.d/brcmfmac_regd.conf"
    fi
}
